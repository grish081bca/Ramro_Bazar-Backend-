package com.grish.RamroBazar.service.impl;

import com.grish.RamroBazar.model.Product;
import com.grish.RamroBazar.model.ProductDTO;
import com.grish.RamroBazar.model.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProduct {
    ResponseDTO getAllProducts();
    ResponseDTO addProduct(ProductDTO productDTO, MultipartFile imageFile) throws IOException;
    ResponseDTO getProductById(Integer productId);
    ResponseDTO updateProduct(ProductDTO prod, MultipartFile imageFile) throws IOException;
    ResponseDTO deleteProduct(Integer prodId);
}

