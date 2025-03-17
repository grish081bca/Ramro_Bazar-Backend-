package com.grish.RamroBazar.controller;

import com.grish.RamroBazar.model.Product;
import com.grish.RamroBazar.model.ProductDTO;
import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping("/")
    public String greet(HttpServletRequest request) {
        return "Hello World" + request.getSession().getId();
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/products")
    public ResponseDTO getAllProducts() {
        return service.getAllProducts();
    }

    @PostMapping(value = "/add-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO addProduct(
            @RequestPart("productDTO") ProductDTO productDTO,  // Match form field name
            @RequestPart("imageFile") MultipartFile imageFile
    ) throws IOException {
        return service.addProduct(productDTO,imageFile);
    }

    @GetMapping("/products/{id}")
    public ResponseDTO getProductById(@PathVariable Integer id) {
        return service.getProductById(id);
    }

    @PostMapping("/delete/products/{id}")
    public ResponseDTO deleteProduct(@PathVariable Integer id) {
        return service.deleteProduct(id);
    }

//    @PostMapping("/update/product")
//    public ResponseEntity<ResponseDTO> editProduct(@RequestBody ProductDTO productDto) {
//        ResponseDTO response = service.updateProduct(productDto);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping(value = "/update/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> updateProduct(
            @RequestPart("productDTO") ProductDTO productDTO,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {

        ResponseDTO response = service.updateProduct(productDTO,imageFile);

        return ResponseEntity.ok(response);
    }

    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> getProductImageById(@PathVariable Integer id) {
        ResponseDTO response = service.getProductById(id);

        // Check if the response indicates success
        if (!"M0000".equals(response.getCode())) {
            return ResponseEntity.notFound().build();
        }

        // Extract ProductDTO from the response details
        Map<String, Object> details = (Map<String, Object>) response.getDetails();
        ProductDTO productDTO = (ProductDTO) details.get("products");

        // Extract image data from ProductDTO
        byte[] imageData = productDTO.getImageDate();
        String imageType = productDTO.getImageType();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(imageType))
                .body(imageData);
    }
}
