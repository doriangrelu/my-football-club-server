package fr.jadde.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class HttpPrintableException extends RuntimeException {

    private final int errorCode;

    private final String errorTitle;

    private final Collection<HttpErrorDetails> errors;


    protected HttpPrintableException(final int errorCode, final String errorTitle, final Collection<HttpErrorDetails> errors) {
        super(errorTitle);
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
        this.errors = new ArrayList<>(errors);
    }

    public static HttpErrorBuilder builder(final int errorCode, final String errorTitle) {
        return new HttpErrorBuilder(errorCode, errorTitle);
    }

    public static class HttpErrorBuilder {

        private final int errorCode;

        private final String errorTitle;

        private final Collection<HttpErrorDetails> details;

        protected HttpErrorBuilder(final int errorCode, final String errorTitle) {
            this.errorCode = errorCode;
            this.errorTitle = errorTitle;
            this.details = new ArrayList<>();
        }

        @SafeVarargs
        public final HttpErrorBuilder withError(final String errorKey, final Map.Entry<String, String>... flatParameters) {
            return this.withError(errorKey, Map.ofEntries(flatParameters));
        }

        public HttpErrorBuilder withError(final String errorKey, final Map<String, String> parameters) {
            this.details.add(new HttpErrorDetails(errorKey, parameters));
            return this;
        }

        public HttpPrintableException build() {
            return new HttpPrintableException(this.errorCode, this.errorTitle, this.details);
        }

        public void buildAndThrow() {
            throw this.build();
        }

    }

    public int errorCode() {
        return errorCode;
    }

    public String errorTitle() {
        return errorTitle;
    }

    public Collection<HttpErrorDetails> errors() {
        return java.util.List.copyOf(errors);
    }

}
