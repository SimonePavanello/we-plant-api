package cloud.nino.nino.service;

import cloud.nino.nino.domain.Albero;
import cloud.nino.nino.repository.AlberoRepository;
import cloud.nino.nino.service.dto.AlberoDTO;
import cloud.nino.nino.service.mapper.AlberoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Albero.
 */
@Service
@Transactional
public class AlberoService {

    private final Logger log = LoggerFactory.getLogger(AlberoService.class);

    private final AlberoRepository alberoRepository;

    private final AlberoMapper alberoMapper;

    public AlberoService(AlberoRepository alberoRepository, AlberoMapper alberoMapper) {
        this.alberoRepository = alberoRepository;
        this.alberoMapper = alberoMapper;
    }

    /**
     * Save a albero.
     *
     * @param alberoDTO the entity to save
     * @return the persisted entity
     */
    public AlberoDTO save(AlberoDTO alberoDTO) {
        log.debug("Request to save Albero : {}", alberoDTO);
        Albero albero = alberoMapper.toEntity(alberoDTO);
        albero = alberoRepository.save(albero);
        return alberoMapper.toDto(albero);
    }

    /**
     * Get all the alberos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlberoDTO> findAll() {
        log.debug("Request to get all Alberos");
        return alberoRepository.findAll().stream()
            .map(alberoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one albero by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AlberoDTO> findOne(Long id) {
        log.debug("Request to get Albero : {}", id);
        return alberoRepository.findById(id)
            .map(alberoMapper::toDto);
    }

    /**
     * Delete the albero by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Albero : {}", id);
        alberoRepository.deleteById(id);
    }
}
