package fr.jadde.resource;

import fr.jadde.domain.command.team.CreateTeamInvitationCommand;
import fr.jadde.domain.model.Team;
import fr.jadde.service.TeamService;
import fr.jadde.service.util.SecurityUtils;
import io.smallrye.mutiny.Uni;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;

@Path("/team/invitation")
public class TeamInvitationResource {

    private final TeamService teamService;

    public TeamInvitationResource(final TeamService teamService) {
        this.teamService = teamService;
    }

    @POST
    @Path("/{uuid}")
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<Team> create(final @Valid CreateTeamInvitationCommand command, final @PathParam("uuid") UUID teamIdentifier, final SecurityContext context) {
        return this.teamService.createTeamInvitation(command, SecurityUtils.extractUserId(context), teamIdentifier);
    }

}
