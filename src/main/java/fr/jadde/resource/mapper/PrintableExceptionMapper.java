package fr.jadde.resource.mapper;

import fr.jadde.exception.HttpPrintableException;
import fr.jadde.resource.mapper.domain.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PrintableExceptionMapper implements ExceptionMapper<HttpPrintableException> {

    @Override
    public Response toResponse(final HttpPrintableException e) {
        return Response.status(404)
                .entity(new ErrorResponse(e.errorCode(), e.errorTitle(), e.errors()))
                .build();
    }

}
