package cloud.nino.nino.service;

import cloud.nino.nino.domain.Poi;
import cloud.nino.nino.repository.PoiRepository;
import cloud.nino.nino.service.dto.PoiDTO;
import cloud.nino.nino.service.mapper.PoiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Poi.
 */
@Service
@Transactional
public class PoiService {

    private final Logger log = LoggerFactory.getLogger(PoiService.class);

    private final PoiRepository poiRepository;

    private final PoiMapper poiMapper;

    public PoiService(PoiRepository poiRepository, PoiMapper poiMapper) {
        this.poiRepository = poiRepository;
        this.poiMapper = poiMapper;
    }

    /**
     * Save a poi.
     *
     * @param poiDTO the entity to save
     * @return the persisted entity
     */
    public PoiDTO save(PoiDTO poiDTO) {
        log.debug("Request to save Poi : {}", poiDTO);
        Poi poi = poiMapper.toEntity(poiDTO);
        poi = poiRepository.save(poi);
        return poiMapper.toDto(poi);
    }

    /**
     * Get all the pois.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PoiDTO> findAll() {
        log.debug("Request to get all Pois");
        return poiRepository.findAll().stream()
            .map(poiMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one poi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PoiDTO> findOne(Long id) {
        log.debug("Request to get Poi : {}", id);
        return poiRepository.findById(id)
            .map(poiMapper::toDto);
    }

    /**
     * Delete the poi by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Poi : {}", id);
        poiRepository.deleteById(id);
    }
}
