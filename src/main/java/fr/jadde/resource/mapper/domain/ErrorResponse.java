package fr.jadde.resource.mapper.domain;

public class ErrorResponse {

    private final int statusCode;

    private final String[] messages;

    public ErrorResponse(final int statusCode, final String[] messages) {
        this.statusCode = statusCode;
        this.messages = messages;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String[] getMessages() {
        return this.messages;
    }
}
