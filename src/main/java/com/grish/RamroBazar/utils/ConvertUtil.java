    package com.grish.RamroBazar.utils;

    import com.grish.RamroBazar.model.Product;
    import com.grish.RamroBazar.model.ProductDTO;
    import lombok.experimental.UtilityClass;

    @UtilityClass
    public class ConvertUtil {

        public static ProductDTO convertProductDTO(Product product) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription());
            productDTO.setCategory(product.getCategory());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setAvailable(product.getAvailable());
            productDTO.setReleaseDate(product.getReleaseDate());
            productDTO.setBrand(product.getBrand());
            productDTO.setImageDate(product.getImageDate());
            productDTO.setImageType(product.getImageType());
            productDTO.setImageName(product.getImageName());
            return productDTO;
        }
    }
