package fr.jadde.domain.model;

import fr.jadde.domain.model.scheduling.AbstractPlanning;

import java.util.Set;

public record MatchDefinition(
        long id,
        Team team,
        String label,
        short numberOfParticipant,
        Set<AbstractPlanning> plannings
) {

}
