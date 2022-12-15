package fr.jadde.domain.command.match.scheduling;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.jadde.service.util.DateTimeDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WeeklyPlanningModification.class, name = "weekly"),
})
public abstract class AbstractPlanningModification {

    @NotNull
    @JsonProperty("startAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate startAt;

    @NotNull
    @Future
    @JsonProperty("endAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate endAt;

    @NotNull
    @JsonProperty("hour")
    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime hour;

    protected AbstractPlanningModification(final LocalDate startAt,
                                           final LocalDate endAt,
                                           final LocalTime hour) {
        if(startAt.atStartOfDay().isAfter(endAt.atStartOfDay())) {
            throw new IllegalArgumentException("Start date must less than end date");
        }
        this.startAt = startAt;
        this.endAt = endAt;
        this.hour = hour;
    }

    public LocalTime getHour() {
        return this.hour;
    }

    public LocalDate getStartAt() {
        return this.startAt;
    }

    public LocalDate getEndAt() {
        return this.endAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof final AbstractPlanningModification that)) {
            return false;
        }

        return new EqualsBuilder().append(this.getStartAt(), that.getStartAt()).append(this.getEndAt(), that.getEndAt()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.getStartAt()).append(this.getEndAt()).toHashCode();
    }
}
