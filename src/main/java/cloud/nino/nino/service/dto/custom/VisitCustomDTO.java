package cloud.nino.nino.service.dto.custom;

import cloud.nino.nino.service.dto.StopDTO;
import cloud.nino.nino.service.dto.VisitDTO;
import com.google.maps.model.DirectionsResult;
import com.graphhopper.directions.api.client.model.RouteResponsePath;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the Visit entity.
 */
public class VisitCustomDTO extends VisitDTO implements Serializable {
    private Set<StopCustomDTO> stops;
    private StopCustomDTO nextStop;
    private Long nextStopTime;
    private RouteResponsePath routeResponsePath;
    private String timeMessage;
    private String routeUrl;
    private DirectionsResult directionsResult;
    private long minutesToGo;
    private StopCustomDTO endPoint;
    private StopCustomDTO startPoint;

    public VisitCustomDTO(VisitDTO visitDTO, StopDTO startPoint, StopDTO endPoint) {
        BeanUtils.copyProperties(visitDTO, this);
        super.setActive(visitDTO.isActive());
        super.setInProgress(visitDTO.isInProgress());
        if (visitDTO.isInProgress()) {
            long startTomeNow = visitDTO.getStartTime().until(ZonedDateTime.now(), ChronoUnit.MINUTES);
            long minutesToGo = visitDTO.getMaxVisitTime() - startTomeNow;
            this.setMinutesToGo(minutesToGo > 0 ? minutesToGo : 0);

        }
        if (endPoint != null) {
            this.endPoint = new StopCustomDTO(endPoint);
        }
        if (startPoint != null) {
            this.startPoint = new StopCustomDTO(startPoint);
        }
    }

    public StopCustomDTO getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(StopCustomDTO endPoint) {
        this.endPoint = endPoint;
    }

    public StopCustomDTO getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(StopCustomDTO startPoint) {
        this.startPoint = startPoint;
    }

    public long getMinutesToGo() {
        return minutesToGo;
    }

    public void setMinutesToGo(long minutesToGo) {
        this.minutesToGo = minutesToGo;
    }

    public DirectionsResult getDirectionsResult() {
        return directionsResult;
    }

    public void setDirectionsResult(DirectionsResult directionsResult) {
        this.directionsResult = directionsResult;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public String getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(String timeMessage) {
        this.timeMessage = timeMessage;
    }

    public Boolean isActive() {
        return super.isActive();
    }


    public Set<StopCustomDTO> getStops() {
        List<StopCustomDTO> stopCustomDTOS = stops.stream().collect(Collectors.toList());
        Collections.sort(stopCustomDTOS, (o1, o2) -> o1.getId().compareTo(o2.getId()));
        return new LinkedHashSet<>(stopCustomDTOS);
    }

    public void setStops(Set<StopCustomDTO> stops) {
        this.stops = stops;
    }

    public StopCustomDTO getNextStop() {
        return nextStop;
    }

    public void setNextStop(StopCustomDTO nextStop) {
        this.nextStop = nextStop;
    }

    public Long getNextStopTime() {
        return nextStopTime;
    }

    public void setNextStopTime(Long nextStopTime) {
        this.nextStopTime = nextStopTime;
    }

    public RouteResponsePath getRouteResponsePath() {
        return routeResponsePath;
    }

    public void setRouteResponsePath(RouteResponsePath routeResponsePath) {
        this.routeResponsePath = routeResponsePath;
    }

}
