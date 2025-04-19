package com.grish.RamroBazar.service.impl;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.model.UserDTO;

public interface IUser {
    ResponseDTO getUsers();
    ResponseDTO addUser(UserDTO userDTO);
    ResponseDTO editUser(UserDTO userDTO);
    ResponseDTO deleteUser(Integer userId);
    ResponseDTO getUserById(Integer userId);
    ResponseDTO verifyUser(UserDTO userDTO);
}
