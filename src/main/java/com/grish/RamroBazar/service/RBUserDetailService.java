package com.grish.RamroBazar.service;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.model.Users;
import com.grish.RamroBazar.model.UsersPrinciple;
import com.grish.RamroBazar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RBUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = repository.findByUserName(username);

        if (users == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UsersPrinciple(users);
    }

    public ResponseDTO getUsers() {
        List<Users> users = repository.findAll();

        Map<String,Object> usersList = new HashMap<>();
        usersList.put("totalUsers", users.size());
        usersList.put("users", users);

        return new ResponseDTO("M0000", "M0000", "Success", usersList);
    }

    public ResponseDTO addUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        Map<String,Object> details = new HashMap<>();
        details.put("users", user);

        return new ResponseDTO("M0000", "M0000", "User added successfully", details);
    }

    public ResponseDTO getUserById(Integer userId){
        Users user = repository.findById(userId).orElse(null);
        if (user != null) {
            Map<String, Object> details = new HashMap<>();
            details.put("users", user);
            return new ResponseDTO("M0000", "M0000", "User found", details);
        } else {
            Map<String,Object> detail = new HashMap<>();
            detail.put("error", "User not found");
            return new ResponseDTO("M0000", "M0000", "User not found", detail);
        }
    }

    public ResponseDTO deleteUser(Integer userId){
        Users users = repository.findById(userId).orElse(null);
         if (users != null){
              repository.delete(users);
                Map<String,Object> details = new HashMap<>();
                details.put("message", "User deleted successfully");
                details.put("user", users);
                return new ResponseDTO("M0000", "M0000", "User deleted successfully", details);
            } else {
             Map<String, Object> details = new HashMap<>();
             details.put("error", "User not found");
             return new ResponseDTO("M0000", "M0000", "User not found", details);
         }
    }

    public ResponseDTO editUser(Users user){
        Users users = repository.findById(user.getUserId()).orElse(null);
        if (users != null){
            users.setUserName(user.getUserName());
            users.setPassword(user.getPassword());
            users.setRole(user.getRole());
            repository.save(users);
            Map<String,Object> details = new HashMap<>();
            details.put("message", "User updated successfully");
            details.put("user", users);
            return new ResponseDTO("M0000", "M0000", "User updated successfully", details);
        } else {
            Map<String, Object> details = new HashMap<>();
            details.put("error", "User not found");
            return new ResponseDTO("M0000", "M0000", "User not found", details);
        }
    }
}
