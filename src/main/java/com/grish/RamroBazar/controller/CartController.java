package com.grish.RamroBazar.controller;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.service.impl.ICart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICart cartService;

    @GetMapping("/{userId}")
    public ResponseDTO getCart(@PathVariable Integer userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/add")
    public ResponseDTO addToCart(
            @RequestParam("userId") Integer userId,
            @RequestParam("productId") Integer productId,
            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity
    ) {
        return cartService.addToCart(userId, productId, quantity);
    }

    @PostMapping("/update")
    public ResponseDTO updateCartItem(
            @RequestParam("cartItemId") Integer cartItemId,
            @RequestParam("quantity") Integer quantity
    ) {
        return cartService.updateCartItem(cartItemId, quantity);
    }

    @PostMapping("/remove/{cartItemId}")
    public ResponseDTO removeFromCart(@PathVariable Integer cartItemId) {
        return cartService.removeFromCart(cartItemId);
    }

    @PostMapping("/clear/{userId}")
    public ResponseDTO clearCart(@PathVariable Integer userId) {
        return cartService.clearCart(userId);
    }
}
