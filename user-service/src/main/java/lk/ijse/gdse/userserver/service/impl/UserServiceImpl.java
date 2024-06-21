package lk.ijse.gdse.userserver.service.impl;

import lk.ijse.gdse.userserver.dto.UserDTO;
import lk.ijse.gdse.userserver.entity.User;
import lk.ijse.gdse.userserver.repo.UserRepo;
import lk.ijse.gdse.userserver.service.UserService;
import lk.ijse.gdse.userserver.service.exception.DuplicateRecordException;
import lk.ijse.gdse.userserver.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return modelMapper.map(userRepo.findAll(), new TypeToken<ArrayList<UserDTO>>() {}.getType());
    }

    @Override
    public void saveUser(UserDTO dto) {
        userRepo.findById(dto.getNic()).ifPresentOrElse(
                user -> {
                    throw new DuplicateRecordException("User Already Exist");
                },
                () -> {
                    userRepo.save(modelMapper.map(dto, User.class));
                });
    }

    @Override
    public UserDTO searchUser(String id) {
        return (UserDTO) userRepo.findById(id)
                .map(user -> modelMapper.map(userRepo.findById(id), UserDTO.class))
                .orElseThrow(() -> new NotFoundException("User Not Exist"));
    }

    @Override
    public void updateUser(UserDTO dto) {
        userRepo.findById(dto.getNic()).ifPresentOrElse(
                user -> {
                    userRepo.save(modelMapper.map(dto, User.class));
                },
                () -> {
                    throw new NotFoundException("User Not Exist");
                });

    }

    @Override
    public void deleteUser(String id) {
        userRepo.findById(id).ifPresentOrElse(
                user -> {
                    userRepo.deleteById(id);
                }
                ,
                () -> {
                    throw new NotFoundException("User Not Exist");
                }
        );
    }
}
