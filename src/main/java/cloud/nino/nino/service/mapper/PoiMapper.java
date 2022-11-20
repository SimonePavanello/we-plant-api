package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.PoiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Poi and its DTO PoiDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PoiMapper extends EntityMapper<PoiDTO, Poi> {


    @Mapping(target = "eventsAndlocations", ignore = true)
    Poi toEntity(PoiDTO poiDTO);

    default Poi fromId(Long id) {
        if (id == null) {
            return null;
        }
        Poi poi = new Poi();
        poi.setId(id);
        return poi;
    }
}
