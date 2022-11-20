package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.EssenzaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Essenza and its DTO EssenzaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EssenzaMapper extends EntityMapper<EssenzaDTO, Essenza> {



    default Essenza fromId(Long id) {
        if (id == null) {
            return null;
        }
        Essenza essenza = new Essenza();
        essenza.setId(id);
        return essenza;
    }
}
