package cloud.nino.nino.service.dto.custom;

import java.io.Serializable;

/**
 * Created by dawit on 04/08/2019.
 */
public class LatLon implements Serializable{
    private Double lat;
    private Double lon;


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "LatLon{" +
            "lat=" + lat +
            ", lon=" + lon +
            '}';
    }
}
