package lk.ijse.gdse.vehicleserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {

    @Id
    private String licensePlate;
    private String model;
    private String user;

}
