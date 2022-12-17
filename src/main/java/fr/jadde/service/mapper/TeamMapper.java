package fr.jadde.service.mapper;

import fr.jadde.database.entity.TeamEntity;
import fr.jadde.domain.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = {
        UserMapper.class
})

public interface TeamMapper {

    Team from(TeamEntity teamEntity);

    List<Team> from(List<TeamEntity> teamEntity);

}
