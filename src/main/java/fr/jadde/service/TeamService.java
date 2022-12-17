package fr.jadde.service;

import fr.jadde.database.entity.TeamEntity;
import fr.jadde.database.entity.UserEntity;
import fr.jadde.domain.command.team.CreateTeamCommand;
import fr.jadde.domain.model.Team;
import fr.jadde.exception.HttpPrintableException;
import fr.jadde.service.client.AuthoritySelfRemoteService;
import fr.jadde.service.mapper.TeamMapper;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TeamService {

    private final UserService userService;
    private final TeamMapper teamMapper;
    @Inject
    @RestClient
    AuthoritySelfRemoteService authoritySelfRemoteService;

    public TeamService(final UserService userService, final TeamMapper teamMapper) {
        this.userService = userService;
        this.teamMapper = teamMapper;
    }

    //todo remove this function used fo debug
    public Uni<List<Team>> getAllFromOwner(final String userIdentifier) {
        return TeamEntity.<TeamEntity>find("select distinct t from TeamEntity t inner join fetch t.owner o where o.id=?1", userIdentifier)
                .list()
                .map(this.teamMapper::from);
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

}
