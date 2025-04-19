package com.grish.RamroBazar.controller;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.model.UserDTO;
import com.grish.RamroBazar.model.Users;
import com.grish.RamroBazar.service.RBUserDetailService;
import com.grish.RamroBazar.service.impl.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    IUser iUser;

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody UserDTO userDTO) {
        try {
            return iUser.verifyUser(userDTO);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseDTO("error","M0000","something went wrong", null,null);
        }
    }

    @GetMapping("/list")
    public  ResponseEntity<ResponseDTO> getAllUsers() {
        try {
            ResponseDTO responseDTO = iUser.getUsers();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO errorDTO = new ResponseDTO("Error","E0000","Error while getting users", null,null);
            return new ResponseEntity<>(errorDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addUser(@RequestBody UserDTO userDTO) {
        try {
            ResponseDTO responseDTO = iUser.addUser(userDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO("Error","E0000","Error adding user",null,null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public  ResponseDTO getUserByID(@PathVariable Integer id){
        return iUser.getUserById(id);
    }

    @PostMapping("/delete/{id}")
    public ResponseDTO deleteUser(@PathVariable Integer id) {
        return iUser.deleteUser(id);
    }

    @PostMapping("/update")
    public ResponseDTO updateUser(@RequestBody UserDTO userDTO) {
        return iUser.editUser(userDTO);
    }
}
