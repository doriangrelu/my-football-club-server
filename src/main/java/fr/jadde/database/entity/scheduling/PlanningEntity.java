package fr.jadde.database.entity.scheduling;

import fr.jadde.database.entity.MatchDefinitionEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "plannings")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PlanningEntity extends PanacheEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_definition_id", nullable = false)
    private MatchDefinitionEntity matchDefinitionEntity;

    @Column(name = "start_at", nullable = false)
    private LocalDate startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDate endAt;

    @Column(name = "hour", nullable = false)
    private LocalTime hour;

    public LocalTime getHour() {
        return this.hour;
    }

    public void setHour(final LocalTime hour) {
        this.hour = hour;
    }

    public LocalDate getEndAt() {
        return this.endAt;
    }

    public void setEndAt(final LocalDate endAt) {
        this.endAt = endAt;
    }

    public LocalDate getStartAt() {
        return this.startAt;
    }

    public void setStartAt(final LocalDate startAt) {
        this.startAt = startAt;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" +
                "id = " + this.id + ")";
    }


    public MatchDefinitionEntity getMatchDefinition() {
        return this.matchDefinitionEntity;
    }

    public void setMatchDefinition(final MatchDefinitionEntity matchDefinitionEntity) {
        matchDefinitionEntity.getPlannings().add(this);
        this.matchDefinitionEntity = matchDefinitionEntity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final PlanningEntity planning = (PlanningEntity) o;
        return this.id != null && Objects.equals(this.id, planning.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}