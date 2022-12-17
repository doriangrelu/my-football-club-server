package fr.jadde.service.spi.voter;

import fr.jadde.service.spi.voter.context.VoterContext;

public abstract class AbstractVoter implements Voter {

    private final ThreadLocal<VoterContext> context = new ThreadLocal<>();

    @Override
    public void unsetContext() {
        this.context.remove();
    }

    protected VoterContext getContext() {
        return this.context.get();
    }

    @Override
    public void setContext(final VoterContext context) {
        this.context.set(context);
    }

}
