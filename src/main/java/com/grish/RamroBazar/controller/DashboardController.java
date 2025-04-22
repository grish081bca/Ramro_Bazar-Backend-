package com.grish.RamroBazar.controller;

import com.grish.RamroBazar.model.ResponseDTO;
import com.grish.RamroBazar.service.impl.IDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private IDashboard dashboardService;

    // Admin dashboard data
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseDTO getAdminDashboard() {
        return dashboardService.getAdminDashboardData();
    }

    // Admin-specific user stats
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/users-stats")
    public ResponseDTO getUsersStats() {
        return dashboardService.getUsersStatistics();
    }

    // Admin-specific product stats
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/products-stats")
    public ResponseDTO getProductsStats() {
        return dashboardService.getProductsStatistics();
    }

    // Admin-specific sales stats
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/sales-stats")
    public ResponseDTO getSalesStats() {
        return dashboardService.getSalesStatistics();
    }

    // User dashboard data
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user/{userId}")
    public ResponseDTO getUserDashboard(@PathVariable Integer userId) {
        return dashboardService.getUserDashboardData(userId);
    }

    // User purchase history
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user/{userId}/purchases")
    public ResponseDTO getUserPurchases(@PathVariable Integer userId) {
        return dashboardService.getUserPurchaseHistory(userId);
    }

    // User recommended products
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user/{userId}/recommendations")
    public ResponseDTO getUserRecommendations(@PathVariable Integer userId) {
        return dashboardService.getUserRecommendedProducts(userId);
    }

    // Seller dashboard data
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/seller/{userId}")
    public ResponseDTO getSellerDashboard(@PathVariable Integer userId) {
        return dashboardService.getSellerDashboardData(userId);
    }

    // Seller sales reports
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/seller/{userId}/sales")
    public ResponseDTO getSellerSales(@PathVariable Integer userId,
                                      @RequestParam(required = false) String period) {
        return dashboardService.getSellerSalesData(userId, period);
    }

    // Seller product performance
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/seller/{userId}/product-performance")
    public ResponseDTO getProductPerformance(@PathVariable Integer userId) {
        return dashboardService.getSellerProductPerformance(userId);
    }
}
