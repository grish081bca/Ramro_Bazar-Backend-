package com.grish.RamroBazar.repository;

import com.grish.RamroBazar.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByUserName(String username);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByPhone(String phone);
}
