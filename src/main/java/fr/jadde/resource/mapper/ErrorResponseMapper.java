package fr.jadde.resource.mapper;

import fr.jadde.resource.mapper.domain.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;
import java.util.NoSuchElementException;

@Provider
public class ErrorResponseMapper implements ExceptionMapper<NoSuchElementException> {
    @Override
    public Response toResponse(final NoSuchElementException e) {
        return Response.status(404)
                .entity(new ErrorResponse(404, e.getMessage(), Collections.emptyList()))
                .build();
    }
}
