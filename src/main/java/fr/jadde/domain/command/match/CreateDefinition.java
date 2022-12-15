package fr.jadde.domain.command.match;

import fr.jadde.domain.command.match.scheduling.AbstractPlanningModification;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public record CreateDefinition(
        @NotNull UUID teamIdentifier,
        @NotNull String label,
        @NotNull boolean chatOpened,
        @Range(min = 1, max = 10) short numberOfDaysBeforeOpening,
        @Range(min = 1, max = 22) @NotNull short numberOfParticipant,
        @NotNull Set<AbstractPlanningModification> plannings
) {
}
