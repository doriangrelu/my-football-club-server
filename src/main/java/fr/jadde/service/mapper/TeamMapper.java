package fr.jadde.service.mapper;

import fr.jadde.database.entity.TeamEntity;
import fr.jadde.domain.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)

public interface TeamMapper {

    Team from(TeamEntity teamEntity);

}
