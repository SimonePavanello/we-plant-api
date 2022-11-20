package cloud.nino.nino.service.dto.custom;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by dawit on 22/08/2018.
 */
public class StopReached implements Serializable{
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
