package fr.jadde.service.mapper;

import fr.jadde.database.entity.scheduling.PlanningEntity;
import fr.jadde.database.entity.scheduling.WeeklyRecurrenceEntity;
import fr.jadde.domain.model.scheduling.AbstractPlanning;
import fr.jadde.domain.model.scheduling.DayPlanning;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface PlanningMapper {

    default PlanningEntity from(final AbstractPlanning planning) {
        if (planning instanceof DayPlanning dayPlanning) {
            return this.from(dayPlanning);
        }
        throw new UnsupportedOperationException("Cannot find mapper");
    }

    default AbstractPlanning from(final PlanningEntity planning) {
        if (planning instanceof WeeklyRecurrenceEntity weeklyRecurrence) {
            return this.from(weeklyRecurrence);
        }
        throw new UnsupportedOperationException("Cannot find mapper");
    }

    @Mapping(source = "day", target = "dayNumber")
    WeeklyRecurrenceEntity from(DayPlanning planning);

    @Mapping(target = "day", source = "dayNumber")
    DayPlanning from(WeeklyRecurrenceEntity planning);

}
