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
public class MatchInstanceEntity extends PanacheEntity {

    @Column(name = "at", nullable = false)
    private LocalDateTime at;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "match_definition_id", nullable = false)
    private MatchDefinitionEntity matchDefinition;

    @ManyToMany(mappedBy = "matchInstances", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<PlayerEntity> players = new LinkedHashSet<>();

    public Set<PlayerEntity> getPlayers() {
        return this.players;
    }

    public void setPlayers(final Set<PlayerEntity> playerEntities) {
        this.players = playerEntities;
    }

    public MatchDefinitionEntity getMatchDefinition() {
        return this.matchDefinition;
    }

    public void setMatchDefinition(final MatchDefinitionEntity matchDefinitionEntity) {
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