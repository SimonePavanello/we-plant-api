package cloud.nino.nino.service;

import cloud.nino.nino.domain.Essenza;
import cloud.nino.nino.repository.EssenzaRepository;
import cloud.nino.nino.service.dto.EssenzaDTO;
import cloud.nino.nino.service.mapper.EssenzaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Essenza.
 */
@Service
@Transactional
public class EssenzaService {

    private final Logger log = LoggerFactory.getLogger(EssenzaService.class);

    private final EssenzaRepository essenzaRepository;

    private final EssenzaMapper essenzaMapper;

    public EssenzaService(EssenzaRepository essenzaRepository, EssenzaMapper essenzaMapper) {
        this.essenzaRepository = essenzaRepository;
        this.essenzaMapper = essenzaMapper;
    }

    /**
     * Save a essenza.
     *
     * @param essenzaDTO the entity to save
     * @return the persisted entity
     */
    public EssenzaDTO save(EssenzaDTO essenzaDTO) {
        log.debug("Request to save Essenza : {}", essenzaDTO);
        Essenza essenza = essenzaMapper.toEntity(essenzaDTO);
        essenza = essenzaRepository.save(essenza);
        return essenzaMapper.toDto(essenza);
    }

    /**
     * Get all the essenzas.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EssenzaDTO> findAll() {
        log.debug("Request to get all Essenzas");
        return essenzaRepository.findAll().stream()
            .map(essenzaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one essenza by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EssenzaDTO> findOne(Long id) {
        log.debug("Request to get Essenza : {}", id);
        return essenzaRepository.findById(id)
            .map(essenzaMapper::toDto);
    }

    /**
     * Delete the essenza by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Essenza : {}", id);
        essenzaRepository.deleteById(id);
    }
}
