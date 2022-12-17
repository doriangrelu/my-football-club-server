package fr.jadde.service.mapper;

import fr.jadde.database.entity.UserEntity;
import fr.jadde.domain.model.UserInformation;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface UserMapper {

    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "email", ignore = true)
    UserInformation from(UserEntity playerEntity);

}
