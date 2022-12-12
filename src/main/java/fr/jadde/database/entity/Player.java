package fr.jadde.database.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "player")
public class Player extends PanacheEntity {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "player_matchInstances",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "matchInstances_id"))
    private Set<MatchInstance> matchInstances = new LinkedHashSet<>();

    public Set<MatchInstance> getMatchInstances() {
        return matchInstances;
    }

    public void setMatchInstances(final Set<MatchInstance> matchInstances) {
        this.matchInstances = matchInstances;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final Player player = (Player) o;
        return id != null && Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}