package lk.ijse.gdse.userserver.service;

import lk.ijse.gdse.userserver.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    void saveUser(UserDTO dto);
    UserDTO searchUser(String id);
    void updateUser(UserDTO dto);
    void deleteUser(String id);
}
