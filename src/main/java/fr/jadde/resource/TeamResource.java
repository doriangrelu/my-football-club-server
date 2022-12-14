package fr.jadde.resource;

import fr.jadde.database.entity.TeamEntity;
import fr.jadde.domain.command.team.CreateTeamCommand;
import fr.jadde.service.TeamService;
import io.smallrye.mutiny.Uni;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.List;

@Path("/team")
public class TeamResource {

    private final TeamService teamService;

    public TeamResource(final TeamService teamService) {
        this.teamService = teamService;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<TeamEntity> create(final @Valid CreateTeamCommand command) {
        return this.teamService.create(command);
    }

    @GET
    @Produces("application/json")
    public Uni<List<TeamEntity>> all() {
        return this.teamService.getAll();
    }

}
