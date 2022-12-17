package fr.jadde.service.spi.voter.impl;

import fr.jadde.exception.DenyAccessException;
import fr.jadde.service.spi.voter.Voter;
import fr.jadde.service.spi.voter.VoterEngine;
import fr.jadde.service.spi.voter.VoterService;
import fr.jadde.service.spi.voter.context.VoterContext;
import io.quarkus.arc.All;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class VoterSystemImpl implements VoterEngine, VoterService {

    private final Set<Voter> voters;

    private final VoterCommand command;

    public VoterSystemImpl(final @All List<Voter> voters) {
        this.voters = new HashSet<>(voters);
        this.command = new VoterCommand();
    }

    @Override
    public VoterEngine addVoter(final Voter voter) {
        this.voters.add(voter);
        return this;
    }

    @Override
    public VoterEngine removeVoter(final Voter voter) {
        this.voters.remove(voter);
        return this;
    }


    @Override
    public void denyAccessUnlessGranted(final String attribute, final Object subject, final VoterContext context) throws DenyAccessException {
        if (this.isDenyAccessUnlessGranted(attribute, subject, context)) {
            throw new DenyAccessException();
        }
    }

    @Override
    public boolean isDenyAccessUnlessGranted(final String attribute, final Object subject, final VoterContext context) {
        return this.command.execute(attribute, subject, context, this.voters, this.getClass());
    }

}