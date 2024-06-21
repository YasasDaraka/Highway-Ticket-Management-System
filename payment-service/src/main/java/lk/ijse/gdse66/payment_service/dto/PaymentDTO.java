package lk.ijse.gdse66.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Long id;
    private Double amount;
    private LocalDateTime paymentDate;
    private Long ticketId;
    private String status;

}
