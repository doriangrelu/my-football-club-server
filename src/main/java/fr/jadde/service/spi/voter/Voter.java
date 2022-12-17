package fr.jadde.service.spi.voter;

import fr.jadde.service.spi.voter.context.VoterContext;

public interface Voter {
    boolean accept(final String attribute, Object subject);

    boolean voteOnContext(String attribute, Object subject);

    void setContext(VoterContext context);

    void unsetContext();

    /**
     * Donne le rang à utiliser
     * Plus le rang est petit, le tôt sera déclencher le voter
     *
     * @return rang
     */
    default int priority() {
        return -1;
    }
}
