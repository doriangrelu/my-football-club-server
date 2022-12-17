package fr.jadde.service.mapper;

import fr.jadde.database.entity.match.MatchDefinitionEntity;
import fr.jadde.domain.command.match.CreateDefinition;
import fr.jadde.domain.model.match.MatchDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = {
        PlanningMapper.class,
        TeamMapper.class
})
public interface MatchDefinitionMapper {

    @Mapping(target = "team", ignore = true)
    MatchDefinitionEntity from(CreateDefinition definition);

    MatchDefinition from(MatchDefinitionEntity definitionEntity);

    List<MatchDefinition> from(List<MatchDefinitionEntity> definitionEntity);


}
