package fr.jadde.service.mapper;

import fr.jadde.database.entity.TeamInvitationEntity;
import fr.jadde.domain.model.TeamInvitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface TeamInvitationMapper {

    @Mapping(source = "teamInvitationStatus", target = "status")
    @Mapping(source = "limitDate", target = "endAt")
    TeamInvitation from(TeamInvitationEntity teamInvitationEntity);

}
