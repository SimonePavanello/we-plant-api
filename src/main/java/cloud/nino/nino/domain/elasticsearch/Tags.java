package cloud.nino.nino.domain.elasticsearch;

import java.io.Serializable;

/**
 * Created by dawit on 19/08/2018.
 */
public class Tags implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
