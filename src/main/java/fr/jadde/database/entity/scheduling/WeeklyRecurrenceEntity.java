package fr.jadde.database.entity.scheduling;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "weekly_recurrence")
public class WeeklyRecurrenceEntity extends PlanningEntity {
    @Column(name = "day_number", nullable = false)
    private Short dayNumber;

    public Short getDayNumber() {
        return this.dayNumber;
    }

    public void setDayNumber(final Short dayNumber) {
        this.dayNumber = dayNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final WeeklyRecurrenceEntity that = (WeeklyRecurrenceEntity) o;
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