package com.example.demo.service;

import com.example.demo.dto.SupplierDTO;
import com.example.demo.mapper.SupplierMapper;
import com.example.demo.model.Supplier;
import com.example.demo.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Υλοποίηση της διεπαφής SupplierService.
 * Παρέχει λειτουργικότητα για τη διαχείριση προμηθευτών.
 */
@Service
public class SupplierImpl implements SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierImpl.class);

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final OrderRepository orderRepository;

    /**
     * Κατασκευαστής με dependency injection.
     *
     * @param supplierRepository το repository για τους προμηθευτές.
     * @param supplierMapper το mapper για μετατροπές DTO <-> Entity.
     * @param orderRepository το repository για τις παραγγελίες.
     */
    public SupplierImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper, OrderRepository orderRepository) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
        this.orderRepository = orderRepository;
    }

    /**
     * Δημιουργεί έναν νέο προμηθευτή.
     *
     * @param supplierDTO τα δεδομένα του προμηθευτή.
     * @return το DTO του δημιουργημένου προμηθευτή.
     */
    @Override
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        logger.info("Ξεκινάει η δημιουργία προμηθευτή: {}", supplierDTO);

        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);

        logger.info("Ο προμηθευτής δημιουργήθηκε με ID: {}", savedSupplier.getId());
        return supplierMapper.toDTO(savedSupplier);
    }

    /**
     * Ενημερώνει έναν προμηθευτή με βάση το ID του.
     *
     * @param id το ID του προμηθευτή.
     * @param supplierDTO τα δεδομένα της ενημέρωσης.
     * @return το ενημερωμένο DTO του προμηθευτή.
     */
    @Override
    public SupplierDTO updateSupplier(int id, SupplierDTO supplierDTO) {
        logger.info("Αίτημα ενημέρωσης προμηθευτή με ID: {}", id);

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ο προμηθευτής με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Ο προμηθευτής με ID " + id + " δεν βρέθηκε.");
                });

        logger.debug("Προμηθευτής πριν την ενημέρωση: {}", supplier);

        supplierMapper.updateEntityFromDTO(supplierDTO, supplier);
        Supplier updatedSupplier = supplierRepository.save(supplier);

        logger.info("Ο προμηθευτής με ID {} ενημερώθηκε με επιτυχία.", id);
        return supplierMapper.toDTO(updatedSupplier);
    }

    /**
     * Διαγράφει έναν προμηθευτή με βάση το ID του.
     *
     * @param id το ID του προμηθευτή.
     */
    @Override
    public void deleteSupplier(int id) {
        logger.info("Αίτημα διαγραφής προμηθευτή με ID: {}", id);

        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            logger.info("Ο προμηθευτής με ID {} διαγράφηκε με επιτυχία.", id);
        } else {
            logger.error("Ο προμηθευτής με ID {} δεν βρέθηκε.", id);
            throw new RuntimeException("Ο προμηθευτής με ID " + id + " δεν βρέθηκε.");
        }
    }

    /**
     * Επιστρέφει έναν προμηθευτή με βάση το ID του.
     *
     * @param id το ID του προμηθευτή.
     * @return το DTO του προμηθευτή.
     */
    @Override
    public SupplierDTO getSupplierById(int id) {
        logger.info("Αναζήτηση προμηθευτή με ID: {}", id);

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ο προμηθευτής με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Ο προμηθευτής με ID " + id + " δεν βρέθηκε.");
                });

        return supplierMapper.toDTO(supplier);
    }

    /**
     * Επιστρέφει όλους τους προμηθευτές.
     *
     * @return λίστα με όλους τους προμηθευτές.
     */
    @Override
    public List<SupplierDTO> getAllSuppliers() {
        logger.info("Αναζητούνται όλοι οι προμηθευτές...");

        List<Supplier> suppliers = supplierRepository.findAll();

        logger.debug("Βρέθηκαν {} προμηθευτές.", suppliers.size());
        return suppliers.stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Αναζητά προμηθευτές με βάση το όνομα ή μέρος του ονόματός τους.
     *
     * @param name το όνομα ή μέρος του ονόματος του προμηθευτή.
     * @return λίστα με τους προμηθευτές που ταιριάζουν.
     */
    @Override
    public List<SupplierDTO> findSuppliersByName(String name) {
        logger.info("Αναζήτηση προμηθευτών με όνομα ή τμήμα ονόματος: {}", name);

        List<Supplier> suppliers = supplierRepository.findByFirstNameContainingIgnoreCase(name);

        if (suppliers.isEmpty()) {
            logger.warn("Δεν βρέθηκαν προμηθευτές με το όνομα: {}", name);
        }

        return suppliers.stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Αναζητά προμηθευτές με βάση την τοποθεσία τους.
     *
     * @param location η τοποθεσία του προμηθευτή.
     * @return λίστα με τους προμηθευτές που ταιριάζουν.
     */
    @Override
    public List<SupplierDTO> findSuppliersByLocation(String location) {
        logger.info("Αναζήτηση προμηθευτών με τοποθεσία: {}", location);

        List<Supplier> suppliers = supplierRepository.findByLocationContainingIgnoreCase(location);

        if (suppliers.isEmpty()) {
            logger.warn("Δεν βρέθηκαν προμηθευτές με την τοποθεσία: {}", location);
        }

        return suppliers.stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Υπολογίζει τον τζίρο ενός προμηθευτή για συγκεκριμένο χρονικό διάστημα.
     *
     * @param supplierId το ID του προμηθευτή.
     * @param startDate η αρχική ημερομηνία.
     * @param endDate η τελική ημερομηνία.
     * @return ο συνολικός τζίρος.
     */
    @Override
    public Double calculateTurnover(Integer supplierId, LocalDate startDate, LocalDate endDate) {
        logger.info("Υπολογισμός τζίρου για τον προμηθευτή με ID: {}, από {} έως {}", supplierId, startDate, endDate);

        List<Order> orders = orderRepository.findBySupplierId(supplierId);

        double turnover = orders.stream()
                .filter(order -> {
                    LocalDate orderDate = order.getCreatedAt().toLocalDate();
                    return (orderDate.isEqual(startDate) || orderDate.isAfter(startDate)) &&
                            (orderDate.isEqual(endDate) || orderDate.isBefore(endDate));
                })
                .mapToDouble(Order::getTotalPrice)
                .sum();

        logger.info("Ο τζίρος για τον προμηθευτή με ID {} είναι: {}", supplierId, turnover);
        return turnover;
    }
}
