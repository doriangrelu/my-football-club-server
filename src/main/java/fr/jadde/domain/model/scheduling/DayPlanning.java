package fr.jadde.domain.model.scheduling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.jadde.service.util.DateTimeDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DayPlanning extends AbstractPlanning {

    public final short day;

    @JsonCreator
    public DayPlanning(final @JsonDeserialize(using = DateTimeDeserializer.class) @JsonProperty("startAt") LocalDateTime startAt,
                       final @JsonDeserialize(using = DateTimeDeserializer.class) @JsonProperty("endAt") LocalDateTime endAt,
                       final @JsonProperty("hour") LocalTime hour,
                       final @JsonProperty("day") short day
    ) {
        super(startAt, endAt, hour);
        this.day = day;
    }

    public short getDay() {
        return this.day;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof final DayPlanning that)) {
            return false;
        }

        return new EqualsBuilder().appendSuper(super.equals(o)).append(this.getDay(), that.getDay()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.getDay()).toHashCode();
    }

}
