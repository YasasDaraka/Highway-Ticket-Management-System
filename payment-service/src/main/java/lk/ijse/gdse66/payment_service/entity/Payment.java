package lk.ijse.gdse66.payment_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {

    @Id
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @CreationTimestamp
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private Long ticketId;

    @Column(nullable = false)
    private String status;

}
