package fr.jadde.domain.model.match;

import fr.jadde.domain.model.Team;
import fr.jadde.domain.model.scheduling.AbstractPlanning;

import java.util.Set;

public record MatchDefinition(
        long id,
        Team team,
        String label,
        short numberOfParticipant,

        short numberOfDaysBeforeOpening,

        Set<AbstractPlanning> plannings,

        Set<MatchInstance> instances
) {

}
