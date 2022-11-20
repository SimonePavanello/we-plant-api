package cloud.nino.nino.service.custom;

import cloud.nino.nino.domain.Privacy;
import cloud.nino.nino.repository.PrivacyRepository;
import cloud.nino.nino.service.dto.PrivacyDTO;
import cloud.nino.nino.service.mapper.PrivacyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Privacy.
 */
@Service
@Transactional
public class PrivacyCustomService {

    private final Logger log = LoggerFactory.getLogger(PrivacyCustomService.class);

    private final PrivacyRepository privacyRepository;

    private final PrivacyMapper privacyMapper;

    public PrivacyCustomService(PrivacyRepository privacyRepository, PrivacyMapper privacyMapper) {
        this.privacyRepository = privacyRepository;
        this.privacyMapper = privacyMapper;
    }

    /**
     * Save a privacy.
     *
     * @param privacyDTO the entity to save
     * @return the persisted entity
     */
    public PrivacyDTO save(PrivacyDTO privacyDTO) {
        privacyDTO.setTime(ZonedDateTime.now());
        log.debug("Request to save Privacy : {}", privacyDTO);
        Privacy privacy = privacyMapper.toEntity(privacyDTO);
        privacy = privacyRepository.save(privacy);
        return privacyMapper.toDto(privacy);
    }

    /**
     * Get all the privacies.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrivacyDTO> findAll() {
        log.debug("Request to get all Privacies");
        return privacyRepository.findAll().stream()
            .map(privacyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one privacy by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PrivacyDTO> findOne(Long id) {
        log.debug("Request to get Privacy : {}", id);
        return privacyRepository.findById(id)
            .map(privacyMapper::toDto);
    }

    /**
     * Delete the privacy by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Privacy : {}", id);
        privacyRepository.deleteById(id);
    }
}
