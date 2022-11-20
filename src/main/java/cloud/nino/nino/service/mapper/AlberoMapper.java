package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.AlberoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Albero and its DTO AlberoDTO.
 */
@Mapper(componentModel = "spring", uses = {EssenzaMapper.class, UserMapper.class})
public interface AlberoMapper extends EntityMapper<AlberoDTO, Albero> {

    @Mapping(source = "essenza.id", target = "essenzaId")
    @Mapping(source = "essenza.nomeComune", target = "essenzaNomeComune")
    @Mapping(source = "modificatoDa.id", target = "modificatoDaId")
    @Mapping(source = "modificatoDa.email", target = "modificatoDaEmail")
    @Mapping(source = "main.id", target = "mainId")
    AlberoDTO toDto(Albero albero);

    @Mapping(source = "essenzaId", target = "essenza")
    @Mapping(source = "modificatoDaId", target = "modificatoDa")
    @Mapping(source = "mainId", target = "main")
    Albero toEntity(AlberoDTO alberoDTO);

    default Albero fromId(Long id) {
        if (id == null) {
            return null;
        }
        Albero albero = new Albero();
        albero.setId(id);
        return albero;
    }
}
