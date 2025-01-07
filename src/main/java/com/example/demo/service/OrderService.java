package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderDTO createOrderWithProductAndQuantity(int productId, int quantity);  // Δημιουργία παραγγελίας
    OrderDTO getOrderById(int id);  // Εύρεση παραγγελίας με βάση το ID
    OrderDTO updateOrder(int id, OrderDTO orderDTO);  // Ενημέρωση παραγγελίας
    void deleteOrder(int id);  // Διαγραφή παραγγελίας
    List<OrderDTO> searchOrders(String productName);  // Αναζήτηση παραγγελιών
    List<OrderDTO> getAllOrders();  // Επιστροφή όλων των παραγγελιών
    List<OrderDTO> searchOrdersBySupplierId(Integer supplierId);
    List<OrderDTO> getOrdersBySupplierAndDate(Integer supplierId, LocalDate startDate, LocalDate endDate);
}
