package fr.jadde.service;

import fr.jadde.database.entity.Team;
import fr.jadde.domain.command.CreateTeamCommand;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TeamService {

    public Uni<List<Team>> getAll() {
        return Team.findAll().list();
    }

    public Uni<Team> create(final CreateTeamCommand create) {
        final Team team = new Team();
        team.setName(create.getName());
        return team.persistAndFlush();
    }

}
