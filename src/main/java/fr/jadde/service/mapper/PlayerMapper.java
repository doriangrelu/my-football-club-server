package fr.jadde.service.mapper;

import fr.jadde.database.entity.user.PlayerEntity;
import fr.jadde.domain.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface PlayerMapper {

    Player from(PlayerEntity playerEntity);

}
