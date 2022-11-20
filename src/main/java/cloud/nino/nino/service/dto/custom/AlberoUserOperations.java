package cloud.nino.nino.service.dto.custom;

import cloud.nino.nino.service.dto.UserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dawit on 08/06/2019.
 */
public class AlberoUserOperations implements Serializable {
    private UserDTO userDTO;
    List<AlberoCustomDTO> alberoCustomDTOS;

    public AlberoUserOperations(UserDTO userDTO, List<AlberoCustomDTO> alberoCustomDTOS) {
        this.userDTO = userDTO;
        this.alberoCustomDTOS = alberoCustomDTOS;
    }


    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<AlberoCustomDTO> getAlberoCustomDTOS() {
        return alberoCustomDTOS;
    }

    public void setAlberoCustomDTOS(List<AlberoCustomDTO> alberoCustomDTOS) {
        this.alberoCustomDTOS = alberoCustomDTOS;
    }
}
