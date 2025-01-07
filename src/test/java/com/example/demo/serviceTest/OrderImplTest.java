package com.example.demo.serviceTest;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.SupplierMapper;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private SupplierService supplierService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private SupplierMapper supplierMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() {
        int productId = 1;
        int quantity = 5;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setPrice(20.0);

        Product product = new Product();
        Order order = new Order();
        Order savedOrder = new Order();
        OrderDTO orderDTO = new OrderDTO();

        when(productService.getProductById(productId)).thenReturn(productDTO);
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(orderMapper.createOrderFromProductDTO(productDTO, quantity)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(savedOrder);
        when(orderMapper.toDTO(savedOrder)).thenReturn(orderDTO);

        OrderDTO result = orderService.createOrderWithProductAndQuantity(productId, quantity);

        assertNotNull(result);
        verify(orderRepository).save(order);
    }

    @Test
    void getOrderById_ShouldReturnOrder() {
        int orderId = 1;
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(order)).thenReturn(orderDTO);

        OrderDTO result = orderService.getOrderById(orderId);

        assertNotNull(result);
        verify(orderRepository).findById(orderId);
    }

    @Test
    void getOrderById_ShouldThrowException_WhenOrderNotFound() {
        int orderId = 1;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(orderId));
    }

    @Test
    void updateOrder_ShouldReturnUpdatedOrder() {
        int orderId = 1;

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(2);
        orderDTO.setSupplierId(3);

        ProductDTO productDTO = new ProductDTO();
        SupplierDTO supplierDTO = new SupplierDTO();

        Product product = new Product();
        Supplier supplier = new Supplier();

        Order existingOrder = new Order();
        Order updatedOrder = new Order();
        OrderDTO updatedOrderDTO = new OrderDTO();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(productService.getProductById(orderDTO.getProductId())).thenReturn(productDTO);
        when(supplierService.getSupplierById(orderDTO.getSupplierId())).thenReturn(supplierDTO);
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(supplierMapper.toEntity(supplierDTO)).thenReturn(supplier);
        doNothing().when(orderMapper).updateOrderFromDTO(existingOrder, product, supplier, orderDTO);
        when(orderRepository.save(existingOrder)).thenReturn(updatedOrder);
        when(orderMapper.toDTO(updatedOrder)).thenReturn(updatedOrderDTO);

        OrderDTO result = orderService.updateOrder(orderId, orderDTO);

        assertNotNull(result);
        verify(orderRepository).save(existingOrder);
    }

    @Test
    void deleteOrder_ShouldDeleteOrder() {
        int orderId = 1;
        Order order = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(order);

        orderService.deleteOrder(orderId);

        verify(orderRepository).delete(order);
    }

    @Test
    void searchOrders_ShouldReturnOrders() {
        String productName = "TestProduct";
        List<Order> orders = List.of(new Order());
        List<OrderDTO> orderDTOs = List.of(new OrderDTO());

        when(orderRepository.findByProductProductNameContainingIgnoreCase(productName)).thenReturn(orders);
        when(orderMapper.toDTO(orders.get(0))).thenReturn(orderDTOs.get(0));

        List<OrderDTO> result = orderService.searchOrders(productName);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository).findByProductProductNameContainingIgnoreCase(productName);
    }

    @Test
    void getAllOrders_ShouldReturnAllOrders() {
        List<Order> orders = List.of(new Order());
        List<OrderDTO> orderDTOs = List.of(new OrderDTO());

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.toDTO(orders.get(0))).thenReturn(orderDTOs.get(0));

        List<OrderDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getOrdersBySupplierAndDate_ShouldReturnOrders() {
        int supplierId = 1;
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now();
        List<Order> orders = List.of(new Order());
        List<OrderDTO> orderDTOs = List.of(new OrderDTO());

        when(orderRepository.findBySupplierIdAndCreatedAtBetween(supplierId, startDate, endDate)).thenReturn(orders);
        when(orderMapper.toDTO(orders.get(0))).thenReturn(orderDTOs.get(0));

        List<OrderDTO> result = orderService.getOrdersBySupplierAndDate(supplierId, startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository).findBySupplierIdAndCreatedAtBetween(supplierId, startDate, endDate);
    }
}
