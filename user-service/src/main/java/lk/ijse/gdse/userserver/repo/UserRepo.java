package lk.ijse.gdse.userserver.repo;

import lk.ijse.gdse.userserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
