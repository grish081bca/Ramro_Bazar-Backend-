package com.grish.RamroBazar.repository;

import com.grish.RamroBazar.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(Integer cartId, Integer productId);
}
