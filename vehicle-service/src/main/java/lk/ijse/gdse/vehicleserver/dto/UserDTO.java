package lk.ijse.gdse.vehicleserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String nic;
    private String name;
    private String contactNo;

}
