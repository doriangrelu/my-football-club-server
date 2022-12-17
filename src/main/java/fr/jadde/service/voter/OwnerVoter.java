package fr.jadde.service.voter;

import fr.jadde.database.entity.TeamEntity;
import fr.jadde.service.spi.voter.AbstractVoter;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OwnerVoter extends AbstractVoter {

    @Override
    public boolean accept(final String attribute, final Object subject) {
        return this.getContext().contains("userIdentifier")
                && "action::createMatchDefinition".equals(attribute)
                && subject instanceof TeamEntity;
    }

    @Override
    public boolean voteOnContext(final String attribute, final Object subject) {
        final TeamEntity team = (TeamEntity) subject;
        return team.getOwner().getId().equals(this.getContext().<String>get("userIdentifier")
                .orElse(null)
        );
    }

}
