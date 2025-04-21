package com.grish.RamroBazar.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Integer cartItemId;
    private Integer productId;
    private String productName;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;
}
