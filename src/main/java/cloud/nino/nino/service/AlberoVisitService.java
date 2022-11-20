package cloud.nino.nino.service;

import cloud.nino.nino.domain.AlberoVisit;
import cloud.nino.nino.repository.AlberoVisitRepository;
import cloud.nino.nino.service.dto.AlberoVisitDTO;
import cloud.nino.nino.service.mapper.AlberoVisitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing AlberoVisit.
 */
@Service
@Transactional
public class AlberoVisitService {

    private final Logger log = LoggerFactory.getLogger(AlberoVisitService.class);

    private final AlberoVisitRepository alberoVisitRepository;

    private final AlberoVisitMapper alberoVisitMapper;

    public AlberoVisitService(AlberoVisitRepository alberoVisitRepository, AlberoVisitMapper alberoVisitMapper) {
        this.alberoVisitRepository = alberoVisitRepository;
        this.alberoVisitMapper = alberoVisitMapper;
    }

    /**
     * Save a alberoVisit.
     *
     * @param alberoVisitDTO the entity to save
     * @return the persisted entity
     */
    public AlberoVisitDTO save(AlberoVisitDTO alberoVisitDTO) {
        log.debug("Request to save AlberoVisit : {}", alberoVisitDTO);
        AlberoVisit alberoVisit = alberoVisitMapper.toEntity(alberoVisitDTO);
        alberoVisit = alberoVisitRepository.save(alberoVisit);
        return alberoVisitMapper.toDto(alberoVisit);
    }

    /**
     * Get all the alberoVisits.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlberoVisitDTO> findAll() {
        log.debug("Request to get all AlberoVisits");
        return alberoVisitRepository.findAll().stream()
            .map(alberoVisitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one alberoVisit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AlberoVisitDTO> findOne(Long id) {
        log.debug("Request to get AlberoVisit : {}", id);
        return alberoVisitRepository.findById(id)
            .map(alberoVisitMapper::toDto);
    }

    /**
     * Delete the alberoVisit by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AlberoVisit : {}", id);
        alberoVisitRepository.deleteById(id);
    }
}
