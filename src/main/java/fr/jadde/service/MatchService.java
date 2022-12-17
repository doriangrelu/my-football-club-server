package fr.jadde.service;

import fr.jadde.database.entity.TeamEntity;
import fr.jadde.database.entity.match.MatchDefinitionEntity;
import fr.jadde.database.entity.match.MatchInstanceEntity;
import fr.jadde.database.entity.match.PlanningEntity;
import fr.jadde.database.entity.match.WeeklyPlanningEntity;
import fr.jadde.domain.command.match.CreateDefinition;
import fr.jadde.domain.model.match.MatchDefinition;
import fr.jadde.exception.HttpPrintableException;
import fr.jadde.service.mapper.MatchDefinitionMapper;
import fr.jadde.service.spi.voter.VoterService;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@ApplicationScoped
public class MatchService {

    private final UserService userService;

    private final MatchDefinitionMapper definitionMapper;

    private final VoterService voterService;


    public MatchService(final UserService userService, final MatchDefinitionMapper definitionMapper, final VoterService voterService) {
        this.userService = userService;
        this.definitionMapper = definitionMapper;
        this.voterService = voterService;
    }

    private static LocalDate recursiveDay(final LocalDate start, final DayOfWeek dayOfWeek) {
        if (start.getDayOfWeek().equals(dayOfWeek)) {
            return start;
        }
        return recursiveDay(start.plusDays(1), dayOfWeek);
    }

    private static Uni<Void> fetchMatchInstances(final Set<MatchInstanceEntity> entities) {
        return Uni.combine().all().unis(
                entities.stream().map(matchInstanceEntity -> Mutiny.fetch(matchInstanceEntity.getPlayers())).toList()
        ).discardItems();
    }

    public Uni<MatchDefinition> getDefinition(final UUID uuid) {
        return MatchDefinitionEntity.<MatchDefinitionEntity>findById(uuid)
                .onItem()
                .ifNull()
                .failWith(() -> HttpPrintableException.builder(404, "Not found").build())
                .map(this.definitionMapper::from);
    }

    public Uni<List<MatchDefinition>> getAllDefinitions(final String userIdentifier, final UUID teamIdentifier) {
        final List<Object> requestParameters = new ArrayList<>();
        String request = "from MatchDefinitionEntity d inner join fetch d.team t inner join fetch t.owner o where o.id=?1";
        requestParameters.add(userIdentifier);
        if (null != teamIdentifier) {
            request += " and t.id=?2";
            requestParameters.add(teamIdentifier);
        }
        return MatchDefinitionEntity.<MatchDefinitionEntity>find(request, requestParameters.toArray())
                .list()
                .map(this.definitionMapper::from);
    }

    public Uni<MatchDefinition> createDefinition(final String userIdentifier, final CreateDefinition createDefinition) {
        this.handleCheckPlanningDatesOrFail(createDefinition);
        final MatchDefinitionEntity definitionEntity = this.definitionMapper.from(createDefinition);
        final AtomicReference<TeamEntity> team = new AtomicReference<>();
        return TeamEntity.<TeamEntity>findById(createDefinition.teamIdentifier())
                .onItem()
                .ifNull()
                .failWith(() -> HttpPrintableException.builder(404, "Not found").build())
                .chain(teamEntity -> this.userService.createIfNecessary(userIdentifier)
                        .flatMap(user -> !this.voterService.isDenyAccessUnlessGranted("action::createMatchDefinition", teamEntity, Map.of(
                                "userIdentifier", userIdentifier
                        )) ? Uni.createFrom().item(teamEntity) : Uni.createFrom().failure(UnauthorizedException::new))
                )
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

    private void handleCheckPlanningDatesOrFail(final CreateDefinition definition) {
        definition.plannings().forEach(planning -> {
            if (planning.getStartAt().isAfter(planning.getEndAt())) {
                HttpPrintableException.builder(400, "Bad request")
                        .withError("notBefore.%start%.%end%", Map.entry("start", "start"), Map.entry("end", "end"))
                        .buildAndThrow();
            }
        });
    }

    private void handleCreateInstances(final MatchDefinitionEntity definitionEntity) {
        definitionEntity.getPlannings().forEach(this::createInstanceFromPlanning);
    }

    private void createInstanceFromPlanning(final PlanningEntity planningEntity) {
        if (planningEntity instanceof WeeklyPlanningEntity weeklyPlanningEntity) {
            final long daysBetween = Duration.between(
                    planningEntity.getStartAt().atStartOfDay(),
                    planningEntity.getEndAt().atStartOfDay()
            ).toDays();
            final LocalDate now = LocalDate.now();

            final LocalDate startAtFirstDay = recursiveDay(planningEntity.getStartAt(), weeklyPlanningEntity.getDayOfWeek());
            Stream.iterate(startAtFirstDay, localDate -> localDate.plusDays(1))
                    .limit(daysBetween)
                    .map(localDate -> recursiveDay(localDate, weeklyPlanningEntity.getDayOfWeek()))
                    .distinct()
                    .filter(localDate -> !localDate.isAfter(planningEntity.getEndAt()) && localDate.isAfter(now))
                    .forEach(localDateTime -> {
                        final MatchInstanceEntity instanceEntity = new MatchInstanceEntity();
                        instanceEntity.setAt(localDateTime);
                        instanceEntity.setPlanning(planningEntity);
                    });
        }
    }

}
