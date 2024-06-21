package lk.ijse.gdse66.payment_service.service;

import lk.ijse.gdse66.payment_service.dto.PaymentDTO;
import lk.ijse.gdse66.payment_service.dto.TicketDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getAllPayments();
    void savePayment(PaymentDTO dto);
    PaymentDTO searchPayment(Long id);
    void updatePayment(PaymentDTO dto);
    void deletePayment(Long id);
}
