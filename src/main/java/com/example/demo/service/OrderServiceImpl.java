package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.SupplierMapper;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final SupplierService supplierService;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final SupplierMapper supplierMapper;
    private final MessageSource messageSource;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService,
                            SupplierService supplierService, OrderMapper orderMapper,
                            ProductMapper productMapper, SupplierMapper supplierMapper,
                            MessageSource messageSource) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.supplierService = supplierService;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.supplierMapper = supplierMapper;
        this.messageSource = messageSource;
    }

    @Override
    public OrderDTO createOrderWithProductAndQuantity(int productId, int quantity) {
        logger.info("Δημιουργία παραγγελίας για προϊόν με ID: {} και ποσότητα: {}", productId, quantity);

        ProductDTO productDTO = productService.getProductById(productId);
        if (productDTO == null) {
            throw new ResourceNotFoundException(ErrorCode.PRODUCT_NOT_FOUND,productId);
        }

        Order order = orderMapper.createOrderFromProductDTO(productDTO, quantity);
        Order savedOrder = orderRepository.save(order);

        logger.info("Η παραγγελία δημιουργήθηκε με επιτυχία: {}", savedOrder);
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(int id) {
        logger.info("Ανάκτηση παραγγελίας με ID: {}", id);
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PRODUCT_NOT_FOUND,id));
    }

    @Override
    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        logger.info("Ενημέρωση παραγγελίας με ID: {}", id);

        // Αναζητά την παραγγελία με το ID
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η παραγγελία με ID {} δεν βρέθηκε.", id);
                    // Αν δεν βρεθεί παραγγελία, ρίχνουμε ResourceNotFoundException
                    String localizedMessage = messageSource.getMessage(
                            ErrorCode.ORDER_NOT_FOUND.getCode(),
                            new Object[]{id},  // Παράμετρος για το ID της παραγγελίας
                            LocaleContextHolder.getLocale()
                    );
                    return new ResourceNotFoundException(ErrorCode.ORDER_NOT_FOUND, localizedMessage);
                });

        // Ανάκτηση του προϊόντος και του προμηθευτή
        Product product = productMapper.toEntity(productService.getProductById(orderDTO.getProductId()));
        Supplier supplier = supplierMapper.toEntity(supplierService.getSupplierById(orderDTO.getSupplierId()));

        // Ενημέρωση παραγγελίας με τα νέα δεδομένα
        orderMapper.updateOrderFromDTO(existingOrder, product, supplier, orderDTO);

        // Αποθήκευση της ενημερωμένης παραγγελίας
        Order updatedOrder = orderRepository.save(existingOrder);

        logger.info("Η παραγγελία ενημερώθηκε με επιτυχία: {}", updatedOrder);
        return orderMapper.toDTO(updatedOrder);  // Επιστροφή του DTO της ενημερωμένης παραγγελίας
    }


    @Override
    public void deleteOrder(int id) {
        logger.info("Διαγραφή παραγγελίας με ID: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η παραγγελία με ID {} δεν βρέθηκε.", id);
                    String localizedMessage = messageSource.getMessage(
                            ErrorCode.ORDER_NOT_FOUND.getCode(),
                            new Object[]{id},
                            LocaleContextHolder.getLocale()
                    );
                    return new ResourceNotFoundException(ErrorCode.ORDER_NOT_FOUND, localizedMessage);
                });

        orderRepository.delete(order);  // Διαγραφή της παραγγελίας
        logger.info("Η παραγγελία διαγράφηκε με επιτυχία.");
    }


    @Override
    public List<OrderDTO> searchOrders(String productName) {
        logger.info("Αναζήτηση παραγγελιών με όνομα προϊόντος: {}", productName);
        return orderRepository.findByProductProductNameContainingIgnoreCase(productName).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        logger.info("Ανάκτηση όλων των παραγγελιών...");
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> searchOrdersBySupplierId(Integer supplierId) {
        logger.info("Αναζήτηση παραγγελιών για προμηθευτή με ID: {}", supplierId);
        return orderRepository.findBySupplierId(supplierId).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersBySupplierAndDate(Integer supplierId, LocalDate startDate, LocalDate endDate) {
        logger.info("Ανάκτηση παραγγελιών για προμηθευτή με ID: {} από {} έως {}", supplierId, startDate, endDate);
        return orderRepository.findBySupplierIdAndCreatedAtBetween(supplierId, startDate, endDate).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
