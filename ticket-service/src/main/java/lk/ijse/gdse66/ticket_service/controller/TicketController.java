package lk.ijse.gdse66.ticket_service.controller;

import lk.ijse.gdse66.ticket_service.dto.TicketDTO;
import lk.ijse.gdse66.ticket_service.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@CrossOrigin
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    RestTemplate restTemplate;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/getAll")
    public List<TicketDTO> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/search/{id}")
    public TicketDTO getTicket(@PathVariable("id") Long id) {
        return ticketService.searchTicket(id);
    }

    @PostMapping
    public ResponseEntity<Void> saveTicket(@RequestBody TicketDTO dto){
        ticketService.saveTicket(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTicket(@RequestBody TicketDTO dto) {
        ticketService.updateTicket(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(params = "Id")
    public ResponseEntity<Void> deleteTicket(@RequestParam("Id") Long Id) {
        ticketService.deleteTicket(Id);
        return ResponseEntity.noContent().build();
    }
}
