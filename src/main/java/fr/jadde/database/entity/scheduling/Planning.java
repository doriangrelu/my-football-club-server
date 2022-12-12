package fr.jadde.database.entity.scheduling;

import fr.jadde.database.entity.MatchDefinition;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "plannings")
public class Planning extends MatchDefinition {

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_definition_id", nullable = false)
    private MatchDefinition matchDefinition;


    public MatchDefinition getMatchDefinition() {
        return matchDefinition;
    }

    public void setMatchDefinition(final MatchDefinition matchDefinition) {
        this.matchDefinition = matchDefinition;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final Planning planning = (Planning) o;
        return id != null && Objects.equals(id, planning.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}