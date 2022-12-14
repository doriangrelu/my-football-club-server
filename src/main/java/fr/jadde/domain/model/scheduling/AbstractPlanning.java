package fr.jadde.domain.model.scheduling;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.jadde.service.util.DateTimeDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DayPlanning.class, name = "day"),
})
public abstract class AbstractPlanning {

    @JsonProperty("startAt")
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime startAt;

    @JsonProperty("endAt")
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime endAt;

    @JsonProperty("hour")
    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime hour;

    @JsonCreator
    public AbstractPlanning(final @JsonProperty("startAt") LocalDateTime startAt,
                            final @JsonProperty("endAt") LocalDateTime endAt,
                            final @JsonProperty("hour") LocalTime hour) {
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

        if (!(o instanceof final AbstractPlanning that)) {
            return false;
        }

        return new EqualsBuilder().append(this.getStartAt(), that.getStartAt()).append(this.getEndAt(), that.getEndAt()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.getStartAt()).append(this.getEndAt()).toHashCode();
    }
}
