package cloud.nino.nino.service;

import cloud.nino.nino.domain.EventAndLocation;
import cloud.nino.nino.repository.EventAndLocationRepository;
import cloud.nino.nino.service.dto.EventAndLocationDTO;
import cloud.nino.nino.service.mapper.EventAndLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing EventAndLocation.
 */
@Service
@Transactional
public class EventAndLocationService {

    private final Logger log = LoggerFactory.getLogger(EventAndLocationService.class);

    private final EventAndLocationRepository eventAndLocationRepository;

    private final EventAndLocationMapper eventAndLocationMapper;

    public EventAndLocationService(EventAndLocationRepository eventAndLocationRepository, EventAndLocationMapper eventAndLocationMapper) {
        this.eventAndLocationRepository = eventAndLocationRepository;
        this.eventAndLocationMapper = eventAndLocationMapper;
    }

    /**
     * Save a eventAndLocation.
     *
     * @param eventAndLocationDTO the entity to save
     * @return the persisted entity
     */
    public EventAndLocationDTO save(EventAndLocationDTO eventAndLocationDTO) {
        log.debug("Request to save EventAndLocation : {}", eventAndLocationDTO);
        EventAndLocation eventAndLocation = eventAndLocationMapper.toEntity(eventAndLocationDTO);
        eventAndLocation = eventAndLocationRepository.save(eventAndLocation);
        return eventAndLocationMapper.toDto(eventAndLocation);
    }

    /**
     * Get all the eventAndLocations.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EventAndLocationDTO> findAll() {
        log.debug("Request to get all EventAndLocations");
        return eventAndLocationRepository.findAllWithEagerRelationships().stream()
            .map(eventAndLocationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the EventAndLocation with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<EventAndLocationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return eventAndLocationRepository.findAllWithEagerRelationships(pageable).map(eventAndLocationMapper::toDto);
    }


    /**
     * Get one eventAndLocation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EventAndLocationDTO> findOne(Long id) {
        log.debug("Request to get EventAndLocation : {}", id);
        return eventAndLocationRepository.findOneWithEagerRelationships(id)
            .map(eventAndLocationMapper::toDto);
    }

    /**
     * Delete the eventAndLocation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EventAndLocation : {}", id);
        eventAndLocationRepository.deleteById(id);
    }

}
