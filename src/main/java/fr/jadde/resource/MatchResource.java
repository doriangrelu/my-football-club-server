package fr.jadde.resource;

import fr.jadde.domain.command.match.CreateDefinition;
import fr.jadde.domain.model.match.MatchDefinition;
import fr.jadde.service.MatchService;
import fr.jadde.service.spi.voter.VoterService;
import fr.jadde.service.util.SecurityUtils;
import io.smallrye.mutiny.Uni;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.UUID;

@Path("/match")
@RolesAllowed("default-user")
public class MatchResource {

    private final MatchService matchService;

    private final VoterService voterService;

    public MatchResource(final MatchService matchService, final VoterService voterService) {
        this.matchService = matchService;
        this.voterService = voterService;
    }


    @POST
    @Path("/definition")
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<MatchDefinition> createDefinition(final @Valid CreateDefinition definition, final SecurityContext context) {
        return this.matchService.createDefinition(SecurityUtils.extractUserId(context), definition);
    }

    @GET
    @Path("/definition/{uuid}")
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<MatchDefinition> get(final @PathParam("uuid") UUID uuid) {
        return this.matchService.getDefinition(uuid);
    }

    @GET
    @Path("/definitions")
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<List<MatchDefinition>> getAll(final SecurityContext context, final @QueryParam("teamId") UUID teamIdentifier) {
        return this.matchService.getAllDefinitions(SecurityUtils.extractUserId(context), teamIdentifier);
    }

}
