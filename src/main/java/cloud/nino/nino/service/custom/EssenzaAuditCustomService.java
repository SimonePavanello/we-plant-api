package cloud.nino.nino.service.custom;

import cloud.nino.nino.domain.EssenzaAudit;
import cloud.nino.nino.repository.custom.EssenzaAuditCustomRepository;
import cloud.nino.nino.service.dto.EssenzaAuditDTO;
import cloud.nino.nino.service.mapper.EssenzaAuditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EssenzaAudit.
 */
@Service
@Transactional
public class EssenzaAuditCustomService {

    private final Logger log = LoggerFactory.getLogger(EssenzaAuditCustomService.class);

    private final EssenzaAuditCustomRepository essenzaAuditCustomRepository;

    private final EssenzaAuditMapper essenzaAuditMapper;

    public EssenzaAuditCustomService(EssenzaAuditCustomRepository essenzaAuditCustomRepository, EssenzaAuditMapper essenzaAuditMapper) {
        this.essenzaAuditCustomRepository = essenzaAuditCustomRepository;
        this.essenzaAuditMapper = essenzaAuditMapper;
    }

    /**
     * Save a essenzaAudit.
     *
     * @param essenzaAuditDTO the entity to save
     * @return the persisted entity
     */
    public EssenzaAuditDTO save(EssenzaAuditDTO essenzaAuditDTO) {
        log.debug("Request to save EssenzaAudit : {}", essenzaAuditDTO);
        EssenzaAudit essenzaAudit = essenzaAuditMapper.toEntity(essenzaAuditDTO);
        essenzaAudit = essenzaAuditCustomRepository.save(essenzaAudit);
        return essenzaAuditMapper.toDto(essenzaAudit);
    }

    /**
     * Get all the essenzaAudits.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EssenzaAuditDTO> findAll() {
        log.debug("Request to get all EssenzaAudits");
        return essenzaAuditCustomRepository.findAll().stream()
            .map(essenzaAuditMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one essenzaAudit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EssenzaAuditDTO> findOne(Long id) {
        log.debug("Request to get EssenzaAudit : {}", id);
        return essenzaAuditCustomRepository.findById(id)
            .map(essenzaAuditMapper::toDto);
    }

    /**
     * Delete the essenzaAudit by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EssenzaAudit : {}", id);
        essenzaAuditCustomRepository.deleteById(id);
    }


    /**
     * Get last saved Essenza audit by Essenza id
     * @param id
     * @return
     */
    public Optional<EssenzaAuditDTO> getEssenzaAuditByEssenza(Long id) {
        return essenzaAuditCustomRepository.findFirstByEssenza_IdOrderByDataUltimoAggiornamentoDesc(id).map(essenzaAuditMapper::toDto);
    }
}
