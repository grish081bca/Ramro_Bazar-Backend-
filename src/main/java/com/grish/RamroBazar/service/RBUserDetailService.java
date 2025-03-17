package com.grish.RamroBazar.service;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.model.Users;
import com.grish.RamroBazar.model.UsersPrinciple;
import com.grish.RamroBazar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RBUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository repository;

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
        repository.save(user);

        Map<String,Object> details = new HashMap<>();
        details.put("users", user);

        return new ResponseDTO("M0000", "M0000", "User added successfully", details);
    }
}
