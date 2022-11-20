package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.EventAndLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EventAndLocation and its DTO EventAndLocationDTO.
 */
@Mapper(componentModel = "spring", uses = {NinoUserMapper.class, PoiMapper.class})
public interface EventAndLocationMapper extends EntityMapper<EventAndLocationDTO, EventAndLocation> {



    default EventAndLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventAndLocation eventAndLocation = new EventAndLocation();
        eventAndLocation.setId(id);
        return eventAndLocation;
    }
}
