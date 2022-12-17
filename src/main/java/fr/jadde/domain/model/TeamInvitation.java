package fr.jadde.domain.model;

import fr.jadde.database.entity.TeamInvitationStatus;

import java.time.LocalDateTime;

public record TeamInvitation(String id, String toEmail, LocalDateTime endAt, TeamInvitationStatus status,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
}
