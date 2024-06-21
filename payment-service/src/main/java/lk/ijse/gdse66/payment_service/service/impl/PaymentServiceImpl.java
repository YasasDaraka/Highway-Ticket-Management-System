package lk.ijse.gdse66.payment_service.service.impl;

import lk.ijse.gdse66.payment_service.dto.PaymentDTO;
import lk.ijse.gdse66.payment_service.dto.TicketDTO;
import lk.ijse.gdse66.payment_service.entity.Payment;
import lk.ijse.gdse66.payment_service.repo.PaymentRepo;
import lk.ijse.gdse66.payment_service.service.PaymentService;
import lk.ijse.gdse66.payment_service.service.exception.DuplicateRecordException;
import lk.ijse.gdse66.payment_service.service.exception.NotFoundException;
import lk.ijse.gdse66.payment_service.service.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    private static final String ticket_service_url = "http://ticket-service/ticket";

    @Override
    public List<PaymentDTO> getAllPayments() {
        return modelMapper.map(paymentRepo.findAll(), new TypeToken<ArrayList<PaymentDTO>>() {
        }.getType());
    }

    @Override
    public void savePayment(PaymentDTO dto) {
        paymentRepo.findById(dto.getId()).ifPresentOrElse(
                ticket -> {
                    throw new DuplicateRecordException("Payment Already Exist");
                },
                () -> {
                    try {
                        ResponseEntity<TicketDTO> response = restTemplate.getForEntity(ticket_service_url + "/search/" + dto.getTicketId(), TicketDTO.class);
                        if (response.getStatusCode() == HttpStatus.OK) {
                            TicketDTO body = response.getBody();
                            body.setStatus("Payment Success");

                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            HttpEntity<TicketDTO> requestEntity = new HttpEntity<>(body, headers);

                            ResponseEntity<Void> ticketRes = restTemplate.exchange(ticket_service_url, HttpMethod.PUT, requestEntity, Void.class);
                            if (ticketRes.getStatusCode().is2xxSuccessful() || ticketRes.getStatusCode().value() == 204) {
                                paymentRepo.save(modelMapper.map(dto, Payment.class));
                            } else {
                                throw new RuntimeException("Failed to update ticket status");
                            }

                        } else {
                            throw new NotFoundException("Ticket does not exist.");
                        }

                    } catch (HttpClientErrorException e) {
                        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                            throw new NotFoundException("Ticket does not exist.");
                        }
                    } catch (RestClientException e) {
                        throw new ServiceException("Unable to communicate with Ticket service");
                    }
                });
    }

    @Override
    public PaymentDTO searchPayment(Long id) {
        return (PaymentDTO) paymentRepo.findById(id)
                .map(payment -> modelMapper.map(paymentRepo.findById(id), PaymentDTO.class))
                .orElseThrow(() -> new NotFoundException("Payment Not Exist"));
    }

    @Override
    public void updatePayment(PaymentDTO dto) {
        paymentRepo.findById(dto.getId()).ifPresentOrElse(
                payment -> {
                    try {
                        ResponseEntity<TicketDTO> response = restTemplate.getForEntity(ticket_service_url + "/search/" + dto.getTicketId(), TicketDTO.class);
                        if (response.getStatusCode() == HttpStatus.OK) {
                            TicketDTO body = response.getBody();
                            body.setStatus("Payment Success");

                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            HttpEntity<TicketDTO> requestEntity = new HttpEntity<>(body, headers);

                            ResponseEntity<Void> ticketRes = restTemplate.exchange(ticket_service_url, HttpMethod.PUT, requestEntity, Void.class);
                            if (ticketRes.getStatusCode().is2xxSuccessful() || ticketRes.getStatusCode().value() == 204) {
                                dto.setPaymentDate(payment.getPaymentDate());
                                paymentRepo.save(modelMapper.map(dto, Payment.class));
                            } else {
                                throw new RuntimeException("Failed to update ticket status");
                            }

                        } else {
                            throw new NotFoundException("Ticket does not exist.");
                        }

                    } catch (HttpClientErrorException e) {
                        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                            throw new NotFoundException("Ticket does not exist.");
                        }
                    } catch (RestClientException e) {
                        throw new ServiceException("Unable to communicate with Ticket service");
                    }
                },
                () -> {
                    throw new NotFoundException("Payment Id Not Exist");
                });

    }

    @Override
    public void deletePayment(Long id) {
        paymentRepo.findById(id).ifPresentOrElse(
                ticket -> {
                    paymentRepo.deleteById(id);
                }
                ,
                () -> {
                    throw new NotFoundException("Payment Id Not Exist");
                }
        );
    }
}
