package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderSupplierCommonServiceImpl implements OrderSupplierCommonService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderSupplierCommonServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDTO> searchOrdersBySupplierId(Integer supplierId) {
        return orderRepository.findBySupplierId(supplierId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersBySupplierAndDate(Integer supplierId, LocalDate startDate, LocalDate endDate) {
        return orderRepository.findBySupplierIdAndCreatedAtBetween(supplierId, startDate, endDate)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }
}
