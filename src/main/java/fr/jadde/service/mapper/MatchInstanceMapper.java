package fr.jadde.service.mapper;

import fr.jadde.database.entity.match.MatchDefinitionEntity;
import fr.jadde.database.entity.match.MatchInstanceEntity;
import fr.jadde.domain.model.match.MatchDefinition;
import fr.jadde.domain.model.match.MatchInstance;
import fr.jadde.domain.model.match.MatchStatus;
import org.mapstruct.*;

import java.time.Duration;
import java.time.LocalDate;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = {
        PlayerMapper.class
})
public interface MatchInstanceMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "players", source = "players")
    @Mapping(target = "status", ignore = true)
    MatchInstance from(MatchInstanceEntity matchInstanceEntity);

    @AfterMapping // or @BeforeMapping
    default void computeStatus(MatchInstanceEntity matchInstanceEntity, @MappingTarget MatchInstance matchInstance) {
        final LocalDate startAt = matchInstanceEntity.getAt();
        final LocalDate now = LocalDate.now();
        final MatchStatus status;
        final long daysBetween = Duration.between(
                now.atStartOfDay(),
                startAt.atStartOfDay()
        ).toDays();

        if (startAt.equals(now)) {
            status = MatchStatus.PENDING;
        } else if (daysBetween <= matchInstanceEntity.getPlanning().getMatchDefinition().getNumberOfDaysBeforeOpening()) {
            status = MatchStatus.REGISTRATION_OPEN;
        } else if (startAt.isBefore(now)) {
            status = MatchStatus.ENDED;
        } else {
            status = MatchStatus.REGISTRATION_CLOSED;
        }
        matchInstance.setStatus(status);
    }

}
