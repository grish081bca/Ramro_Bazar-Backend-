package com.grish.RamroBazar.service.impl;

import com.grish.RamroBazar.model.ResponseDTO;

public interface ICart {
    ResponseDTO getCartByUserId(Integer userId);
    ResponseDTO addToCart(Integer userId, Integer productId, Integer quantity);
    ResponseDTO updateCartItem(Integer cartItemId, Integer quantity);
    ResponseDTO removeFromCart(Integer cartItemId);
    ResponseDTO clearCart(Integer userId);
}
