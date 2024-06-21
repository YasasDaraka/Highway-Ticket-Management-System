package lk.ijse.gdse.userserver.controller;

import lk.ijse.gdse.userserver.dto.UserDTO;
import lk.ijse.gdse.userserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    public boolean saveUser(@RequestBody UserDTO dto){
        userService.saveUser(dto);
        return true;
    }

    @GetMapping
    public String getAllUser(){
        return "awaaa";
    }

}
