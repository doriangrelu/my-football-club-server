package fr.jadde.service.util;

import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.ws.rs.core.SecurityContext;
import java.util.function.Function;

public class SecurityUtils {

    public static String extractUserId(final SecurityContext context) {
        return extract(context, JsonWebToken::getSubject);
    }

    public static <T> T extract(final SecurityContext context, Function<OidcJwtCallerPrincipal, T> extractor) {
        if (context.getUserPrincipal() instanceof OidcJwtCallerPrincipal jwtCallerPrincipal) {
            return extractor.apply(jwtCallerPrincipal);
        }
        throw new UnsupportedOperationException("Cannot introspect non JWT caller principal");
    }

}
