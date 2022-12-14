package fr.jadde.service;

import fr.jadde.database.entity.MatchDefinitionEntity;
import fr.jadde.database.entity.TeamEntity;
import fr.jadde.domain.command.match.CreateDefinition;
import fr.jadde.domain.model.MatchDefinition;
import fr.jadde.service.mapper.MatchDefinitionMapper;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

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
                    return team.get().persistAndFlush();
                })
                .map(dummy -> this.definitionMapper.from(definitionEntity));
    }

}
