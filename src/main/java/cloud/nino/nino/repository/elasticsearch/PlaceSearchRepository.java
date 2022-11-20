package cloud.nino.nino.repository.elasticsearch;


import cloud.nino.nino.domain.elasticsearch.Place;
import cloud.nino.nino.service.util.ObjectMapperUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by dawit on 16/04/2018.
 */
@Repository
public class PlaceSearchRepository extends AbstractRepository<Place> {
    private final Logger log = LoggerFactory.getLogger(PlaceSearchRepository.class);
    private final SearchRequest placeRequest;
    private RestHighLevelClient restHighLevelClient;
    private final ObjectMapperUtil<Place> objectMapperUtil;
    private ArrayList<String> condiments = new ArrayList<>();

    public PlaceSearchRepository(RestHighLevelClient restHighLevelClient, SearchRequest placeSearchRequest) {
        super(restHighLevelClient, placeSearchRequest, Place.class);
        this.restHighLevelClient = restHighLevelClient;
        this.placeRequest = placeSearchRequest;
        objectMapperUtil = new ObjectMapperUtil(Place.class);
    }



}
