package fr.jadde.domain.model.scheduling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.jadde.domain.model.match.MatchInstance;
import fr.jadde.service.util.DateTimeDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class WeeklyPlanning extends AbstractPlanning {

    public final DayOfWeek dayOfWeek;

    @JsonCreator
    public WeeklyPlanning(final @JsonDeserialize(using = DateTimeDeserializer.class) @JsonProperty("startAt") LocalDateTime startAt,
                          final @JsonDeserialize(using = DateTimeDeserializer.class) @JsonProperty("endAt") LocalDateTime endAt,
                          final @JsonProperty("hour") LocalTime hour,
                          final @JsonProperty("dayOfWeek") DayOfWeek dayOfWeek,
                          final @JsonProperty("instances") Set<MatchInstance> instances
    ) {
        super(startAt, endAt, hour, instances);
        this.dayOfWeek = dayOfWeek;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof final WeeklyPlanning that)) {
            return false;
        }

        return new EqualsBuilder().appendSuper(super.equals(o)).append(this.getDayOfWeek(), that.getDayOfWeek()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.getDayOfWeek()).toHashCode();
    }

}
