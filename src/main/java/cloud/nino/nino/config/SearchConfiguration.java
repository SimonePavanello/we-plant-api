package cloud.nino.nino.config;

import org.elasticsearch.action.search.SearchRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dawit on 17/04/2018.
 */
@Configuration
public class SearchConfiguration {
    @Bean
    public SearchRequest placeSearchRequest() {
        SearchRequest searchRequest = new SearchRequest("places");
        searchRequest.types("place");
        return searchRequest;
    }



}
