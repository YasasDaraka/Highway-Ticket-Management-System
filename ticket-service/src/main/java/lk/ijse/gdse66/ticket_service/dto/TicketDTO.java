package lk.ijse.gdse66.ticket_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Long id;
    private String description;
    private String status;
    private String userId;
    private String vehicleId;
}
