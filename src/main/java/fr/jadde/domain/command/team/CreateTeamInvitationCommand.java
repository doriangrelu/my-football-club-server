package fr.jadde.domain.command.team;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record CreateTeamInvitationCommand(
        @Email @NotNull String email,
        String message
) {
}
