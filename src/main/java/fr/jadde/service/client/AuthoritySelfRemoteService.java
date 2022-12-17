package fr.jadde.service.client;

import fr.jadde.domain.model.UserInformation;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@RegisterRestClient(configKey = "keycloak-remote-service")
@RegisterClientHeaders
public interface AuthoritySelfRemoteService {

    @GET
    @Path("/account")
    @Produces("application/json")
    Uni<UserInformation> getUserInfos();

}
