package fr.jadde.service.mapper;

import fr.jadde.database.entity.MatchInstanceEntity;
import fr.jadde.domain.model.match.MatchInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI, uses = {
        PlayerMapper.class
})
public interface MatchInstanceMapper {

    @Mapping(target = "status", ignore = true)
    MatchInstance from(MatchInstanceEntity matchInstanceEntity);

}
