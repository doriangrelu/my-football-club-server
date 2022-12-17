package fr.jadde.service.spi.voter.impl;

import fr.jadde.service.spi.voter.Voter;
import fr.jadde.service.spi.voter.context.VoterContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class VoterCommand {

    private static final Logger logger = LoggerFactory.getLogger(VoterCommand.class);

    private Queue<Voter> makePriorityQueue(final Set<Voter> voters) {
        logger.debug("Create priority Queue with {} voters", voters.size());
        final Queue<Voter> voterPriorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.priority(), o1.priority()));
        voterPriorityQueue.addAll(new ArrayList<>(voters));
        return voterPriorityQueue;
    }

    private boolean resolveAndVote(final Queue<Voter> voters, final String attribute, final Object subject, final VoterContext context) {
        final Optional<Voter> voter = this.pollVoter(voters);

        logger.debug("Try to poll voter [{}] for [attribute: {}  |  subject: {}]", voter.orElse(null), attribute, subject);
        try {
            voter.ifPresent(v -> v.setContext(context));
            if (voter.isPresent() && voter.get().accept(attribute, subject)) {
                if (voter.get().voteOnContext(attribute, subject)) {
                    logger.debug("Voter [{}] vote TRUE [attribute: {}  |  subject: {}]", voter.get(), attribute, subject);
                    return this.resolveAndVote(voters, attribute, subject, context);
                }
                logger.info("Voter [{}] vote FALSE [attribute: {}  |  subject: {}]", voter.get(), attribute, subject);
                return false;
            }
            logger.warn("No voter present, vote TRUE [attribute: {}  |  subject: {}]", attribute, subject);
            return true;
        } finally {
            voter.ifPresent(Voter::unsetContext);
        }
    }

    private Optional<Voter> pollVoter(final Queue<Voter> voters) {
        return Optional.ofNullable(voters.poll());
    }

    public boolean execute(final String attributes, final Object subject, final VoterContext context, final Set<Voter> voters, final Class<?> origin) {

        logger.debug("Try to execute voter command [Attribute: {}  | Subject: {}] with {} voters from [{}]",
                attributes,
                subject,
                voters.size(),
                origin
        );

        return !this.resolveAndVote(
                this.makePriorityQueue(voters),
                attributes,
                subject,
                context
        );
    }
}
