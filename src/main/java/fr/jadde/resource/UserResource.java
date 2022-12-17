package fr.jadde.resource;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;

@Path("/user/infos")
public class UserResource {
    @GET
    public Uni<?> get(final SecurityContext context) {
        return null;
    }

}
