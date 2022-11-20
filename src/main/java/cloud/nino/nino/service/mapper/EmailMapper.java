package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.EmailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Email and its DTO EmailDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmailMapper extends EntityMapper<EmailDTO, Email> {



    default Email fromId(Long id) {
        if (id == null) {
            return null;
        }
        Email email = new Email();
        email.setId(id);
        return email;
    }
}
