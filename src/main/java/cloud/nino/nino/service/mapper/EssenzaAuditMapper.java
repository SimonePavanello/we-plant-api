package cloud.nino.nino.service.mapper;

import cloud.nino.nino.domain.*;
import cloud.nino.nino.service.dto.EssenzaAuditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EssenzaAudit and its DTO EssenzaAuditDTO.
 */
@Mapper(componentModel = "spring", uses = {EssenzaMapper.class, UserMapper.class})
public interface EssenzaAuditMapper extends EntityMapper<EssenzaAuditDTO, EssenzaAudit> {

    @Mapping(source = "essenza.id", target = "essenzaId")
    @Mapping(source = "modificatoDa.id", target = "modificatoDaId")
    @Mapping(source = "modificatoDa.email", target = "modificatoDaEmail")
    EssenzaAuditDTO toDto(EssenzaAudit essenzaAudit);

    @Mapping(source = "essenzaId", target = "essenza")
    @Mapping(source = "modificatoDaId", target = "modificatoDa")
    EssenzaAudit toEntity(EssenzaAuditDTO essenzaAuditDTO);

    default EssenzaAudit fromId(Long id) {
        if (id == null) {
            return null;
        }
        EssenzaAudit essenzaAudit = new EssenzaAudit();
        essenzaAudit.setId(id);
        return essenzaAudit;
    }
}
