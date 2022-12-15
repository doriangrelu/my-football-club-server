package fr.jadde.resource.mapper.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.jadde.exception.HttpErrorDetails;

import java.util.Collection;

public class ErrorResponse {

    private final int statusCode;

    private final String title;
    private final Collection<HttpErrorDetails> errors;

    public ErrorResponse(final int statusCode, final String title, final Collection<HttpErrorDetails> errors) {
        this.statusCode = statusCode;
        this.title = title;
        this.errors = errors;
    }

    @JsonProperty("statusCode")
    private int getStatusCode() {
        return statusCode;
    }

    @JsonProperty("title")
    private String getTitle() {
        return title;
    }

    @JsonProperty("errors")
    private Collection<HttpErrorDetails> getErrors() {
        return errors;
    }
}
