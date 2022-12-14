package fr.jadde.resource;

import fr.jadde.domain.command.match.CreateDefinition;
import fr.jadde.domain.model.match.MatchDefinition;
import fr.jadde.service.MatchService;
import io.smallrye.mutiny.Uni;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/match")
public class MatchResource {

    private final MatchService matchService;

    public MatchResource(final MatchService matchService) {
        this.matchService = matchService;
    }


    @POST
    @Path("/definition")
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<MatchDefinition> createDefinition(final @Valid CreateDefinition definition) {
        return this.matchService.createDefinition(definition);
    }

}
