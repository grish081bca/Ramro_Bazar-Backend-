package com.grish.RamroBazar.controller;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.model.Users;
import com.grish.RamroBazar.service.RBUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    @Autowired
    RBUserDetailService userService;


    @GetMapping("/users")
    public ResponseDTO getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/add-user")
    public ResponseDTO addUser(@RequestBody Users user) {
        return userService.addUser(user);
    }
}
