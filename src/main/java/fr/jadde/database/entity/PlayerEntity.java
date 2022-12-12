package fr.jadde.database.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "player")
public class PlayerEntity extends PanacheEntity {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "player_matchInstances",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "matchInstances_id"))
    private Set<MatchInstanceEntity> matchInstances = new LinkedHashSet<>();

    public Set<MatchInstanceEntity> getMatchInstances() {
        return this.matchInstances;
    }

    public void setMatchInstances(final Set<MatchInstanceEntity> matchInstanceEntities) {
        this.matchInstances = matchInstanceEntities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final PlayerEntity playerEntity = (PlayerEntity) o;
        return this.id != null && Objects.equals(this.id, playerEntity.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}