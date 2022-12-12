package fr.jadde.database.entity;

import fr.jadde.database.entity.scheduling.Planning;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "match_definitions")
public class MatchDefinition extends PanacheEntity {

    @Column(name = "label")
    private String label;

    @Column(name = "number_of_participant")
    private Short numberOfParticipant;

    @OneToMany(mappedBy = "matchDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Planning> plannings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "matchDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchInstance> matchInstances = new LinkedHashSet<>();

    public Set<MatchInstance> getMatchInstances() {
        return matchInstances;
    }

    public void setMatchInstances(final Set<MatchInstance> matchInstances) {
        this.matchInstances = matchInstances;
    }

    public Set<Planning> getPlannings() {
        return plannings;
    }

    public void setPlannings(final Set<Planning> plannings) {
        this.plannings = plannings;
    }

    public Short getNumberOfParticipant() {
        return numberOfParticipant;
    }

    public void setNumberOfParticipant(final Short numberOfParticipant) {
        this.numberOfParticipant = numberOfParticipant;
    }

    public String getLabel() {
        return label;
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
        final MatchDefinition that = (MatchDefinition) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}