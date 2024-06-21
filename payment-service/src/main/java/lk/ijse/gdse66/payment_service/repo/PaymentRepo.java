package lk.ijse.gdse66.payment_service.repo;

import lk.ijse.gdse66.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
