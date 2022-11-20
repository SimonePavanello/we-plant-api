package cloud.nino.nino.service;

import cloud.nino.nino.domain.Stop;
import cloud.nino.nino.repository.StopRepository;
import cloud.nino.nino.service.dto.StopDTO;
import cloud.nino.nino.service.mapper.StopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Stop.
 */
@Service
@Transactional
public class StopService {

    private final Logger log = LoggerFactory.getLogger(StopService.class);

    private final StopRepository stopRepository;

    private final StopMapper stopMapper;

    public StopService(StopRepository stopRepository, StopMapper stopMapper) {
        this.stopRepository = stopRepository;
        this.stopMapper = stopMapper;
    }

    /**
     * Save a stop.
     *
     * @param stopDTO the entity to save
     * @return the persisted entity
     */
    public StopDTO save(StopDTO stopDTO) {
        log.debug("Request to save Stop : {}", stopDTO);
        Stop stop = stopMapper.toEntity(stopDTO);
        stop = stopRepository.save(stop);
        return stopMapper.toDto(stop);
    }

    /**
     * Get all the stops.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StopDTO> findAll() {
        log.debug("Request to get all Stops");
        return stopRepository.findAll().stream()
            .map(stopMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stop by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StopDTO> findOne(Long id) {
        log.debug("Request to get Stop : {}", id);
        return stopRepository.findById(id)
            .map(stopMapper::toDto);
    }

    /**
     * Delete the stop by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Stop : {}", id);
        stopRepository.deleteById(id);
    }
}
