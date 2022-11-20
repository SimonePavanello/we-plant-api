package cloud.nino.nino.service.dto.custom;

import cloud.nino.nino.service.dto.AlberoVisitDTO;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by dawit on 08/06/2019.
 */
public class AlberoVisitEssenza implements Serializable {
    String essenza;
    LinkedList<AlberoVisitDTO> alberoVisitDTOS;


    public AlberoVisitEssenza(String essenza, LinkedList<AlberoVisitDTO> alberoVisitDTOS) {
        this.essenza = essenza;
        this.alberoVisitDTOS = alberoVisitDTOS;
    }

    public AlberoVisitEssenza() {
    }

    public String getEssenza() {
        return essenza;
    }

    public void setEssenza(String essenza) {
        this.essenza = essenza;
    }

    public LinkedList<AlberoVisitDTO> getAlberoVisitDTOS() {
        return alberoVisitDTOS;
    }

    public void setAlberoVisitDTOS(LinkedList<AlberoVisitDTO> alberoVisitDTOS) {
        this.alberoVisitDTOS = alberoVisitDTOS;
    }
}
