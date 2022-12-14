package fr.jadde.service;

import fr.jadde.database.entity.MatchDefinitionEntity;
import fr.jadde.database.entity.MatchInstanceEntity;
import fr.jadde.database.entity.TeamEntity;
import fr.jadde.database.entity.scheduling.PlanningEntity;
import fr.jadde.domain.command.match.CreateDefinition;
import fr.jadde.domain.model.match.MatchDefinition;
import fr.jadde.service.mapper.MatchDefinitionMapper;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@ApplicationScoped
public class MatchService {

    private final MatchDefinitionMapper definitionMapper;


    public MatchService(final MatchDefinitionMapper definitionMapper) {
        this.definitionMapper = definitionMapper;
    }


    public Uni<MatchDefinition> createDefinition(final CreateDefinition createDefinition) {
        final MatchDefinitionEntity definitionEntity = this.definitionMapper.from(createDefinition);
        final AtomicReference<TeamEntity> team = new AtomicReference<>();
        return TeamEntity.<TeamEntity>findById(createDefinition.teamIdentifier())
                .onItem()
                .ifNull()
                .failWith(() -> new NoSuchElementException("Missing associated team with identifier '" + createDefinition.teamIdentifier() + "'"))
                .invoke(team::set)
                .chain(teamEntity -> Mutiny.fetch(teamEntity.getMatchDefinitions()))
                .flatMap(matchDefinitionEntities -> {
                    matchDefinitionEntities.add(definitionEntity);
                    definitionEntity.setTeam(team.get());
                    this.handleCreateInstances(definitionEntity);
                    return team.get().persistAndFlush();
                })
                .map(dummy -> this.definitionMapper.from(definitionEntity));
    }

    private void handleCreateInstances(final MatchDefinitionEntity definitionEntity) {
        definitionEntity.getPlannings().forEach(this::createInstanceFromPlanning);
    }

    private void createInstanceFromPlanning(final PlanningEntity planningEntity) {
        final AtomicReference<LocalDate> at = new AtomicReference<>(planningEntity.getStartAt());
        final long daysBetween = Duration.between(
                planningEntity.getStartAt().atStartOfDay(),
                planningEntity.getEndAt().atStartOfDay()
        ).toDays();
        Stream.generate(MatchInstanceEntity::new)
                .limit(daysBetween)
                .forEach(matchInstanceEntity -> {
                    matchInstanceEntity.setMatchDefinition(planningEntity.getMatchDefinition());
                    matchInstanceEntity.setAt(at.get().atStartOfDay());
                    final LocalDate nextDate = at.get().plusDays(1);
                    at.set(nextDate);
                });
    }

}
