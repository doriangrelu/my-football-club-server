package fr.jadde.resource;

import fr.jadde.domain.command.match.CreateDefinition;
import fr.jadde.domain.model.match.MatchDefinition;
import fr.jadde.service.MatchService;
import io.smallrye.mutiny.Uni;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;

@Path("/match")
@RolesAllowed("default-user")
public class MatchResource {

    private final MatchService matchService;

    public MatchResource(final MatchService matchService) {
        this.matchService = matchService;
    }


    @POST
    @Path("/definition")
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<MatchDefinition> createDefinition(final @Valid CreateDefinition definition, SecurityContext context) {
        return this.matchService.createDefinition(definition);
    }

    @GET
    @Path("/definition/{uuid}")
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<MatchDefinition> getDefinition(final @PathParam("uuid") UUID uuid) {
        return this.matchService.getDefinition(uuid);
    }

}
