package lk.ijse.gdse.vehicleserver.repo;

import lk.ijse.gdse.vehicleserver.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepo extends JpaRepository<Vehicle, String> {
}
