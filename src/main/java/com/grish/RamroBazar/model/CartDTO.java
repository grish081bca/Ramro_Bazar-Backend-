package com.grish.RamroBazar.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDTO {
    private Integer cartId;
    private Integer userId;
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;
}
