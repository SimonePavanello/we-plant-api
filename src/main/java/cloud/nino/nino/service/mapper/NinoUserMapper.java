package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.NinoUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NinoUser and its DTO NinoUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NinoUserMapper extends EntityMapper<NinoUserDTO, NinoUser> {

    @Mapping(source = "user.id", target = "userId")
    NinoUserDTO toDto(NinoUser ninoUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "administeredEventsAndLocations", ignore = true)
    NinoUser toEntity(NinoUserDTO ninoUserDTO);

    default NinoUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        NinoUser ninoUser = new NinoUser();
        ninoUser.setId(id);
        return ninoUser;
    }
}
