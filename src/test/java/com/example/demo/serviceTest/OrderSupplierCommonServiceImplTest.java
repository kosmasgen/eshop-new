package com.example.demo.serviceTest;

import com.example.demo.dto.OrderDTO;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderSupplierCommonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderSupplierCommonServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderSupplierCommonServiceImpl orderSupplierCommonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchOrdersBySupplierId_ShouldReturnOrders() {
        // Mock δεδομένα
        Order order1 = new Order();
        Order order2 = new Order();
        OrderDTO orderDTO1 = new OrderDTO();
        OrderDTO orderDTO2 = new OrderDTO();

        when(orderRepository.findBySupplierId(1)).thenReturn(Arrays.asList(order1, order2));
        when(orderMapper.toDTO(order1)).thenReturn(orderDTO1);
        when(orderMapper.toDTO(order2)).thenReturn(orderDTO2);

        // Εκτέλεση
        List<OrderDTO> result = orderSupplierCommonService.searchOrdersBySupplierId(1);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findBySupplierId(1);
        verify(orderMapper, times(2)).toDTO(any(Order.class));
    }

    @Test
    void getOrdersBySupplierAndDate_ShouldReturnOrdersWithinDateRange() {
        // Mock δεδομένα
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Order order1 = new Order();
        Order order2 = new Order();
        OrderDTO orderDTO1 = new OrderDTO();
        OrderDTO orderDTO2 = new OrderDTO();

        when(orderRepository.findBySupplierIdAndCreatedAtBetween(1, startDate, endDate))
                .thenReturn(Arrays.asList(order1, order2));
        when(orderMapper.toDTO(order1)).thenReturn(orderDTO1);
        when(orderMapper.toDTO(order2)).thenReturn(orderDTO2);

        // Εκτέλεση
        List<OrderDTO> result = orderSupplierCommonService.getOrdersBySupplierAndDate(1, startDate, endDate);

        // Έλεγχοι
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findBySupplierIdAndCreatedAtBetween(1, startDate, endDate);
        verify(orderMapper, times(2)).toDTO(any(Order.class));
    }

    @Test
    void deleteOrder_ShouldDeleteOrderById() {
        // Εκτέλεση
        orderSupplierCommonService.deleteOrder(1);

        // Έλεγχοι
        verify(orderRepository, times(1)).deleteById(1);
    }
}
