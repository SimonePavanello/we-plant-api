package cloud.nino.nino.service;

import cloud.nino.nino.domain.Visit;
import cloud.nino.nino.repository.VisitRepository;
import cloud.nino.nino.service.dto.VisitDTO;
import cloud.nino.nino.service.mapper.VisitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Visit.
 */
@Service
@Transactional
public class VisitService {

    private final Logger log = LoggerFactory.getLogger(VisitService.class);

    private final VisitRepository visitRepository;

    private final VisitMapper visitMapper;

    public VisitService(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    /**
     * Save a visit.
     *
     * @param visitDTO the entity to save
     * @return the persisted entity
     */
    public VisitDTO save(VisitDTO visitDTO) {
        log.debug("Request to save Visit : {}", visitDTO);
        Visit visit = visitMapper.toEntity(visitDTO);
        visit = visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }

    /**
     * Get all the visits.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VisitDTO> findAll() {
        log.debug("Request to get all Visits");
        return visitRepository.findAll().stream()
            .map(visitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one visit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VisitDTO> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id)
            .map(visitMapper::toDto);
    }

    /**
     * Delete the visit by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Visit : {}", id);
        visitRepository.deleteById(id);
    }
}
