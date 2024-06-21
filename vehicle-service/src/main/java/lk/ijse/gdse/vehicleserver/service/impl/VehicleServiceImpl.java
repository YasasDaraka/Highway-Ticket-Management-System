package lk.ijse.gdse.vehicleserver.service.impl;

import lk.ijse.gdse.vehicleserver.dto.UserDTO;
import lk.ijse.gdse.vehicleserver.dto.VehicleDTO;
import lk.ijse.gdse.vehicleserver.entity.Vehicle;
import lk.ijse.gdse.vehicleserver.repo.VehicleRepo;
import lk.ijse.gdse.vehicleserver.service.VehicleService;
import lk.ijse.gdse.vehicleserver.service.exception.DuplicateRecordException;
import lk.ijse.gdse.vehicleserver.service.exception.NotFoundException;
import lk.ijse.gdse.vehicleserver.service.exception.ServiceException;
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
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepo vehicleRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    private static final String user_service_url = "http://user-service/user/";

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return modelMapper.map(vehicleRepo.findAll(), new TypeToken<ArrayList<VehicleDTO>>() {}.getType());
    }

    @Override
    public void saveVehicle(VehicleDTO dto) {
        vehicleRepo.findById(dto.getLicensePlate()).ifPresentOrElse(
                vehicle -> {
                    throw new DuplicateRecordException("Vehicle Already Exist");
                },
                () -> {
                    try {
                        ResponseEntity<UserDTO> response = restTemplate.getForEntity(user_service_url + "search/" + dto.getUser(), UserDTO.class);
                        if (response.getStatusCode() == HttpStatus.OK) {
                            vehicleRepo.save(modelMapper.map(dto, Vehicle.class));
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
    public VehicleDTO searchVehicle(String id) {
        return (VehicleDTO) vehicleRepo.findById(id)
                .map(vehicle -> modelMapper.map(vehicleRepo.findById(id), VehicleDTO.class))
                .orElseThrow(() -> new NotFoundException("Vehicle Not Exist"));
    }

    @Override
    public void updateVehicle(VehicleDTO dto) {
        vehicleRepo.findById(dto.getLicensePlate()).ifPresentOrElse(
                vehicle -> {
                    try {
                        ResponseEntity<UserDTO> response = restTemplate.getForEntity(user_service_url + "search/" + dto.getUser(), UserDTO.class);
                        if (response.getStatusCode() == HttpStatus.OK) {
                            vehicleRepo.save(modelMapper.map(dto, Vehicle.class));
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
                    throw new NotFoundException("Vehicle Not Exist");
                });

    }

    @Override
    public void deleteVehicle(String id) {
        vehicleRepo.findById(id).ifPresentOrElse(
                vehicle -> {
                    vehicleRepo.deleteById(id);
                }
                ,
                () -> {
                    throw new NotFoundException("Vehicle Not Exist");
                }
        );
    }
}
