package fr.jadde.database.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "match_instances")
public class MatchInstance extends PanacheEntity {

    @Column(name = "at", nullable = false)
    private LocalDateTime at;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "match_definition_id", nullable = false)
    private MatchDefinition matchDefinition;

    @ManyToMany(mappedBy = "matchInstances", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Player> players = new LinkedHashSet<>();

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(final Set<Player> players) {
        this.players = players;
    }

    public MatchDefinition getMatchDefinition() {
        return matchDefinition;
    }

    public void setMatchDefinition(final MatchDefinition matchDefinition) {
        this.matchDefinition = matchDefinition;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public void setAt(final LocalDateTime at) {
        this.at = at;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final MatchInstance that = (MatchInstance) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}