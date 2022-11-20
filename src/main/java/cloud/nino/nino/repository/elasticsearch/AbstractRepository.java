package cloud.nino.nino.repository.elasticsearch;


import cloud.nino.nino.domain.elasticsearch.ElasticsearchModel;
import cloud.nino.nino.service.util.ObjectMapperUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by dawit on 20/04/2018.
 */
public abstract class AbstractRepository<T extends ElasticsearchModel> {
    private final RestHighLevelClient restHighLevelClient;
    private final SearchRequest searchRequest;
    private final ObjectMapperUtil<T> objectMapperUtil;

    protected AbstractRepository(RestHighLevelClient restHighLevelClient, SearchRequest searchRequest, Class currentClass) {
        this.restHighLevelClient = restHighLevelClient;
        this.searchRequest = searchRequest;
        objectMapperUtil = new ObjectMapperUtil<>(currentClass);
    }

    public Collection<T> findBy(String field, Object value, boolean fuzziness) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(field, value);
        if (fuzziness) {
            matchQueryBuilder.fuzziness(Fuzziness.AUTO).prefixLength(3)
                .maxExpansions(10);
        }
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        return objectMapperUtil.parseListTo(searchResponse);

    }
    public Collection<T> findByPhrase(String field, Object value) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = new MatchPhraseQueryBuilder(field, value);
        sourceBuilder.query(matchPhraseQueryBuilder);
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        return objectMapperUtil.parseListTo(searchResponse);

    }

    public Optional<T> findOneById(String id){
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("_id", id);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse;
        try {

            searchResponse = restHighLevelClient.search(searchRequest);
            return objectMapperUtil.parseTo(searchResponse);
        } catch (IOException e) {
            return Optional.empty();
        }

    }

    public Collection<T> startWith(String field, String value) throws IOException{
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        PrefixQueryBuilder prefixQueryBuilder = new PrefixQueryBuilder(field, value);
        sourceBuilder.size(20);
        sourceBuilder.query(prefixQueryBuilder);
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        return objectMapperUtil.parseListTo(searchResponse);
    }

}
