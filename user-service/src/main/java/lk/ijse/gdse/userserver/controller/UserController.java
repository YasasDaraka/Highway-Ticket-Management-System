package lk.ijse.gdse.userserver.controller;

import lk.ijse.gdse.userserver.dto.UserDTO;
import lk.ijse.gdse.userserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RestTemplate restTemplate;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/getAll")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/search/{id}")
    public UserDTO getUser(@PathVariable("id") String id) {
        return userService.searchUser(id);
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody UserDTO dto){
        userService.saveUser(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody UserDTO dto) {
        userService.updateUser(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(params = "userNic")
    public ResponseEntity<Void> deleteCustomer(@RequestParam("userNic") String userNic) {
        userService.deleteUser(userNic);
        return ResponseEntity.noContent().build();
    }
}
