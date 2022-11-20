package cloud.nino.nino.web.rest;

import cloud.nino.nino.service.custom.AlberoCustomService;
import cloud.nino.nino.service.dto.custom.LatLon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the AlberoResource REST controller.
 *
 * @see AlberoResource
 */
@RunWith(SpringRunner.class)
public class AlberoCustomResourceIntTest {


    private AlberoCustomService alberoCustomService = new AlberoCustomService(null, null, null, null, null, null, null, null, null, null);

    @Test
    public void testWktToLatLon() {
        LatLon latLon = alberoCustomService.wKTToLatLon("POINT (11.026412 45.44243)");
        System.out.println(latLon);
        assertEquals(latLon.getLat(), new Double(45.44243));
        assertEquals(latLon.getLon(), new Double(11.026412));
    }
}
