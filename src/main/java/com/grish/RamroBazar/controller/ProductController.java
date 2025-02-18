package com.grish.RamroBazar.controller;

import com.grish.RamroBazar.model.ProductDTO;
import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping("/")
    public String greet(){
        return "Hello World";
    }

    @GetMapping("products")
    public ResponseDTO getAllProducts() {
        return service.getAllProducts();
    }

    @PostMapping("add-product")
    public ResponseDTO addProduct(@RequestBody ProductDTO productDTO) {
        return service.addProduct(productDTO);
    }

    @GetMapping("/products/{id}")
    public ResponseDTO getProductById(@PathVariable Integer id) {
        return service.getProductById(id);
    }

    @PostMapping("/delete/products/{id}")
    public ResponseDTO deleteProduct(@PathVariable Integer id) {
        return service.deleteProduct(id);
    }

    @PostMapping("/update/product")
    public ResponseEntity<ResponseDTO> editProduct(@RequestBody ProductDTO productDto){
        ResponseDTO response = service.updateProduct(productDto);
        return ResponseEntity.ok(response);
    }
}
