package cloud.nino.nino.web.rest.custom;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api/custom")
public class ComuneProxyResources {

    private final Logger log = LoggerFactory.getLogger(ComuneProxyResources.class);
    private String server = "https://mapserver7.comune.verona.it/sigirest/api/values/areaverde_gps";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public ComuneProxyResources() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }


    @GetMapping("/area-verde/{lat}/{lon}")
    @Timed
    public ResponseEntity<String> get(@PathVariable String lat, @PathVariable String lon) {
        HttpEntity<String> requestEntity = new HttpEntity("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + "?Lat=" + lat + "&Lon=" + lon, HttpMethod.GET, requestEntity, String.class);
        return ResponseEntity.ok()
            .headers(responseEntity.getHeaders())
            .body(responseEntity.getBody());
    }
}
