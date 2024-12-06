package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Υλοποίηση της διεπαφής OrderService.
 * Παρέχει λειτουργικότητα για τη διαχείριση παραγγελιών.
 */
@Service
public class OrderImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderImpl.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    /**
     * Κατασκευαστής με dependency injection.
     *
     * @param orderRepository το repository για τις παραγγελίες.
     * @param productRepository το repository για τα προϊόντα.
     * @param orderMapper το mapper για μετατροπές DTO <-> Entity.
     */
    @Autowired
    public OrderImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Δημιουργεί μια νέα παραγγελία για ένα προϊόν με συγκεκριμένη ποσότητα.
     *
     * @param productId το ID του προϊόντος.
     * @param quantity η ποσότητα της παραγγελίας.
     * @return το DTO της δημιουργημένης παραγγελίας.
     * @throws IllegalArgumentException αν το productId ή η ποσότητα είναι μικρότερη ή ίση με το μηδέν.
     */
    @Override
    public OrderDTO createOrderWithProductAndQuantity(int productId, int quantity) {
        logger.info("Ξεκινάει η δημιουργία παραγγελίας για προϊόν με ID: {} και ποσότητα: {}", productId, quantity);

        if (productId <= 0) {
            logger.error("Το productId πρέπει να είναι μεγαλύτερο από το μηδέν.");
            throw new IllegalArgumentException("Το productId πρέπει να είναι μεγαλύτερο από το μηδέν.");
        }
        if (quantity <= 0) {
            logger.error("Η ποσότητα πρέπει να είναι μεγαλύτερη από το μηδέν.");
            throw new IllegalArgumentException("Η ποσότητα πρέπει να είναι μεγαλύτερη από το μηδέν.");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Το προϊόν με ID {} δεν βρέθηκε.", productId);
                    return new RuntimeException("Το προϊόν με ID " + productId + " δεν βρέθηκε.");
                });

        logger.debug("Βρέθηκε προϊόν: {}", product);

        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setPrice(product.getPrice());
        order.setTotalPrice(quantity * product.getPrice());
        order.setSupplier(product.getSupplier());

        Order savedOrder = orderRepository.save(order);

        logger.info("Η παραγγελία δημιουργήθηκε με επιτυχία: {}", savedOrder);
        return orderMapper.toDTO(savedOrder);
    }

    /**
     * Επιστρέφει μια παραγγελία με βάση το ID της.
     *
     * @param id το ID της παραγγελίας.
     * @return το DTO της παραγγελίας.
     * @throws RuntimeException αν η παραγγελία δεν βρεθεί.
     */
    @Override
    public OrderDTO getOrderById(int id) {
        logger.info("Αναζήτηση παραγγελίας με ID: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η παραγγελία με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Η παραγγελία με ID " + id + " δεν βρέθηκε.");
                });

        logger.debug("Βρέθηκε παραγγελία: {}", order);
        return orderMapper.toDTO(order);
    }

    /**
     * Ενημερώνει μια παραγγελία με βάση το ID της.
     *
     * @param id το ID της παραγγελίας.
     * @param orderDTO τα δεδομένα της ενημέρωσης.
     * @return το ενημερωμένο DTO της παραγγελίας.
     * @throws RuntimeException αν η παραγγελία δεν βρεθεί.
     */
    @Override
    public OrderDTO updateOrder(int id, OrderDTO orderDTO) {
        logger.info("Αίτημα ενημέρωσης παραγγελίας με ID: {}", id);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η παραγγελία με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Η παραγγελία με ID " + id + " δεν βρέθηκε.");
                });

        logger.debug("Παραγγελία πριν την ενημέρωση: {}", existingOrder);

        orderMapper.toEntity(orderDTO, existingOrder);
        existingOrder.setTotalPrice(existingOrder.getQuantity() * existingOrder.getPrice());

        Order updatedOrder = orderRepository.save(existingOrder);

        logger.info("Η παραγγελία με ID {} ενημερώθηκε με επιτυχία.", id);
        return orderMapper.toDTO(updatedOrder);
    }

    /**
     * Διαγράφει μια παραγγελία με βάση το ID της.
     *
     * @param id το ID της παραγγελίας.
     * @throws RuntimeException αν η παραγγελία δεν βρεθεί.
     */
    @Override
    public void deleteOrder(int id) {
        logger.info("Αίτημα διαγραφής παραγγελίας με ID: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η παραγγελία με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Η παραγγελία με ID " + id + " δεν βρέθηκε.");
                });

        orderRepository.delete(order);
        logger.info("Η παραγγελία με ID {} διαγράφηκε με επιτυχία.", id);
    }

    /**
     * Αναζητά παραγγελίες με βάση το όνομα του προϊόντος.
     *
     * @param productName το όνομα του προϊόντος.
     * @return λίστα με παραγγελίες που ταιριάζουν.
     */
    @Override
    public List<OrderDTO> searchOrders(String productName) {
        logger.info("Αναζήτηση παραγγελιών για προϊόν με όνομα: {}", productName);

        List<Order> orders = orderRepository.findByProductProductNameContainingIgnoreCase(productName);

        if (orders.isEmpty()) {
            logger.warn("Δεν βρέθηκαν παραγγελίες για το προϊόν: {}", productName);
        } else {
            logger.debug("Βρέθηκαν {} παραγγελίες για το προϊόν: {}", orders.size(), productName);
        }

        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Επιστρέφει όλες τις παραγγελίες.
     *
     * @return λίστα με όλες τις παραγγελίες.
     */
    @Override
    public List<OrderDTO> getAllOrders() {
        logger.info("Αναζήτηση όλων των παραγγελιών...");

        List<Order> orders = orderRepository.findAll();

        logger.debug("Βρέθηκαν {} παραγγελίες.", orders.size());
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
