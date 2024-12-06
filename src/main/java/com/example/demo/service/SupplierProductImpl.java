package com.example.demo.service;

import com.example.demo.dto.SupplierProductDTO;
import com.example.demo.model.SupplierProduct;
import com.example.demo.repository.SupplierProductRepository;
import com.example.demo.mapper.SupplierProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Υλοποίηση της διεπαφής SupplierProductService.
 * Παρέχει λειτουργικότητα για τη διαχείριση σχέσεων προμηθευτών-προϊόντων.
 */
@Service
public class SupplierProductImpl implements SupplierProductService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierProductImpl.class);

    private final SupplierProductRepository supplierProductRepository;
    private final SupplierProductMapper supplierProductMapper;

    /**
     * Κατασκευαστής με dependency injection.
     *
     * @param supplierProductRepository το repository για τις σχέσεις προμηθευτών-προϊόντων.
     * @param supplierProductMapper το mapper για μετατροπές DTO <-> Entity.
     */
    @Autowired
    public SupplierProductImpl(SupplierProductRepository supplierProductRepository, SupplierProductMapper supplierProductMapper) {
        this.supplierProductRepository = supplierProductRepository;
        this.supplierProductMapper = supplierProductMapper;
    }

    /**
     * Δημιουργεί μια νέα σχέση προμηθευτή-προϊόντος.
     *
     * @param supplierProductDTO τα δεδομένα της σχέσης.
     * @return το DTO της δημιουργημένης σχέσης.
     */
    @Override
    public SupplierProductDTO createSupplierProduct(SupplierProductDTO supplierProductDTO) {
        logger.info("Ξεκινάει η δημιουργία σύνδεσης προμηθευτή-προϊόντος: {}", supplierProductDTO);

        SupplierProduct supplierProduct = supplierProductMapper.toEntity(supplierProductDTO);
        SupplierProduct savedProduct = supplierProductRepository.save(supplierProduct);

        logger.info("Η σύνδεση προμηθευτή-προϊόντος δημιουργήθηκε με ID: {}", savedProduct.getId());
        return supplierProductMapper.toDTO(savedProduct);
    }

    /**
     * Επιστρέφει όλες τις σχέσεις προμηθευτών-προϊόντων.
     *
     * @return λίστα με όλες τις σχέσεις.
     */
    @Override
    public List<SupplierProductDTO> getAllSupplierProducts() {
        logger.info("Αναζητούνται όλες οι συνδέσεις προμηθευτών-προϊόντων...");

        List<SupplierProduct> supplierProducts = supplierProductRepository.findAll();

        logger.debug("Βρέθηκαν {} συνδέσεις προμηθευτών-προϊόντων.", supplierProducts.size());
        return supplierProducts.stream()
                .map(supplierProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Επιστρέφει μια σχέση προμηθευτή-προϊόντος με βάση το ID της.
     *
     * @param id το ID της σχέσης.
     * @return το DTO της σχέσης.
     */
    @Override
    public SupplierProductDTO getSupplierProductById(int id) {
        logger.info("Αναζήτηση σύνδεσης προμηθευτή-προϊόντος με ID: {}", id);

        SupplierProduct supplierProduct = supplierProductRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η σύνδεση προμηθευτή-προϊόντος με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Η σύνδεση προμηθευτή-προϊόντος με ID " + id + " δεν βρέθηκε.");
                });

        logger.debug("Βρέθηκε σύνδεση προμηθευτή-προϊόντος: {}", supplierProduct);
        return supplierProductMapper.toDTO(supplierProduct);
    }

    /**
     * Ενημερώνει μια σχέση προμηθευτή-προϊόντος με βάση το ID της.
     *
     * @param id το ID της σχέσης.
     * @param supplierProductDTO τα δεδομένα της ενημέρωσης.
     * @return το ενημερωμένο DTO της σχέσης.
     */
    @Override
    public SupplierProductDTO updateSupplierProduct(int id, SupplierProductDTO supplierProductDTO) {
        logger.info("Αίτημα ενημέρωσης σύνδεσης προμηθευτή-προϊόντος με ID: {}", id);

        SupplierProduct existingSupplierProduct = supplierProductRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η σύνδεση προμηθευτή-προϊόντος με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Η σύνδεση προμηθευτή-προϊόντος με ID " + id + " δεν βρέθηκε.");
                });

        logger.debug("Σύνδεση προμηθευτή-προϊόντος πριν την ενημέρωση: {}", existingSupplierProduct);

        supplierProductMapper.toEntity(supplierProductDTO);

        SupplierProduct updatedSupplierProduct = supplierProductRepository.save(existingSupplierProduct);

        logger.info("Η σύνδεση προμηθευτή-προϊόντος με ID {} ενημερώθηκε με επιτυχία.", id);
        return supplierProductMapper.toDTO(updatedSupplierProduct);
    }

    /**
     * Διαγράφει μια σχέση προμηθευτή-προϊόντος με βάση το ID της.
     *
     * @param id το ID της σχέσης.
     */
    @Override
    public void deleteSupplierProduct(int id) {
        logger.info("Αίτημα διαγραφής σύνδεσης προμηθευτή-προϊόντος με ID: {}", id);

        if (!supplierProductRepository.existsById(id)) {
            logger.error("Η σύνδεση προμηθευτή-προϊόντος με ID {} δεν βρέθηκε.", id);
            throw new RuntimeException("Η σύνδεση προμηθευτή-προϊόντος με ID " + id + " δεν βρέθηκε.");
        }

        supplierProductRepository.deleteById(id);
        logger.info("Η σύνδεση προμηθευτή-προϊόντος με ID {} διαγράφηκε με επιτυχία.", id);
    }
}
