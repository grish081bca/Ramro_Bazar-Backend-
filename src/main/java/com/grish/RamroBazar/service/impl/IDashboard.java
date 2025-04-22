package com.grish.RamroBazar.service.impl;

import com.grish.RamroBazar.model.ResponseDTO;

public interface IDashboard {
    // Admin dashboard methods
    ResponseDTO getAdminDashboardData();
    ResponseDTO getUsersStatistics();
    ResponseDTO getProductsStatistics();
    ResponseDTO getSalesStatistics();

    // User dashboard methods
    ResponseDTO getUserDashboardData(Integer userId);
    ResponseDTO getUserPurchaseHistory(Integer userId);
    ResponseDTO getUserRecommendedProducts(Integer userId);

    // Seller dashboard methods
    ResponseDTO getSellerDashboardData(Integer userId);
    ResponseDTO getSellerSalesData(Integer userId, String period);
    ResponseDTO getSellerProductPerformance(Integer userId);
}
