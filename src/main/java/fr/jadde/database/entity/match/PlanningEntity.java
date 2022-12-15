package fr.jadde.database.entity.match;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "plannings")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PlanningEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    protected UUID id;

    public UUID getId() {
        return this.id;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_definition_id", nullable = false)
    private MatchDefinitionEntity matchDefinition;

    @OneToMany(mappedBy = "planning", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<MatchInstanceEntity> matchInstances = new LinkedHashSet<>();


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

    public Set<MatchInstanceEntity> getMatchInstances() {
        return this.matchInstances;
    }

    public void setMatchInstances(final Set<MatchInstanceEntity> matchInstanceEntities) {
        matchInstanceEntities.forEach(matchInstanceEntity -> matchInstanceEntity.setPlanning(this));
        this.matchInstances = matchInstanceEntities;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" +
                "id = " + this.id + ")";
    }


    public MatchDefinitionEntity getMatchDefinition() {
        return this.matchDefinition;
    }

    public void setMatchDefinition(final MatchDefinitionEntity matchDefinitionEntity) {
        matchDefinitionEntity.getPlannings().add(this);
        this.matchDefinition = matchDefinitionEntity;
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