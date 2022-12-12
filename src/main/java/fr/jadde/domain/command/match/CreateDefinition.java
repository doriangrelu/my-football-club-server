package fr.jadde.domain.command.match;

public class CreateDefinition {

    private final int teamIdentifier;

    private final String label;


    public CreateDefinition(final int teamIdentifier, final String label) {
        this.teamIdentifier = teamIdentifier;
        this.label = label;
    }
}
