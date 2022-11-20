package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.ImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Image and its DTO ImageDTO.
 */
@Mapper(componentModel = "spring", uses = {AlberoMapper.class, PoiMapper.class, UserMapper.class})
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {

    @Mapping(source = "albero.id", target = "alberoId")
    @Mapping(source = "albero.nomeComune", target = "alberoNomeComune")
    @Mapping(source = "poi.id", target = "poiId")
    @Mapping(source = "poi.name", target = "poiName")
    @Mapping(source = "cratedBy.id", target = "cratedById")
    ImageDTO toDto(Image image);

    @Mapping(source = "alberoId", target = "albero")
    @Mapping(source = "poiId", target = "poi")
    @Mapping(source = "cratedById", target = "cratedBy")
    Image toEntity(ImageDTO imageDTO);

    default Image fromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
