package lk.ijse.gdse.vehicleserver.service;



import lk.ijse.gdse.vehicleserver.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {
    List<VehicleDTO> getAllVehicles();
    void saveVehicle(VehicleDTO dto);
    VehicleDTO searchVehicle(String id);
    void updateVehicle(VehicleDTO dto);
    void deleteVehicle(String id);
}
