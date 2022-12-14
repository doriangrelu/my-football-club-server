package fr.jadde.domain.command.match.scheduling;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.jadde.service.util.DateTimeDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
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
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime startAt;

    @NotNull
    @JsonProperty("endAt")
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime endAt;

    @NotNull
    @JsonProperty("hour")
    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime hour;

    protected AbstractPlanningModification(final LocalDateTime startAt,
                                           final LocalDateTime endAt,
                                           final LocalTime hour) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.hour = hour;
    }

    public LocalTime getHour() {
        return this.hour;
    }

    public LocalDateTime getStartAt() {
        return this.startAt;
    }

    public LocalDateTime getEndAt() {
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
