package fr.jadde.database.entity;

import fr.jadde.database.entity.match.MatchDefinitionEntity;
import fr.jadde.database.entity.user.AbstractUser;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "teams")
public class TeamEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToMany(mappedBy = "teams")
    private Set<AbstractUser> users = new LinkedHashSet<>();

    public UUID getId() {
        return this.id;
    }

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchDefinitionEntity> matchDefinitions = new LinkedHashSet<>();

    public Set<AbstractUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AbstractUser> users) {
        this.users = users;
    }

    public Set<MatchDefinitionEntity> getMatchDefinitions() {
        return this.matchDefinitions;
    }

    public void setMatchDefinitions(final Set<MatchDefinitionEntity> matchDefinitions) {
        this.matchDefinitions = matchDefinitions;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final TeamEntity teamEntity = (TeamEntity) o;
        return this.id != null && Objects.equals(this.id, teamEntity.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}