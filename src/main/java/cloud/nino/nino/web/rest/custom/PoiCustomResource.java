package cloud.nino.nino.web.rest.custom;

import cloud.nino.nino.domain.elasticsearch.Place;
import cloud.nino.nino.domain.enumeration.Amenity;
import cloud.nino.nino.domain.enumeration.Shop;
import cloud.nino.nino.domain.enumeration.Tourism;
import cloud.nino.nino.repository.elasticsearch.PlaceSearchRepository;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by dawit on 20/08/2018.
 */
@RestController
@RequestMapping("/api/custom")
public class PoiCustomResource {
    private final Logger log = LoggerFactory.getLogger(PoiCustomResource.class);

    private static final String ENTITY_NAME = "poi";

    private final PlaceSearchRepository placeSearchRepository;

    public PoiCustomResource(PlaceSearchRepository placeSearchRepository) {
        this.placeSearchRepository = placeSearchRepository;
    }

    /**
     * GET  /stops/:id : get the "id" stop.
     *
     * @param id the id of the stopDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stopDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pois/{id}")
    @Timed
    public ResponseEntity<Place> getPoi(@PathVariable String id) {
        log.debug("REST request to get Poi : {}", id);
        Optional<Place> stopDTO = placeSearchRepository.findOneById(id);
        return ResponseUtil.wrapOrNotFound(stopDTO);
    }

    /**
     * GET  /pois : search pois.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stops in body
     */
    @GetMapping("/pois/search-by-name/{search}")
    @Timed
    public Collection<Place> searchPoisByName(@PathVariable String search) throws IOException {
        log.debug("REST request to get all Stops");
        return placeSearchRepository.findBy("tags.name", search, false);
    }

    /**
     * GET  /pois/search-by-amenity/ : search pois.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stops in body
     */
    @GetMapping("/pois/search-by-amenity/{amenity}")
    @Timed
    public Collection<Place> searchPoisByAmenity(@PathVariable Amenity amenity ) throws IOException {
        log.debug("REST request to get all Stops by amenity");
        return placeSearchRepository.findBy("tags.amenity", amenity, false);
    }

    /**
     * GET  //pois/search-by-tourism/ : search pois.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stops in body
     */
    @GetMapping("/pois/search-by-tourism/{tourism}")
    @Timed
    public Collection<Place> searchPoisByTourism(@PathVariable Tourism tourism) throws IOException {
        log.debug("REST request to get all Stops by tourism");
        return placeSearchRepository.findBy("tags.tourism", tourism, false);
    }

    /**
     * GET  //pois/search-by-shop/ : search pois.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stops in body
     */
    @GetMapping("/pois/search-by-shop/{shop}")
    @Timed
    public Collection<Place> searchPoisByShop(@PathVariable Shop shop) throws IOException {
        log.debug("REST request to get all Stops by shop");
        return placeSearchRepository.findBy("tags.shop", shop, false);
    }
}
