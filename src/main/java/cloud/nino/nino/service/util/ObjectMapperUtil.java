package cloud.nino.nino.service.util;

import cloud.nino.nino.domain.elasticsearch.ElasticsearchModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * Created by dawit on 17/04/2018.
 */
public class ObjectMapperUtil<T extends ElasticsearchModel> {
    private final static Logger log = LoggerFactory.getLogger(ObjectMapperUtil.class);

    private final static ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    Class currentClass;

    public ObjectMapperUtil(Class currentClass) {
        this.currentClass = currentClass;
    }

    public Collection<T> parseListTo(SearchResponse searchResponse) {
        JavaType type = objectMapper.getTypeFactory().constructType(currentClass);
        Collection<T> list = new LinkedHashSet();
        SearchHits hits = searchResponse.getHits();
        Arrays.stream(hits.getHits()).forEach(documentFields -> {
            try {

                T element = objectMapper.readValue(documentFields.getSourceAsString(), type);
                element.set_id(documentFields.getId());
                element.setScore(documentFields.getScore());
                list.add(element);
            } catch (IOException e) {
                log.error("", e);
            }
        });
        return list;
    }

    public static String writeValueAsString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public Optional<T> parseTo(SearchResponse searchResponse) {
        return parseListTo(searchResponse).stream().findFirst();
    }
}
