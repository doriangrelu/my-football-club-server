package fr.jadde.resource;

import fr.jadde.domain.command.team.CreateTeamCommand;
import fr.jadde.domain.model.Team;
import fr.jadde.service.TeamService;
import fr.jadde.service.util.SecurityUtils;
import io.smallrye.mutiny.Uni;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/team")
@RolesAllowed("default-user")
public class TeamResource {

    private final TeamService teamService;

    public TeamResource(final TeamService teamService) {
        this.teamService = teamService;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<Team> create(final @Valid CreateTeamCommand command, final SecurityContext context) {
        return this.teamService.create(SecurityUtils.extractUserId(context), command);
    }

    @GET
    @Produces("application/json")
    public Uni<List<Team>> all() {
        return this.teamService.getAll();
    }

}
