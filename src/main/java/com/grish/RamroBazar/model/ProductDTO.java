package com.grish.RamroBazar.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductDTO {

    private Integer productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private String brand;
    private String category;
    private LocalDate releaseDate;
    private Boolean available;
    private Integer quantity;
    private String imageType;
    private String imageName;
    private byte[] imageDate;
}
