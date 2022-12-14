package fr.jadde.database.entity.scheduling;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Objects;

@Entity
@Table(name = "weekly_recurrence")
public class WeeklyPlanningEntity extends PlanningEntity {
    @Column(name = "day_number", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(final DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final WeeklyPlanningEntity that = (WeeklyPlanningEntity) o;
        return this.id != null && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" +
                "id = " + this.id + ")";
    }
}