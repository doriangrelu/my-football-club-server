package fr.jadde.domain.model.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.jadde.domain.model.Player;

import java.time.LocalDate;
import java.util.List;

public record MatchInstance(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate at,
        List<Player> players,
        MatchStatus status

) {
}
