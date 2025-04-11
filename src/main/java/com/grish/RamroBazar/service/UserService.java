package com.grish.RamroBazar.service;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.model.UserDTO;
import com.grish.RamroBazar.model.Users;
import com.grish.RamroBazar.repository.UserRepository;
import com.grish.RamroBazar.utils.ConvertUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private UserRepository userRepository;

    public ResponseDTO getUsers() {
        try {
            List<Users> users = repository.findAll();
            List<UserDTO> userDTO = ConvertUtil.convertUserListDTO(users);

            Map<String, Object> usersList = new HashMap<>();
            usersList.put("totalUsers", userDTO.size());
            usersList.put("users", userDTO);

            return new ResponseDTO("M0000", "M0000", "Success", usersList, null);
        }catch (Exception e) {
            return new ResponseDTO("Error","E0000","No list found", null,null);
        }
    }

    public ResponseDTO addUser(UserDTO userDTO) {
        Optional<Users> existUserByName = repository.findByUserName(userDTO.getUserName());
        Optional<Users> existUserByEmail = repository.findByEmail(userDTO.getUserEmail());
        Optional<Users> existUserByPhone = repository.findByPhone(userDTO.getUserPhone());
        try{
            if (existUserByName.isPresent()) {
                return new ResponseDTO("Error","E0000","User already exists", null,null);
            } else if (existUserByEmail.isPresent()) {
                return new ResponseDTO("Error","E0000","User already exists", null,null);
            }else if (existUserByPhone.isPresent()) {
                return new ResponseDTO("Error","E0000","User already exists", null,null);
            }else {
                Users users = new Users();
                users.setUserName(userDTO.getUserName());
                users.setPassword(encoder.encode(userDTO.getPassword()));
                users.setRole(userDTO.getRole());
                users.setEmail(userDTO.getUserEmail());
                users.setPhone(userDTO.getUserPhone());
                repository.save(users);

                Map<String, Object> details = new HashMap<>();
                details.put("users", ConvertUtil.convertUserDTO(users));

                return new ResponseDTO("M0000", "M0000", "User added successfully", details, null);
            }
        } catch (Exception e) {
            return new ResponseDTO("Error","E0000","Failed To Add User", null,null);
        }
    }

    public ResponseDTO getUserById(Integer userId){
        Users user = repository.findById(userId).orElse(null);
        if (user != null) {
            Map<String, Object> details = new HashMap<>();
            details.put("users", ConvertUtil.convertUserDTO(user));
            return new ResponseDTO("M0000", "M0000", "User found", details, null);
        } else {
            Map<String,Object> detail = new HashMap<>();
            detail.put("error", "User not found");
            return new ResponseDTO("M0000", "M0000", "User not found", detail,null);
        }
    }

    public ResponseDTO deleteUser(Integer userId){
        Users users = repository.findById(userId).orElse(null);
        if (users != null){
            repository.delete(users);
            Map<String,Object> details = new HashMap<>();
            details.put("message", "User deleted successfully");
            details.put("user", ConvertUtil.convertUserDTO(users));
            return new ResponseDTO("M0000", "M0000", "User deleted successfully", details, null);
        } else {
            Map<String, Object> details = new HashMap<>();
            details.put("error", "User not found");
            return new ResponseDTO("M0000", "M0000", "User not found", details, null);
        }
    }

    public ResponseDTO editUser(UserDTO userDTO){
        Users users = repository.findById(userDTO.getUserId()).orElse(null);
        if (users != null){
            users.setUserName(userDTO.getUserName());
            users.setRole(userDTO.getRole());
            users.setEmail(userDTO.getUserEmail());
            users.setPhone(userDTO.getUserPhone());
            repository.save(users);
            Map<String,Object> details = new HashMap<>();
            details.put("message", "User updated successfully");
            details.put("user", ConvertUtil.convertUserDTO(users));
            return new ResponseDTO("M0000", "M0000", "User updated successfully", details, null);
        } else {
            Map<String, Object> details = new HashMap<>();
            details.put("error", "User not found");
            return new ResponseDTO("M0000", "M0000", "User not found", details, null);
        }
    }

    public ResponseDTO verifyUser(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getPassword()));

        if (authentication.isAuthenticated()){
           var getToken = jwtService.generateToken(userDTO.getUserName());
            Map<String,Object> detail = new HashMap<>();
            detail.put("token", getToken);
            return  new ResponseDTO("M0000", "M0000", "Login SuccessFully", detail, null) ;
        }else {
            Map<String,Object> message = new HashMap<>();
            message.put("error", "Login Failed");
            return new ResponseDTO("M0000","M0000", "Login Failed",message,null);
        }
    }
}
