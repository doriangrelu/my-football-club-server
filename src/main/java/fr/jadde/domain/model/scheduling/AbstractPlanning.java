package fr.jadde.domain.model.scheduling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.jadde.domain.model.match.MatchInstance;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WeeklyPlanning.class, name = "weekly"),
})
public abstract class AbstractPlanning {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate startAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate endAt;

    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime hour;

    private final Set<MatchInstance> instances;

    @JsonCreator
    protected AbstractPlanning(final LocalDate startAt,
                               final LocalDate endAt,
                               final LocalTime hour,
                               final Set<MatchInstance> instances
    ) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.hour = hour;
        this.instances = instances;
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

    public Set<MatchInstance> getInstances() {
        return Set.copyOf(this.instances);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof final AbstractPlanning that)) {
            return false;
        }

        return new EqualsBuilder().append(this.getStartAt(), that.getStartAt()).append(this.getEndAt(), that.getEndAt()).append(this.getHour(), that.getHour()).append(this.getInstances(), that.getInstances()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.getStartAt()).append(this.getEndAt()).append(this.getHour()).append(this.getInstances()).toHashCode();
    }

    @Override
    public String toString() {
        return "AbstractPlanning{" +
                "startAt=" + this.startAt +
                ", endAt=" + this.endAt +
                ", hour=" + this.hour +
                ", instances=" + this.instances +
                '}';
    }
}
