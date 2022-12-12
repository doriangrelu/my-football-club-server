package fr.jadde.domain.command.team;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.common.constraint.NotNull;

@RegisterForReflection
public class CreateTeamCommand {

    @JsonProperty("name")
    @NotNull
    private final String name;

    @JsonCreator
    public CreateTeamCommand(final @JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
