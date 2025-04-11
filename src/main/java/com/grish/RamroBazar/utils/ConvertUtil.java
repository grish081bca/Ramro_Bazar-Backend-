    package com.grish.RamroBazar.utils;

    import com.grish.RamroBazar.model.Product;
    import com.grish.RamroBazar.model.ProductDTO;
    import com.grish.RamroBazar.model.UserDTO;
    import com.grish.RamroBazar.model.Users;
    import lombok.experimental.UtilityClass;

    import java.util.List;
    import java.util.stream.Collectors;

    @UtilityClass
    public class ConvertUtil {

        public static ProductDTO convertProductDTO(Product product) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setProductName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription());
            productDTO.setCategory(product.getCategory());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setAvailable(product.getAvailable());
            productDTO.setReleaseDate(product.getReleaseDate());
            productDTO.setBrand(product.getBrand());
            productDTO.setImageUrl(product.getImageUrl());
            return productDTO;
        }

        public static List<ProductDTO> convertProductListDTO(List<Product> products) {
            return products.stream().map(ConvertUtil::convertProductDTO).collect(Collectors.toList());
        }

        public static UserDTO convertUserDTO(Users users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(users.getUserId());
            userDTO.setUserName(users.getUserName());
            userDTO.setPassword(users.getPassword());
            userDTO.setRole(users.getRole());
            userDTO.setUserEmail(users.getEmail());
            userDTO.setUserPhone(users.getPhone());
            return userDTO;
        }

        public static List<UserDTO> convertUserListDTO(List<Users> users) {
            return users.stream().map(ConvertUtil::convertUserDTO).collect(Collectors.toList());
        }
    }
