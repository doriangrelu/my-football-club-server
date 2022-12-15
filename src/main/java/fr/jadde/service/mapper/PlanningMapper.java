package fr.jadde.service.mapper;

import fr.jadde.database.entity.match.PlanningEntity;
import fr.jadde.database.entity.match.WeeklyPlanningEntity;
import fr.jadde.domain.command.match.scheduling.AbstractPlanningModification;
import fr.jadde.domain.command.match.scheduling.WeeklyPlanningModification;
import fr.jadde.domain.model.scheduling.AbstractPlanning;
import fr.jadde.domain.model.scheduling.WeeklyPlanning;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = {
        MatchInstanceMapper.class
})
public interface PlanningMapper {

    default PlanningEntity from(final AbstractPlanningModification planning) {
        if (planning instanceof WeeklyPlanningModification weeklyPlanning) {
            return this.from(weeklyPlanning);
        }
        throw new UnsupportedOperationException("Cannot find mapper");
    }

    default AbstractPlanning from(final PlanningEntity planning) {
        if (planning instanceof WeeklyPlanningEntity weeklyRecurrence) {
            return this.from(weeklyRecurrence);
        }
        throw new UnsupportedOperationException("Cannot find mapper");
    }

    WeeklyPlanningEntity from(WeeklyPlanningModification planning);

    @Mapping(source = "endAt", target = "endAt")
    @Mapping(source = "startAt", target = "startAt")
    @Mapping(source = "matchInstances", target = "instances")
    WeeklyPlanning from(WeeklyPlanningEntity planning);

}
