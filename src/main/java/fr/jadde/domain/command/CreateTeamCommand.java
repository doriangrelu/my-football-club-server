package fr.jadde.domain.command;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CreateTeamCommand {

    private final String name;

    public CreateTeamCommand(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
