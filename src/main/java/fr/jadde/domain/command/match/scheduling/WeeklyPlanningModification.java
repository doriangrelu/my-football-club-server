package fr.jadde.domain.command.match.scheduling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.jadde.service.util.DateTimeDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WeeklyPlanningModification extends AbstractPlanningModification {

    @NotNull
    public final DayOfWeek dayOfWeek;

    @JsonCreator
    public WeeklyPlanningModification(final @JsonProperty("startAt") LocalDate startAt,
                                      final @JsonProperty("endAt") LocalDate endAt,
                                      final @JsonProperty("hour") LocalTime hour,
                                      final @JsonProperty("dayOfWeek") DayOfWeek day
    ) {
        super(startAt, endAt, hour);
        this.dayOfWeek = day;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof final WeeklyPlanningModification that)) {
            return false;
        }

        return new EqualsBuilder().appendSuper(super.equals(o)).append(this.getDayOfWeek(), that.getDayOfWeek()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.getDayOfWeek()).toHashCode();
    }

}
