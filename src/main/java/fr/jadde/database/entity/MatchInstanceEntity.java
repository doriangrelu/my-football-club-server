package fr.jadde.database.entity;

import fr.jadde.database.entity.user.AbstractUser;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "match_instances")
public class MatchInstanceEntity extends PanacheEntity {

    @Column(name = "at", nullable = false)
    private LocalDateTime at;

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_definition_id", nullable = false)
    private MatchDefinitionEntity matchDefinition;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "match_subscriptions",
            joinColumns = @JoinColumn(name = "match_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<AbstractUser> players = new LinkedHashSet<>();

    public Set<AbstractUser> getPlayers() {
        return this.players;
    }

    public void setPlayers(final Set<AbstractUser> players) {
        this.players = players;
    }

    public MatchDefinitionEntity getMatchDefinition() {
        return this.matchDefinition;
    }

    public void setMatchDefinition(final MatchDefinitionEntity matchDefinitionEntity) {
        matchDefinitionEntity.getMatchInstances().add(this);
        this.matchDefinition = matchDefinitionEntity;
    }

    public LocalDateTime getAt() {
        return this.at;
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
        final MatchInstanceEntity that = (MatchInstanceEntity) o;
        return this.id != null && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}