package com.grish.RamroBazar.controller;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.model.UserDTO;
import com.grish.RamroBazar.model.Users;
import com.grish.RamroBazar.service.RBUserDetailService;
import com.grish.RamroBazar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody UserDTO userDTO) {
        return userService.verifyUser(userDTO);
    }

    @GetMapping("/users")
    public  ResponseEntity<ResponseDTO> getAllUsers() {
        try {
            ResponseDTO responseDTO = userService.getUsers();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO errorDTO = new ResponseDTO("Error","E0000","Error while getting users", null,null);
            return new ResponseEntity<>(errorDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-user")
    public ResponseEntity<ResponseDTO> addUser(@RequestBody UserDTO userDTO) {
        try {
            ResponseDTO responseDTO = userService.addUser(userDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Error","E0000","Error adding user",null,null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.editUser(userDTO);
    }
}
