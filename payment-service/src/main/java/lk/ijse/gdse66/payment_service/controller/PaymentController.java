package lk.ijse.gdse66.payment_service.controller;

import lk.ijse.gdse66.payment_service.dto.PaymentDTO;
import lk.ijse.gdse66.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    RestTemplate restTemplate;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/getAll")
    public List<PaymentDTO> getAllTickets() {
        return paymentService.getAllPayments();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/search/{id}")
    public PaymentDTO getTicket(@PathVariable("id") Long id) {
        return paymentService.searchPayment(id);
    }

    @PostMapping
    public ResponseEntity<Void> saveTicket(@RequestBody PaymentDTO dto){
        paymentService.savePayment(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTicket(@RequestBody PaymentDTO dto) {
        paymentService.updatePayment(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(params = "Id")
    public ResponseEntity<Void> deleteTicket(@RequestParam("Id") Long Id) {
        paymentService.deletePayment(Id);
        return ResponseEntity.noContent().build();
    }
}
