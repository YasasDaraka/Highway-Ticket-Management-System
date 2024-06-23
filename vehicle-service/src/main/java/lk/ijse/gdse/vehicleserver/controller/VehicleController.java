package lk.ijse.gdse.vehicleserver.controller;


import lk.ijse.gdse.vehicleserver.dto.VehicleDTO;
import lk.ijse.gdse.vehicleserver.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
@CrossOrigin
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    RestTemplate restTemplate;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/getAll")
    public List<VehicleDTO> getAllUsers() {
        return vehicleService.getAllVehicles();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/search/{id}")
    public VehicleDTO getUser(@PathVariable("id") String id) {
        return vehicleService.searchVehicle(id);
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody VehicleDTO dto){
        vehicleService.saveVehicle(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody VehicleDTO dto) {
        vehicleService.updateVehicle(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(params = "licensePlate")
    public ResponseEntity<Void> deleteCustomer(@RequestParam("licensePlate") String licensePlate) {
        vehicleService.deleteVehicle(licensePlate);
        return ResponseEntity.noContent().build();
    }
}
