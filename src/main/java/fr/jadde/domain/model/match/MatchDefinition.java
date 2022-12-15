package fr.jadde.domain.model.match;

import fr.jadde.domain.model.Team;
import fr.jadde.domain.model.scheduling.AbstractPlanning;

import java.util.Set;
import java.util.UUID;

public record MatchDefinition(
        String id,
        Team team,
        String label,
        boolean chatOpened,
        short numberOfParticipant,

        short numberOfDaysBeforeOpening,

        Set<AbstractPlanning> plannings
) {

}
