package fr.jadde.database.entity;

import fr.jadde.database.entity.match.MatchInstanceEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_informations")
public class UserEntity extends PanacheEntityBase {

    @Id
    protected String id;

    @ManyToMany(mappedBy = "players", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<MatchInstanceEntity> matchSubscriptions = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "team_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<TeamEntity> memberTeams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<TeamEntity> ownTeams = new LinkedHashSet<>();

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }



    public Set<TeamEntity> getOwnTeams() {
        return ownTeams;
    }

    public void setOwnTeams(Set<TeamEntity> ownTeams) {
        this.ownTeams = ownTeams;
    }

    public Set<TeamEntity> getMemberTeams() {
        return memberTeams;
    }

    public void setMemberTeams(Set<TeamEntity> memberTeams) {
        this.memberTeams = memberTeams;
    }

    public Set<MatchInstanceEntity> getMatchSubscriptions() {
        return this.matchSubscriptions;
    }

    public void setMatchSubscriptions(final Set<MatchInstanceEntity> matchSubscriptions) {
        this.matchSubscriptions = matchSubscriptions;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final UserEntity that = (UserEntity) o;
        return this.id != null && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
