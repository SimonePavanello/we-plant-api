package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.VisitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Visit and its DTO VisitDTO.
 */
@Mapper(componentModel = "spring", uses = {StopMapper.class, UserMapper.class, EventAndLocationMapper.class})
public interface VisitMapper extends EntityMapper<VisitDTO, Visit> {

    @Mapping(source = "startPoint.id", target = "startPointId")
    @Mapping(source = "endPoint.id", target = "endPointId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "eventAndlocation.id", target = "eventAndlocationId")
    VisitDTO toDto(Visit visit);

    @Mapping(source = "startPointId", target = "startPoint")
    @Mapping(source = "endPointId", target = "endPoint")
    @Mapping(target = "stops", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "eventAndlocationId", target = "eventAndlocation")
    Visit toEntity(VisitDTO visitDTO);

    default Visit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Visit visit = new Visit();
        visit.setId(id);
        return visit;
    }
}
