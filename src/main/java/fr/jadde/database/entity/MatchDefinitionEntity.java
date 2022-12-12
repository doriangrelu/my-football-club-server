package fr.jadde.database.entity;

import fr.jadde.database.entity.scheduling.PlanningEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "match_definitions")
public class MatchDefinitionEntity extends PanacheEntity {

    @Column(name = "label")
    private String label;

    @Column(name = "number_of_participant")
    private Short numberOfParticipant;

    @OneToMany(mappedBy = "matchDefinitionEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlanningEntity> plannings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "matchDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchInstanceEntity> matchInstances = new LinkedHashSet<>();

    public Set<MatchInstanceEntity> getMatchInstances() {
        return this.matchInstances;
    }

    public void setMatchInstances(final Set<MatchInstanceEntity> matchInstanceEntities) {
        this.matchInstances = matchInstanceEntities;
    }

    public Set<PlanningEntity> getPlannings() {
        return this.plannings;
    }

    public void setPlannings(final Set<PlanningEntity> plannings) {
        this.plannings = plannings;
    }

    public Short getNumberOfParticipant() {
        return this.numberOfParticipant;
    }

    public void setNumberOfParticipant(final Short numberOfParticipant) {
        this.numberOfParticipant = numberOfParticipant;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final MatchDefinitionEntity that = (MatchDefinitionEntity) o;
        return this.id != null && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}