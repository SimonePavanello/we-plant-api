package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.StopDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Stop and its DTO StopDTO.
 */
@Mapper(componentModel = "spring", uses = {VisitMapper.class})
public interface StopMapper extends EntityMapper<StopDTO, Stop> {

    @Mapping(source = "visit.id", target = "visitId")
    StopDTO toDto(Stop stop);

    @Mapping(source = "visitId", target = "visit")
    Stop toEntity(StopDTO stopDTO);

    default Stop fromId(Long id) {
        if (id == null) {
            return null;
        }
        Stop stop = new Stop();
        stop.setId(id);
        return stop;
    }
}
