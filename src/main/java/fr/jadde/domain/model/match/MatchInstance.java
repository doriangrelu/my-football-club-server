package fr.jadde.domain.model.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.jadde.domain.model.UserInformation;

import java.time.LocalDate;
import java.util.List;

public class MatchInstance {

    private final String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private final LocalDate at;
    private final List<UserInformation> players;

    private MatchStatus status;

    public MatchInstance(final String id, final LocalDate at, final List<UserInformation> players) {
        this.id = id;
        this.at = at;
        this.players = players;
    }

    public void setStatus(final MatchStatus status) {
        this.status = status;
    }

    @JsonProperty("status")
    public MatchStatus status() {
        return status;
    }

    @JsonProperty("id")
    public String id() {
        return id;
    }

    @JsonProperty("at")
    public LocalDate at() {
        return at;
    }

    @JsonProperty("players")
    public List<UserInformation> players() {
        return players;
    }
}
