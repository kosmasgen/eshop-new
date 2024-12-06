package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller για τη διαχείριση παραγγελιών.
 * Παρέχει endpoints για CRUD λειτουργίες, καθώς και για την αναζήτηση παραγγελιών.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Constructor για την εξάρτηση του OrderService.
     *
     * @param orderService η υπηρεσία διαχείρισης παραγγελιών.
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Δημιουργεί μια νέα παραγγελία.
     *
     * @param orderDTO τα δεδομένα της παραγγελίας που πρόκειται να δημιουργηθεί.
     * @return το DTO της δημιουργημένης παραγγελίας.
     * @throws IllegalArgumentException αν το productId ή η ποσότητα δεν είναι έγκυρα.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        if (orderDTO.getProductId() <= 0) {
            throw new IllegalArgumentException("Το productId είναι υποχρεωτικό.");
        }
        if (orderDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Η ποσότητα πρέπει να είναι μεγαλύτερη από το μηδέν.");
        }
        return orderService.createOrderWithProductAndQuantity(orderDTO.getProductId(), orderDTO.getQuantity());
    }

    /**
     * Επιστρέφει μια παραγγελία με βάση το ID της.
     *
     * @param id το ID της παραγγελίας.
     * @return το DTO της παραγγελίας.
     */
    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    /**
     * Ενημερώνει μια υπάρχουσα παραγγελία με βάση το ID της.
     *
     * @param id το ID της παραγγελίας προς ενημέρωση.
     * @param orderDTO τα νέα δεδομένα της παραγγελίας.
     * @return το ενημερωμένο DTO της παραγγελίας.
     */
    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, orderDTO);
    }

    /**
     * Διαγράφει μια παραγγελία με βάση το ID της.
     *
     * @param id το ID της παραγγελίας που θα διαγραφεί.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    /**
     * Αναζητά παραγγελίες με βάση το όνομα του προϊόντος.
     *
     * @param productName το όνομα ή τμήμα του ονόματος του προϊόντος.
     * @return λίστα από παραγγελίες που ταιριάζουν.
     */
    @GetMapping("/search")
    public List<OrderDTO> searchOrders(@RequestParam String productName) {
        return orderService.searchOrders(productName);
    }

    /**
     * Επιστρέφει όλες τις παραγγελίες.
     *
     * @return λίστα με όλες τις παραγγελίες.
     */
    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
}
