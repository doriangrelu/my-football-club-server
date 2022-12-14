package fr.jadde.domain.command.match;

import fr.jadde.domain.command.match.scheduling.AbstractPlanningModification;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Set;

@RegisterForReflection
public record CreateDefinition(
        @NotNull long teamIdentifier,
        @NotNull String label,
        @Range(min = 1, max = 22) @NotNull short numberOfParticipant,
        @NotNull Set<AbstractPlanningModification> plannings
) {

    public CreateDefinition(final long teamIdentifier, final String label, final short numberOfParticipant, final Set<AbstractPlanningModification> plannings) {
        this.teamIdentifier = teamIdentifier;
        this.label = label;
        this.numberOfParticipant = numberOfParticipant;
        this.plannings = plannings;
    }

    @Override
    public long teamIdentifier() {
        return this.teamIdentifier;
    }

    @Override
    public Set<AbstractPlanningModification> plannings() {
        return this.plannings;
    }

    @Override
    public String label() {
        return this.label;
    }

    @Override
    public short numberOfParticipant() {
        return this.numberOfParticipant;
    }
}
