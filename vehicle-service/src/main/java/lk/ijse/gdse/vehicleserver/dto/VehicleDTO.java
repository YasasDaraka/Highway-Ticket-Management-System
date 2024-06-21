package lk.ijse.gdse.vehicleserver.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private String licensePlate;
    private String model;
    private String user;

}
