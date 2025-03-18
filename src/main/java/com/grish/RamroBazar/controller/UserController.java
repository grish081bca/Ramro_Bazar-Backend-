package com.grish.RamroBazar.controller;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.model.Users;
import com.grish.RamroBazar.service.RBUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @GetMapping("/user")
    public  ResponseDTO getUserByID(@RequestParam Integer id){
        return userService.getUserById(id);
    }

    @PostMapping("/delete/user")
    public ResponseDTO deleteUser(@RequestParam Integer id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/update/user")
    public ResponseDTO updateUser(@RequestBody Users user) {
        return userService.editUser(user);
    }
}
