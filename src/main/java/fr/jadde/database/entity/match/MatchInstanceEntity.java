package fr.jadde.database.entity.match;

import fr.jadde.database.entity.UserEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "match_instances")
public class MatchInstanceEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    public UUID getId() {
        return this.id;
    }

    @Column(name = "at", nullable = false)
    private LocalDate at;

    @ManyToOne(optional = false)
    @JoinColumn(name = "planning_id", nullable = false)
    private PlanningEntity planning;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "match_subscriptions",
            joinColumns = @JoinColumn(name = "match_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<UserEntity> players = new LinkedHashSet<>();

    public Set<UserEntity> getPlayers() {
        return this.players;
    }

    public void setPlayers(final Set<UserEntity> players) {
        this.players = players;
    }

    public PlanningEntity getPlanning() {
        return this.planning;
    }

    public void setPlanning(final PlanningEntity planning) {
        planning.getMatchInstances().add(this);
        this.planning = planning;
    }

    public LocalDate getAt() {
        return this.at;
    }

    public void setAt(final LocalDate at) {
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