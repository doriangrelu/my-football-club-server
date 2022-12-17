package fr.jadde.database.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "team_invitations")
public class TeamInvitationEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TeamInvitationStatus teamInvitationStatus;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "token_identifier", nullable = false, unique = true)
    private String tokenIdentifier;

    @Column(name = "limit_date", nullable = false)
    private LocalDateTime limitDate;


    public LocalDateTime getLimitDate() {
        return this.limitDate;
    }

    public void setLimitDate(final LocalDateTime limitDate) {
        this.limitDate = limitDate;
    }

    public String getTokenIdentifier() {
        return this.tokenIdentifier;
    }

    public void setTokenIdentifier(final String tokenIdentifier) {
        this.tokenIdentifier = tokenIdentifier;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public TeamEntity getTeam() {
        return this.team;
    }

    public void setTeam(final TeamEntity team) {
        this.team = team;
    }

    public TeamInvitationStatus getTeamInvitationStatus() {
        return this.teamInvitationStatus;
    }

    public void setTeamInvitationStatus(final TeamInvitationStatus teamInvitationStatus) {
        this.teamInvitationStatus = teamInvitationStatus;
    }

    public UUID getId() {
        return this.id;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

}