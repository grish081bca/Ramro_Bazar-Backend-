package com.grish.RamroBazar.service;

import com.grish.RamroBazar.model.*;
import com.grish.RamroBazar.repository.CartItemRepository;
import com.grish.RamroBazar.repository.CartRepository;
import com.grish.RamroBazar.repository.ProductRepository;
import com.grish.RamroBazar.repository.UserRepository;
import com.grish.RamroBazar.service.impl.ICart;
import com.grish.RamroBazar.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements ICart{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseDTO getCartByUserId(Integer userId) {
        Optional<Cart> cartOptional = cartRepository.findByUser_UserId(userId);

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            CartDTO cartDTO = ConvertUtil.convertToCartDTO(cart);

            Map<String, Object> details = new HashMap<>();
            details.put("cart", cartDTO);

            return new ResponseDTO("M0000", "SUCCESS", "Cart retrieved successfully", details, null);
        } else {
            return new ResponseDTO("M0001", "NOT_FOUND", "Cart not found for user", null, null);
        }
    }

    @Override
    public ResponseDTO addToCart(Integer userId, Integer productId, Integer quantity) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found")));
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<CartItem> existingItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.addCartItem(newItem);
        }

        cart.updateTotalPrice();
        cartRepository.save(cart);

        Map<String, Object> details = new HashMap<>();
        details.put("cart", ConvertUtil.convertToCartDTO(cart));

        return new ResponseDTO("M0000", "SUCCESS", "Product added to cart successfully", details, null);
    }

    @Override
    public ResponseDTO updateCartItem(Integer cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        Cart cart = cartItem.getCart();
        cart.updateTotalPrice();
        cartRepository.save(cart);

        Map<String, Object> details = new HashMap<>();
        details.put("cart", ConvertUtil.convertToCartDTO(cart));

        return new ResponseDTO("M0000", "SUCCESS", "Cart item updated successfully", details, null);
    }

    @Override
    public ResponseDTO removeFromCart(Integer cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = cartItem.getCart();
        cart.removeCartItem(cartItem);

        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);

        Map<String, Object> details = new HashMap<>();
        details.put("cart", ConvertUtil.convertToCartDTO(
                cart));

        return new ResponseDTO("M0000", "SUCCESS", "Item removed from cart successfully", details, null);
    }

    @Override
    public ResponseDTO clearCart(Integer userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getCartItems().clear();
        cart.setTotalPrice(java.math.BigDecimal.ZERO);
        cartRepository.save(cart);

        Map<String, Object> details = new HashMap<>();
        details.put("cart", ConvertUtil.convertToCartDTO(cart));

        return new ResponseDTO("M0000", "SUCCESS", "Cart cleared successfully", details, null);
    }
}
