package lk.ijse.gdse66.ticket_service.dto;

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
