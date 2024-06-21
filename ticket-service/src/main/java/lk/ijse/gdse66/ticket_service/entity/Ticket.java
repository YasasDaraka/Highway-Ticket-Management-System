package lk.ijse.gdse66.ticket_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {

    @Id
    private Long id;
    private String description;
    private String status;
    private String userId;
    private String vehicleId;

}
