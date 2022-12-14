package fr.jadde.service.mapper;

import fr.jadde.database.entity.scheduling.PlanningEntity;
import fr.jadde.database.entity.scheduling.WeeklyPlanningEntity;
import fr.jadde.domain.command.match.scheduling.AbstractPlanningModification;
import fr.jadde.domain.command.match.scheduling.WeeklyPlanningModification;
import fr.jadde.domain.model.scheduling.AbstractPlanning;
import fr.jadde.domain.model.scheduling.WeeklyPlanning;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
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

    WeeklyPlanning from(WeeklyPlanningEntity planning);

}
