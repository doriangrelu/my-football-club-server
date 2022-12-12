package fr.jadde.service;

import fr.jadde.database.entity.TeamEntity;
import fr.jadde.domain.command.team.CreateTeamCommand;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TeamService {

    public Uni<List<TeamEntity>> getAll() {
        return TeamEntity.findAll().list();
    }

    public Uni<TeamEntity> create(final CreateTeamCommand create) {
        final TeamEntity teamEntity = new TeamEntity();
        teamEntity.setName(create.getName());
        return teamEntity.persistAndFlush();
    }

}
