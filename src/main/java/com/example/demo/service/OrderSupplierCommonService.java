package com.example.demo.service;

import com.example.demo.dto.OrderDTO;

import java.time.LocalDate;
import java.util.List;

public interface OrderSupplierCommonService {
    List<OrderDTO> getOrdersBySupplierAndDate(Integer supplierId, LocalDate startDate, LocalDate endDate);

    List<OrderDTO> searchOrdersBySupplierId(Integer supplierId);

    void deleteOrder(Integer orderId);
}
