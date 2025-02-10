package com.grish.RamroBazar.service;

import com.grish.RamroBazar.model.Product;
import com.grish.RamroBazar.model.ProductDTO;
import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.repository.ProductRepository;
import com.grish.RamroBazar.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository repo;

    public ResponseDTO getAllProducts() {
        List<Product> products = repo.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(ConvertUtil::convertProductDTO)
                .collect(Collectors.toList());

        Map<String, Object> details = new HashMap<>();
        details.put("totalProducts", productDTOs.size());
        details.put("products", productDTOs);

        return new ResponseDTO("M0000", "M0000", "Success", details);
    }

    public ResponseDTO addProduct(ProductDTO prod) {
        Product product = new Product();
        product.setName(prod.getName());
        product.setPrice(prod.getPrice());
        product.setDescription(prod.getDescription());
        product.setCategory(prod.getCategory());
        product.setQuantity(prod.getQuantity());
        product.setAvailable(prod.getAvailable());
        product.setReleaseDate(prod.getReleaseDate());
        product.setBrand(prod.getBrand());
        prod.setProductId(repo.save(product).getProductId());

        Map<String, Object> details = new HashMap<>();
        details.put("product", prod);

        return new ResponseDTO("M0000", "M0000", "Product added successfully", details);
    }

    public ResponseDTO getProductById(Integer productId) {
        Product product = repo.findById(productId).orElse(null);
        if (product != null) {
            Map<String, Object> details = new HashMap<>();
            details.put("product", ConvertUtil.convertProductDTO(product));

            return new ResponseDTO("M0000", "M0000", "Success", details);
        } else {
            return new ResponseDTO("M0001", "M0001", "Product not found", null);
        }
    }

    public ResponseDTO deleteProduct(Integer prodId) {
        Product product = repo.findById(prodId).orElse(null);
        if (product != null) {
            repo.delete(product);

            Map<String, Object> details = new HashMap<>();
            details.put("deletedProduct", ConvertUtil.convertProductDTO(product));

            return new ResponseDTO("M0000", "M0000", "Product deleted successfully", details);
        } else {
            return new ResponseDTO("M0001", "M0001", "Product not found", null);
        }
    }

    public ResponseDTO addProductByClient(ProductDTO prod, MultipartFile image) throws IOException {
        Product product = new Product();

        repo.save(product);

        Map<String, Object> details = new HashMap<>();
        details.put("product", ConvertUtil.convertProductDTO(product));

        return new ResponseDTO("M0000", "M0000", "Product added successfully", details);
    }
}
