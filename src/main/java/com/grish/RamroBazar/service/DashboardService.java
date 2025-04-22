package com.grish.RamroBazar.service;

import com.grish.RamroBazar.enums.RoleTypes;
import com.grish.RamroBazar.model.*;
import com.grish.RamroBazar.repository.CartItemRepository;
import com.grish.RamroBazar.repository.CartRepository;
import com.grish.RamroBazar.repository.ProductRepository;
import com.grish.RamroBazar.repository.UserRepository;
import com.grish.RamroBazar.service.impl.IDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService implements IDashboard {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // TODO: Add OrderRepository, once created
    // @Autowired
    // private OrderRepository orderRepository;

    @Override
    public ResponseDTO getAdminDashboardData() {
        try {
            Map<String, Object> dashboardData = new HashMap<>();

            // Count total users
            long totalUsers = userRepository.count();
            dashboardData.put("totalUsers", totalUsers);

            // Count users by role
            List<Users> allUsers = userRepository.findAll();
            Map<String, Long> usersByRole = allUsers.stream()
                    .collect(Collectors.groupingBy(user -> user.getRole().name(), Collectors.counting()));
            dashboardData.put("usersByRole", usersByRole);

            // Count total products
            long totalProducts = productRepository.count();
            dashboardData.put("totalProducts", totalProducts);

            // Recent users (last 5)
            List<Users> recentUsers = allUsers.stream()
                    .sorted(Comparator.comparing(Users::getUserId).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
            List<UserDTO> recentUsersDTO = recentUsers.stream()
                    .map(user -> {
                        UserDTO dto = new UserDTO();
                        dto.setUserId(user.getUserId());
                        dto.setUserName(user.getUserName());
                        dto.setUserEmail(user.getEmail());
                        dto.setUserPhone(user.getPhone());
                        dto.setRole(user.getRole().name());
                        return dto;
                    })
                    .collect(Collectors.toList());
            dashboardData.put("recentUsers", recentUsersDTO);

            // Recent products (last 5)
            List<Product> recentProducts = productRepository.findAll().stream()
                    .sorted(Comparator.comparing(Product::getProductId).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
            List<ProductDTO> recentProductsDTO = recentProducts.stream()
                    .map(product -> {
                        ProductDTO dto = new ProductDTO();
                        dto.setProductId(product.getProductId());
                        dto.setProductName(product.getName());
                        dto.setPrice(product.getPrice());
                        dto.setCategory(product.getCategory());
                        return dto;
                    })
                    .collect(Collectors.toList());
            dashboardData.put("recentProducts", recentProductsDTO);

            // TODO: Add order statistics once Order entity is created

            return new ResponseDTO("M0000", "M0000", "Dashboard data retrieved successfully", dashboardData, null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve dashboard data: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResponseDTO getUsersStatistics() {
        try {
            Map<String, Object> stats = new HashMap<>();

            List<Users> allUsers = userRepository.findAll();

            // Total users count
            stats.put("totalUsers", allUsers.size());

            // Users by role
            Map<String, Long> usersByRole = allUsers.stream()
                    .collect(Collectors.groupingBy(user -> user.getRole().name(), Collectors.counting()));
            stats.put("usersByRole", usersByRole);

            // New users in last 30 days (this is just a placeholder since we don't have createdAt field)
            // In a real application, filter users created in last 30 days
            stats.put("newUsersLast30Days", 0);

            // Active users (users with carts)
            long activeUsers = cartRepository.count();
            stats.put("activeUsers", activeUsers);

            return new ResponseDTO("M0000", "M0000", "User statistics retrieved successfully", stats, null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve user statistics: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResponseDTO getProductsStatistics() {
        try {
            Map<String, Object> stats = new HashMap<>();

            List<Product> allProducts = productRepository.findAll();

            // Total products count
            stats.put("totalProducts", allProducts.size());

            // Products by category
            Map<String, Long> productsByCategory = allProducts.stream()
                    .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
            stats.put("productsByCategory", productsByCategory);

            // Products by availability
            Map<String, Long> productsByAvailability = allProducts.stream()
                    .collect(Collectors.groupingBy(p -> p.getAvailable() ? "Available" : "Unavailable", Collectors.counting()));
            stats.put("productsByAvailability", productsByAvailability);

            // Products by price range
            Map<String, Long> productsByPriceRange = new HashMap<>();
            productsByPriceRange.put("0-50", allProducts.stream()
                    .filter(p -> p.getPrice().compareTo(new BigDecimal("50")) <= 0)
                    .count());
            productsByPriceRange.put("51-100", allProducts.stream()
                    .filter(p -> p.getPrice().compareTo(new BigDecimal("50")) > 0 && p.getPrice().compareTo(new BigDecimal("100")) <= 0)
                    .count());
            productsByPriceRange.put("101-500", allProducts.stream()
                    .filter(p -> p.getPrice().compareTo(new BigDecimal("100")) > 0 && p.getPrice().compareTo(new BigDecimal("500")) <= 0)
                    .count());
            productsByPriceRange.put("501+", allProducts.stream()
                    .filter(p -> p.getPrice().compareTo(new BigDecimal("500")) > 0)
                    .count());
            stats.put("productsByPriceRange", productsByPriceRange);

            return new ResponseDTO("M0000", "M0000", "Product statistics retrieved successfully", stats, null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve product statistics: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResponseDTO getSalesStatistics() {
        // This would require an Order entity
        // For now, we'll return placeholder data
        try {
            Map<String, Object> stats = new HashMap<>();

            stats.put("totalSales", 0);
            stats.put("totalRevenue", 0);
            stats.put("averageOrderValue", 0);

            // TODO: Implement real sales statistics once Order entity is created

            return new ResponseDTO("M0000", "M0000", "Sales statistics retrieved successfully", stats, null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve sales statistics: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResponseDTO getUserDashboardData(Integer userId) {
//        String username = auth.getName(); // From token
//        Users user = userRepository.findByUserName(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        if (!user.getUserId().equals(userId)) {
//            throw new AccessDeniedException("You are not authorized to view this dashboard.");
//        }
        try {
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Map<String, Object> dashboardData = new HashMap<>();

            // User profile info
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUserName(user.getUserName());
            userDTO.setUserEmail(user.getEmail());
            userDTO.setUserPhone(user.getPhone());
            userDTO.setRole(user.getRole().name());
            dashboardData.put("userProfile", userDTO);

            // User's cart
            Optional<Cart> cart = cartRepository.findByUser_UserId(userId);
            if (cart.isPresent()) {
                CartDTO cartDTO = new CartDTO();
                cartDTO.setCartId(cart.get().getCartId());
                cartDTO.setUserId(userId);
                cartDTO.setTotalPrice(cart.get().getTotalPrice());

                List<CartItemDTO> cartItemDTOs = cart.get().getCartItems().stream()
                        .map(item -> {
                            CartItemDTO itemDTO = new CartItemDTO();
                            itemDTO.setCartItemId(item.getCartItemId());
                            itemDTO.setProductId(item.getProduct().getProductId());
                            itemDTO.setProductName(item.getProduct().getName());
                            itemDTO.setPrice(item.getProduct().getPrice());
                            itemDTO.setQuantity(item.getQuantity());
                            return itemDTO;
                        })
                        .collect(Collectors.toList());
                dashboardData.put("cartItems", cartItemDTOs);

                dashboardData.put("cart", cartDTO);
            } else {
                dashboardData.put("cart", null);
            }

            // Recommended products (for demo, just get 5 random products)
            List<Product> recommendedProducts = productRepository.findAll().stream()
                    .limit(5)
                    .collect(Collectors.toList());
            List<ProductDTO> recommendedProductDTOs = recommendedProducts.stream()
                    .map(product -> {
                        ProductDTO dto = new ProductDTO();
                        dto.setProductId(product.getProductId());
                        dto.setProductName(product.getName());
                        dto.setPrice(product.getPrice());
                        dto.setImageUrl(product.getImageUrl());
                        dto.setCategory(product.getCategory());
                        return dto;
                    })
                    .collect(Collectors.toList());
            dashboardData.put("recommendedProducts", recommendedProductDTOs);

            // TODO: Add purchase history once Order entity is created
            dashboardData.put("recentOrders", Collections.emptyList());

            return new ResponseDTO("M0000", "M0000", "User dashboard data retrieved successfully", dashboardData, null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve user dashboard data: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResponseDTO getUserPurchaseHistory(Integer userId) {
        // This would require an Order entity
        // For now, we'll return placeholder data
        try {
            return new ResponseDTO("M0000", "M0000", "No purchase history found", Collections.emptyMap(), null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve purchase history: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResponseDTO getUserRecommendedProducts(Integer userId) {
        try {
            // In a real application, this would use recommendation algorithms
            // For demo, just return random products
            List<Product> recommendedProducts = productRepository.findAll().stream()
                    .limit(10)
                    .collect(Collectors.toList());

            List<ProductDTO> recommendedProductDTOs = recommendedProducts.stream()
                    .map(product -> {
                        ProductDTO dto = new ProductDTO();
                        dto.setProductId(product.getProductId());
                        dto.setProductName(product.getName());
                        dto.setPrice(product.getPrice());
                        dto.setImageUrl(product.getImageUrl());
                        dto.setCategory(product.getCategory());
                        dto.setBrand(product.getBrand());
                        dto.setDescription(product.getDescription());
                        return dto;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> data = new HashMap<>();
            data.put("recommendedProducts", recommendedProductDTOs);

            return new ResponseDTO("M0000", "M0000", "Recommendations retrieved successfully", data, null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve recommendations: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResponseDTO getSellerDashboardData(Integer userId) {
        try {
            Users seller = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Seller not found"));

            if (seller.getRole() != RoleTypes.SELLER) {
                return new ResponseDTO("Error", "E0000", "User is not a seller", null, null);
            }

            Map<String, Object> dashboardData = new HashMap<>();

            // Seller profile info
            UserDTO sellerDTO = new UserDTO();
            sellerDTO.setUserId(seller.getUserId());
            sellerDTO.setUserName(seller.getUserName());
            sellerDTO.setUserEmail(seller.getEmail());
            sellerDTO.setUserPhone(seller.getPhone());
            dashboardData.put("sellerProfile", sellerDTO);

            // For a real application, we need to track which products belong to which seller
            // For demo purposes, we'll just pretend all products belong to this seller
            List<Product> sellerProducts = productRepository.findAll();

            // Product statistics
            dashboardData.put("totalProducts", sellerProducts.size());

            // Products by category
            Map<String, Long> productsByCategory = sellerProducts.stream()
                    .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
            dashboardData.put("productsByCategory", productsByCategory);

            // TODO: Add sales statistics once Order entity is created
            dashboardData.put("totalSales", 0);
            dashboardData.put("totalRevenue", 0);

            // Recent products (last 5)
            List<ProductDTO> recentProductDTOs = sellerProducts.stream()
                    .sorted(Comparator.comparing(Product::getProductId).reversed())
                    .limit(5)
                    .map(product -> {
                        ProductDTO dto = new ProductDTO();
                        dto.setProductId(product.getProductId());
                        dto.setProductName(product.getName());
                        dto.setPrice(product.getPrice());
                        dto.setCategory(product.getCategory());
                        dto.setQuantity(product.getQuantity());
                        dto.setAvailable(product.getAvailable());
                        return dto;
                    })
                    .collect(Collectors.toList());
            dashboardData.put("recentProducts", recentProductDTOs);

            return new ResponseDTO("M0000", "M0000", "Seller dashboard data retrieved successfully", dashboardData, null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve seller dashboard data: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResponseDTO getSellerSalesData(Integer userId, String period) {
        // This would require an Order entity
        // For now, we'll return placeholder data
        try {
            Map<String, Object> salesData = new HashMap<>();

            // Create mock sales data for chart
            List<Map<String, Object>> chartData = new ArrayList<>();

            LocalDate now = LocalDate.now();
            int days = 30; // default to last 30 days

            if (period != null) {
                switch (period.toLowerCase()) {
                    case "week":
                        days = 7;
                        break;
                    case "month":
                        days = 30;
                        break;
                    case "year":
                        days = 365;
                        break;
                }
            }

            for (int i = days - 1; i >= 0; i--) {
                LocalDate date = now.minus(i, ChronoUnit.DAYS);
                Map<String, Object> point = new HashMap<>();
                point.put("date", date.toString());
                point.put("sales", Math.random() * 1000); // Random sales value
                chartData.add(point);
            }

            salesData.put("salesChart", chartData);
            salesData.put("totalSales", 0);
            salesData.put("period", period != null ? period : "month");

            return new ResponseDTO("M0000", "M0000", "Sales data retrieved successfully", salesData, null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve sales data: " + e.getMessage(), null, null);
        }
    }

    @Override
    public ResponseDTO getSellerProductPerformance(Integer userId) {
        try {
            Users seller = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Seller not found"));

        if (seller.getRole() != RoleTypes.SELLER) {
                return new ResponseDTO("Error", "E0000", "User is not a seller", null, null);
            }

            // For a real application, we need to track which products belong to which seller
            List<Product> sellerProducts = productRepository.findAll();

            List<Map<String, Object>> productPerformance = sellerProducts.stream()
                    .map(product -> {
                        Map<String, Object> performance = new HashMap<>();
                        performance.put("productId", product.getProductId());
                        performance.put("productName", product.getName());
                        performance.put("price", product.getPrice());
                        performance.put("quantity", product.getQuantity());
                        performance.put("views", Math.round(Math.random() * 1000)); // Mock data
                        performance.put("sales", Math.round(Math.random() * 100));  // Mock data
                        performance.put("revenue", product.getPrice().multiply(new BigDecimal(Math.round(Math.random() * 100)))); // Mock revenue
                        return performance;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> data = new HashMap<>();
            data.put("productPerformance", productPerformance);

            return new ResponseDTO("M0000", "M0000", "Product performance data retrieved successfully", data, null);
        } catch (Exception e) {
            return new ResponseDTO("Error", "E0000", "Failed to retrieve product performance data: " + e.getMessage(), null, null);
        }
    }
}
