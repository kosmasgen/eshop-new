package com.example.demo.mapper;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Η κλάση OrderMapper είναι υπεύθυνη για την μετατροπή των οντοτήτων Order σε DTO και το αντίστροφο.
 * Χρησιμοποιεί το ModelMapper για την αντιστοίχιση πεδίων μεταξύ των αντικειμένων.
 */
@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    /**
     * Κατασκευαστής που δημιουργεί το αντικείμενο ModelMapper για τη μετατροπή των οντοτήτων.
     */
    public OrderMapper() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Μετατρέπει μια οντότητα Order σε DTO Order.
     *
     * @param order Η οντότητα Order.
     * @return Το αντικείμενο OrderDTO που περιέχει τα δεδομένα από την οντότητα.
     */
    public OrderDTO toDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    /**
     * Μετατρέπει ένα DTO Order σε οντότητα Order.
     *
     * @param orderDTO Το DTO που περιέχει τα δεδομένα του Order.
     * @return Η οντότητα Order.
     */
    public Order toEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    /**
     * Ενημερώνει την υπάρχουσα οντότητα Order με τα δεδομένα από το DTO.
     *
     * @param orderDTO Το DTO που περιέχει τα δεδομένα.
     * @param existingOrder Η υπάρχουσα οντότητα Order που θα ενημερωθεί.
     */
    public void toEntity(OrderDTO orderDTO, Order existingOrder) {
        modelMapper.map(orderDTO, existingOrder);
    }
}
