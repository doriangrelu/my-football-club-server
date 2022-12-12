package fr.jadde.resource;

import fr.jadde.database.entity.Team;
import fr.jadde.domain.command.CreateTeamCommand;
import fr.jadde.service.TeamService;
import io.smallrye.mutiny.Uni;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("/team")
public class TeamResource {

    private final TeamService teamService;

    public TeamResource(final TeamService teamService) {
        this.teamService = teamService;
    }

    @POST
    public Uni<Team> create(final @Valid CreateTeamCommand command) {
        return this.teamService.create(command);
    }

    @GET
    public Uni<List<Team>> all() {
        return this.teamService.getAll();
    }

}
