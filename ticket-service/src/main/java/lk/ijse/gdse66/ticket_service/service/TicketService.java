package lk.ijse.gdse66.ticket_service.service;

import lk.ijse.gdse66.ticket_service.dto.TicketDTO;

import java.util.List;

public interface TicketService {
    List<TicketDTO> getAllTickets();
    void saveTicket(TicketDTO dto);
    TicketDTO searchTicket(Long id);
    void updateTicket(TicketDTO dto);
    void deleteTicket(Long id);
}
