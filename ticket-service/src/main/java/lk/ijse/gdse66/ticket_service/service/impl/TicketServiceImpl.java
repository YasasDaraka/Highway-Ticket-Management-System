package lk.ijse.gdse66.ticket_service.service.impl;

import lk.ijse.gdse66.ticket_service.dto.TicketDTO;
import lk.ijse.gdse66.ticket_service.dto.UserDTO;
import lk.ijse.gdse66.ticket_service.dto.VehicleDTO;
import lk.ijse.gdse66.ticket_service.entity.Ticket;
import lk.ijse.gdse66.ticket_service.repo.TicketRepo;
import lk.ijse.gdse66.ticket_service.service.TicketService;
import lk.ijse.gdse66.ticket_service.service.exception.DuplicateRecordException;
import lk.ijse.gdse66.ticket_service.service.exception.NotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    private static final String user_service_url = "http://user-service/user/";
    private static final String vehicle_service_url = "http://vehicle-service/vehicle/";

    @Override
    public List<TicketDTO> getAllTickets() {
        return modelMapper.map(ticketRepo.findAll(), new TypeToken<ArrayList<TicketDTO>>() {}.getType());
    }

    @Override
    public void saveTicket(TicketDTO dto) {
        ticketRepo.findById(dto.getId()).ifPresentOrElse(
                ticket -> {
                    throw new DuplicateRecordException("Ticket Already Exist");
                },
                () -> {
                    try {
                        ResponseEntity<UserDTO> response = restTemplate.getForEntity(user_service_url + "search/" + dto.getUserId(), UserDTO.class);
                        if (response.getStatusCode() == HttpStatus.OK) {

                            try {
                                ResponseEntity<VehicleDTO> vehicleRes = restTemplate.getForEntity(vehicle_service_url + "search/" + dto.getVehicleId(), VehicleDTO.class);
                                if (vehicleRes.getStatusCode() == HttpStatus.OK) {
                                    ticketRepo.save(modelMapper.map(dto, Ticket.class));

                                } else {
                                    throw new NotFoundException("Vehicle does not exist.");
                                }

                            } catch (HttpClientErrorException e) {
                                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                                    throw new NotFoundException("Vehicle does not exist.");
                                }
                            }
                            catch (RestClientException e) {
                                throw new ServiceException("Unable to communicate with vehicle service");
                            }


                        } else {
                            throw new NotFoundException("User does not exist.");
                        }

                    } catch (HttpClientErrorException e) {
                        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                            throw new NotFoundException("User does not exist.");
                        }
                    }
                    catch (RestClientException e) {
                        throw new ServiceException("Unable to communicate with user service");
                    }
                });
    }

    @Override
    public TicketDTO searchTicket(Long id) {
        return (TicketDTO) ticketRepo.findById(id)
                .map(cus -> modelMapper.map(ticketRepo.findById(id), TicketDTO.class))
                .orElseThrow(() -> new NotFoundException("Ticket Not Exist"));
    }

    @Override
    public void updateTicket(TicketDTO dto) {
        ticketRepo.findById(dto.getId()).ifPresentOrElse(
                ticket -> {
                    try {
                        ResponseEntity<UserDTO> response = restTemplate.getForEntity(user_service_url + "search/" + dto.getUserId(), UserDTO.class);
                        if (response.getStatusCode() == HttpStatus.OK) {

                            try {
                                ResponseEntity<VehicleDTO> vehicleRes = restTemplate.getForEntity(vehicle_service_url + "search/" + dto.getVehicleId(), VehicleDTO.class);
                                if (vehicleRes.getStatusCode() == HttpStatus.OK) {
                                    ticketRepo.save(modelMapper.map(dto, Ticket.class));

                                } else {
                                    throw new NotFoundException("Vehicle does not exist.");
                                }

                            } catch (HttpClientErrorException e) {
                                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                                    throw new NotFoundException("Vehicle does not exist.");
                                }
                            }
                            catch (RestClientException e) {
                                throw new ServiceException("Unable to communicate with vehicle service");
                            }


                        } else {
                            throw new NotFoundException("User does not exist.");
                        }

                    } catch (HttpClientErrorException e) {
                        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                            throw new NotFoundException("User does not exist.");
                        }
                    }
                    catch (RestClientException e) {
                        throw new ServiceException("Unable to communicate with user service");
                    }
                },
                () -> {
                    throw new NotFoundException("Ticket Not Exist");
                });

    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepo.findById(id).ifPresentOrElse(
                ticket -> {
                    ticketRepo.deleteById(id);
                }
                ,
                () -> {
                    throw new NotFoundException("Ticket Not Exist");
                }
        );
    }
}
