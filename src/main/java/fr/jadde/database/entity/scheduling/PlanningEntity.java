package fr.jadde.database.entity.scheduling;

import fr.jadde.database.entity.MatchDefinitionEntity;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "plannings")
public class PlanningEntity extends MatchDefinitionEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_definition_id", nullable = false)
    private MatchDefinitionEntity matchDefinitionEntity;


    public MatchDefinitionEntity getMatchDefinition() {
        return this.matchDefinitionEntity;
    }

    public void setMatchDefinition(final MatchDefinitionEntity matchDefinitionEntity) {
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