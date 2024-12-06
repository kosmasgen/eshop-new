package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Υλοποίηση της διεπαφής ProductService.
 * Παρέχει λειτουργικότητα για τη διαχείριση προϊόντων.
 */
@Service
public class ProductImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Κατασκευαστής με dependency injection.
     *
     * @param productRepository το repository για τα προϊόντα.
     * @param productMapper το mapper για μετατροπές DTO <-> Entity.
     */
    @Autowired
    public ProductImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Δημιουργεί ένα νέο προϊόν.
     *
     * @param productDTO τα δεδομένα του προϊόντος.
     * @return το DTO του δημιουργημένου προϊόντος.
     */
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        logger.info("Ξεκινάει η δημιουργία προϊόντος: {}", productDTO);

        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);

        logger.info("Το προϊόν δημιουργήθηκε με ID: {}", savedProduct.getId());
        return productMapper.toDTO(savedProduct);
    }

    /**
     * Επιστρέφει όλα τα προϊόντα.
     *
     * @return λίστα με όλα τα προϊόντα.
     */
    @Override
    public List<ProductDTO> getAllProducts() {
        logger.info("Αναζητούνται όλα τα προϊόντα...");

        List<Product> products = productRepository.findAll();

        logger.debug("Βρέθηκαν {} προϊόντα.", products.size());
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Επιστρέφει ένα προϊόν με βάση το ID του.
     *
     * @param id το ID του προϊόντος.
     * @return το DTO του προϊόντος.
     * @throws RuntimeException αν το προϊόν δεν βρεθεί.
     */
    @Override
    public ProductDTO getProductById(int id) {
        logger.info("Αναζήτηση προϊόντος με ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Το προϊόν με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Το προϊόν με ID " + id + " δεν βρέθηκε.");
                });

        logger.debug("Βρέθηκε προϊόν: {}", product);
        return productMapper.toDTO(product);
    }

    /**
     * Ενημερώνει ένα προϊόν με βάση το ID του.
     *
     * @param id το ID του προϊόντος.
     * @param productDTO τα δεδομένα της ενημέρωσης.
     * @return το ενημερωμένο DTO του προϊόντος.
     * @throws RuntimeException αν το προϊόν δεν βρεθεί.
     */
    @Override
    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        logger.info("Αίτημα ενημέρωσης προϊόντος με ID: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Το προϊόν με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Το προϊόν με ID " + id + " δεν βρέθηκε.");
                });

        logger.debug("Προϊόν πριν την ενημέρωση: {}", existingProduct);

        productMapper.toEntity(productDTO); // Μεταφορά δεδομένων από DTO στην οντότητα

        Product updatedProduct = productRepository.save(existingProduct);

        logger.info("Το προϊόν με ID {} ενημερώθηκε με επιτυχία.", id);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Διαγράφει ένα προϊόν με βάση το ID του.
     *
     * @param id το ID του προϊόντος.
     * @throws RuntimeException αν το προϊόν δεν βρεθεί.
     */
    @Override
    public void deleteProduct(int id) {
        logger.info("Αίτημα διαγραφής προϊόντος με ID: {}", id);

        if (!productRepository.existsById(id)) {
            logger.error("Το προϊόν με ID {} δεν βρέθηκε.", id);
            throw new RuntimeException("Το προϊόν με ID " + id + " δεν βρέθηκε.");
        }

        productRepository.deleteById(id);
        logger.info("Το προϊόν με ID {} διαγράφηκε με επιτυχία.", id);
    }
}
