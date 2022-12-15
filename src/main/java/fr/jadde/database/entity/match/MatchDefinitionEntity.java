package fr.jadde.database.entity.match;

import fr.jadde.database.entity.TeamEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "match_definitions")
public class MatchDefinitionEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    public UUID getId() {
        return this.id;
    }

    @Column(name = "label")
    private String label;


    @Column(name = "number_of_participant")
    private Short numberOfParticipant;

    @OneToMany(mappedBy = "matchDefinition", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<PlanningEntity> plannings = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    @Column(name = "number_of_days_before_opening", nullable = false)
    private Short numberOfDaysBeforeOpening;

    @Column(name = "chat_opened", nullable = false)
    private Boolean chatOpened = false;


    public Boolean getChatOpened() {
        return this.chatOpened;
    }

    public void setChatOpened(final Boolean chatOpened) {
        this.chatOpened = chatOpened;
    }

    public Short getNumberOfDaysBeforeOpening() {
        return this.numberOfDaysBeforeOpening;
    }

    public void setNumberOfDaysBeforeOpening(final Short numberOfDaysBeforeOpening) {
        this.numberOfDaysBeforeOpening = numberOfDaysBeforeOpening;
    }

    public TeamEntity getTeam() {
        return this.team;
    }

    public void setTeam(final TeamEntity team) {
        this.team = team;
    }

    public Set<PlanningEntity> getPlannings() {
        return this.plannings;
    }

    public void setPlannings(final Set<PlanningEntity> plannings) {
        plannings.forEach(planningEntity -> planningEntity.setMatchDefinition(this));
        this.plannings = plannings;
    }

    public Short getNumberOfParticipant() {
        return this.numberOfParticipant;
    }

    public void setNumberOfParticipant(final Short numberOfParticipant) {
        this.numberOfParticipant = numberOfParticipant;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final MatchDefinitionEntity that = (MatchDefinitionEntity) o;
        return this.id != null && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}