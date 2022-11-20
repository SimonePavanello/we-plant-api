package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.AlberoVisitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AlberoVisit and its DTO AlberoVisitDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AlberoMapper.class})
public interface AlberoVisitMapper extends EntityMapper<AlberoVisitDTO, AlberoVisit> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "albero.id", target = "alberoId")
    AlberoVisitDTO toDto(AlberoVisit alberoVisit);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "alberoId", target = "albero")
    AlberoVisit toEntity(AlberoVisitDTO alberoVisitDTO);

    default AlberoVisit fromId(Long id) {
        if (id == null) {
            return null;
        }
        AlberoVisit alberoVisit = new AlberoVisit();
        alberoVisit.setId(id);
        return alberoVisit;
    }
}
