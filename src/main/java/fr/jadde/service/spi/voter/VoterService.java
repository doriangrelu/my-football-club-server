package fr.jadde.service.spi.voter;

import fr.jadde.exception.DenyAccessException;
import fr.jadde.service.spi.voter.context.VoterContext;

import java.util.HashMap;
import java.util.Map;

public interface VoterService {

    default void denyAccessUnlessGranted(String attribute, Object subject) throws DenyAccessException {
        this.denyAccessUnlessGranted(attribute, subject, new HashMap<>());
    }

    default void denyAccessUnlessGranted(String attribute, Object subject, Map<String, Object> context) throws DenyAccessException {
        this.denyAccessUnlessGranted(attribute, subject, VoterContext.build(context));
    }

    void denyAccessUnlessGranted(String attribute, Object subject, VoterContext context) throws DenyAccessException;

    default boolean isDenyAccessUnlessGranted(String attribute, Object subject) {
        return this.isDenyAccessUnlessGranted(attribute, subject, new HashMap<>());
    }

    default boolean isDenyAccessUnlessGranted(String attribute, Object subject, Map<String, Object> context) {
        return this.isDenyAccessUnlessGranted(attribute, subject, VoterContext.build(context));
    }

    boolean isDenyAccessUnlessGranted(String attribute, Object subject, VoterContext context);
}
