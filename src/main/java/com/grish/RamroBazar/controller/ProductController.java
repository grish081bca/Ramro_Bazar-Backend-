package com.grish.RamroBazar.controller;

import com.grish.RamroBazar.model.Product;
import com.grish.RamroBazar.model.ProductDTO;
import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.service.ProductService;
import com.grish.RamroBazar.service.impl.IProduct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {
//    @Autowired
//    ProductService productService;

    @Autowired
    IProduct product;

    @GetMapping("/")
    public String greet(HttpServletRequest request) {
        return "Hello World" + request.getSession().getId();
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/list")
    public ResponseDTO getAllProducts() {
        return product.getAllProducts();
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("brand") String brand,
            @RequestParam("category") String category,
            @RequestParam("releaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDate,
            @RequestParam("available") Boolean available,
            @RequestParam("quantity") Integer quantity,
            @RequestPart("imageFile") MultipartFile imageFile
    ) throws IOException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(productName);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        productDTO.setBrand(brand);
        productDTO.setCategory(category);
        productDTO.setReleaseDate(releaseDate);
        productDTO.setAvailable(available);
        productDTO.setQuantity(quantity);

        return product.addProduct(productDTO, imageFile);
    }

    @GetMapping("/{id}")
    public ResponseDTO getProductById(@PathVariable Integer id) {
        return product.getProductById(id);
    }

    @PostMapping("/delete/{id}")
    public ResponseDTO deleteProduct(@PathVariable Integer id) {
        return product.deleteProduct(id);
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> updateProduct(
            @RequestParam("productId") Integer productId,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("brand") String brand,
            @RequestParam("category") String category,
            @RequestParam("releaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDate,
            @RequestParam("available") Boolean available,
            @RequestParam("quantity") Integer quantity,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productId);
        productDTO.setProductName(productName);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        productDTO.setBrand(brand);
        productDTO.setCategory(category);
        productDTO.setReleaseDate(releaseDate);
        productDTO.setAvailable(available);
        productDTO.setQuantity(quantity);

        ResponseDTO response = product.updateProduct(productDTO, imageFile);
        return ResponseEntity.ok(response);
    }

}
