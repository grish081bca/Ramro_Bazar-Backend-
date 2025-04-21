package com.grish.RamroBazar.repository;

import com.grish.RamroBazar.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser_UserId(Integer userUserId);
}
