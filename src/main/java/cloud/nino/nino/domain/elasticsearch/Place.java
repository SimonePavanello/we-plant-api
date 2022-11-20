package cloud.nino.nino.domain.elasticsearch;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dawit on 19/08/2018.
 */
public class Place extends ElasticsearchModel implements Serializable {
    private BigDecimal lat;
    private BigDecimal lon;
    private Tags tags;

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }
}
