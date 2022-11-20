package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.PrivacyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Privacy and its DTO PrivacyDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PrivacyMapper extends EntityMapper<PrivacyDTO, Privacy> {

    @Mapping(source = "user.id", target = "userId")
    PrivacyDTO toDto(Privacy privacy);

    @Mapping(source = "userId", target = "user")
    Privacy toEntity(PrivacyDTO privacyDTO);

    default Privacy fromId(Long id) {
        if (id == null) {
            return null;
        }
        Privacy privacy = new Privacy();
        privacy.setId(id);
        return privacy;
    }
}
