package fr.jadde.service.mapper;

import fr.jadde.database.entity.MatchDefinitionEntity;
import fr.jadde.domain.command.match.CreateDefinition;
import fr.jadde.domain.model.match.MatchDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = {
        PlanningMapper.class,
        TeamMapper.class
})
public interface MatchDefinitionMapper {

    MatchDefinitionEntity from(CreateDefinition definition);

    @Mapping(source = "matchInstances", target = "instances")
    MatchDefinition from(MatchDefinitionEntity definitionEntity);

}
