package fr.jadde.service;

import fr.jadde.database.entity.TeamEntity;
import fr.jadde.database.entity.TeamInvitationEntity;
import fr.jadde.database.entity.TeamInvitationStatus;
import fr.jadde.database.entity.UserEntity;
import fr.jadde.domain.command.team.CreateTeamCommand;
import fr.jadde.domain.command.team.CreateTeamInvitationCommand;
import fr.jadde.domain.model.Team;
import fr.jadde.domain.model.TeamInvitation;
import fr.jadde.exception.HttpPrintableException;
import fr.jadde.service.mapper.TeamInvitationMapper;
import fr.jadde.service.mapper.TeamMapper;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class TeamService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);

    private final UserService userService;
    private final TeamMapper teamMapper;

    private final TeamInvitationMapper teamInvitationMapper;

    @ConfigProperty(name = "token.team.invitation.secret")
    String invitationSecret;

    public TeamService(final UserService userService, final TeamMapper teamMapper, final TeamInvitationMapper teamInvitationMapper) {
        this.userService = userService;
        this.teamMapper = teamMapper;
        this.teamInvitationMapper = teamInvitationMapper;
    }

    //todo remove this function used fo debug
    public Uni<List<Team>> getAllFromOwner(final String userIdentifier) {
        return TeamEntity.<TeamEntity>find("select distinct t from TeamEntity t inner join fetch t.owner o where o.id=?1", userIdentifier)
                .list()
                .map(this.teamMapper::from);
    }

    public Uni<Team> addTeamMember() {

        return null;
    }

    public Uni<Team> create(final String userOwnerIdentifier, final CreateTeamCommand create) {
        return this.userService.createIfNecessary(userOwnerIdentifier)
                .onItem()
                .ifNull()
                .failWith(HttpPrintableException.builder(404, "Not found").build())
                .chain(userEntity -> {
                    if (userEntity.getOwnTeams().stream().anyMatch(teamEntity -> teamEntity.getName().equals(create.getName()))) {
                        return Uni.createFrom().failure(
                                HttpPrintableException.builder(400, "Bad request")
                                        .withError("Team already exists with name")
                                        .build()
                        );
                    }
                    return Uni.createFrom().item(userEntity);
                })
                .chain(user -> {
                    final TeamEntity teamEntity = new TeamEntity();
                    teamEntity.setName(create.getName());
                    teamEntity.setOwner(user);
                    return user.<UserEntity>persistAndFlush().map(dummy -> teamEntity);
                })
                .map(this.teamMapper::from);
    }

    public Uni<TeamInvitation> createTeamInvitation(final CreateTeamInvitationCommand command, final String userIdentifier, final UUID teamIdentifier) {
        final AtomicReference<String> jwtToken = new AtomicReference<>();
        return TeamEntity.<TeamEntity>find("from TeamEntity t inner join fetch t.owner o where o.id=?1 and t.id=?2", userIdentifier, teamIdentifier)
                .firstResult()
                .onItem()
                .ifNull()
                .failWith(() -> HttpPrintableException.builder(404, "Not found").build())
                .chain(team -> {
                    final Duration duration = Duration.of(15, ChronoUnit.DAYS);
                    final LocalDateTime now = LocalDateTime.now();
                    final Pair<String, String> token = this.generateTokenInvitation(userIdentifier, duration);

                    final TeamInvitationEntity invitation = new TeamInvitationEntity();
                    invitation.setTeam(team);
                    invitation.setTeamInvitationStatus(TeamInvitationStatus.PENDING);
                    invitation.setLimitDate(now.toLocalDate().atStartOfDay().plusDays(duration.toDays()));
                    invitation.setToEmail(command.email());
                    invitation.setTokenIdentifier(token.getLeft());

                    jwtToken.set(token.getRight());

                    return invitation.<TeamInvitationEntity>persistAndFlush();
                })
                .invoke(o -> {
                    this.handleSendInvitation(command.email(), command.message(), jwtToken.get());
                })
                .map(this.teamInvitationMapper::from);
    }

    private void handleSendInvitation(final String userEmail, final String message, final String token) {
        // todo send toEmail !
        LOGGER.info("Handle send invitation (token: '{}') to user {} with message '{}'", token, userEmail, message);
    }

    private Pair<String, String> generateTokenInvitation(final String userIdentifier, final Duration expiration) {
        final String jti = UUID.randomUUID().toString();
        return Pair.of(jti, Jwt.claim("typ", "team_invitation")
                .subject(userIdentifier)
                .claim(Claims.jti.name(), jti)
                .expiresIn(expiration)
                .signWithSecret(this.invitationSecret)
        );
    }

}
