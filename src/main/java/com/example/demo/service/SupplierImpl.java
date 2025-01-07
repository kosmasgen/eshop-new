package com.example.demo.service;

import com.example.demo.dto.SupplierDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.SupplierMapper;
import com.example.demo.model.Supplier;
import com.example.demo.repository.SupplierRepository;
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
public class SupplierImpl implements SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierImpl.class);

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final OrderSupplierCommonService orderSupplierCommonService;
    private final MessageSource messageSource;

    @Autowired
    public SupplierImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper,
                        OrderSupplierCommonService orderSupplierCommonService, MessageSource messageSource) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
        this.orderSupplierCommonService = orderSupplierCommonService;
        this.messageSource = messageSource;
    }

    @Override
    public SupplierDTO getSupplierById(Integer id) {
        logger.info("Αναζήτηση προμηθευτή με ID: {}", id);
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ο προμηθευτής με ID {} δεν βρέθηκε.", id);
                    String localizedMessage = messageSource.getMessage(
                            ErrorCode.SUPPLIER_NOT_FOUND.getCode(),
                            new Object[]{id},
                            LocaleContextHolder.getLocale()
                    );
                    return new ResourceNotFoundException(ErrorCode.SUPPLIER_NOT_FOUND, id);
                });
        return supplierMapper.toDTO(supplier);
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        logger.info("Ανάκτηση όλων των προμηθευτών...");
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        logger.info("Δημιουργία νέου προμηθευτή...");
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    @Override
    public SupplierDTO updateSupplier(Integer id, SupplierDTO supplierDTO) {
        logger.info("Ενημέρωση προμηθευτή με ID: {}", id);
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ο προμηθευτής με ID {} δεν βρέθηκε.", id);
                    String localizedMessage = messageSource.getMessage(
                            ErrorCode.SUPPLIER_NOT_FOUND.getCode(),
                            new Object[]{id},
                            LocaleContextHolder.getLocale()
                    );
                    return new ResourceNotFoundException(ErrorCode.SUPPLIER_NOT_FOUND, id);
                });

        supplierMapper.updateEntityFromDTO(supplierDTO, existingSupplier);
        Supplier updatedSupplier = supplierRepository.save(existingSupplier);

        return supplierMapper.toDTO(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Integer id) {
        logger.info("Διαγραφή προμηθευτή με ID: {}", id);

        List<OrderDTO> orders = orderSupplierCommonService.searchOrdersBySupplierId(id);

        if (!orders.isEmpty()) {
            logger.warn("Ο προμηθευτής με ID {} έχει σχετικές παραγγελίες που θα διαγραφούν.", id);
            orders.forEach(order -> orderSupplierCommonService.deleteOrder(order.getId()));
        }

        supplierRepository.deleteById(id);
        logger.info("Ο προμηθευτής με ID {} διαγράφηκε με επιτυχία.", id);
    }

    @Override
    public List<SupplierDTO> findSuppliersByName(String name) {
        logger.info("Αναζήτηση προμηθευτών με όνομα: {}", name);
        return supplierRepository.findByFirstNameContainingIgnoreCase(name).stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierDTO> findSuppliersByLocation(String location) {
        logger.info("Αναζήτηση προμηθευτών με τοποθεσία: {}", location);
        return supplierRepository.findByLocationContainingIgnoreCase(location)
                .stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateTurnover(Integer supplierId, LocalDate startDate, LocalDate endDate) {
        logger.info("Υπολογισμός τζίρου για τον προμηθευτή με ID: {}", supplierId);

        List<OrderDTO> orders = orderSupplierCommonService.getOrdersBySupplierAndDate(supplierId, startDate, endDate);

        double turnover = orders.stream()
                .mapToDouble(OrderDTO::getTotalPrice)
                .sum();

        logger.info("Ο συνολικός τζίρος είναι: {}", turnover);
        return turnover;
    }
}
