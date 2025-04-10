package com.grish.RamroBazar.service;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.model.Users;
import com.grish.RamroBazar.model.UsersPrinciple;
import com.grish.RamroBazar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RBUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = repository.findByUserName(username);

        if (users.isEmpty()){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UsersPrinciple(users.get());
    }
}
