package com.example.demo.service;

import com.example.demo.dto.ProductCategoryDTO;
import com.example.demo.model.ProductCategory;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.mapper.ProductCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Υλοποίηση της διεπαφής ProductCategoryService.
 * Παρέχει λειτουργικότητα για τη διαχείριση κατηγοριών προϊόντων.
 */
@Service
public class ProductCategoryImpl implements ProductCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryImpl.class);

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    /**
     * Κατασκευαστής με dependency injection.
     *
     * @param productCategoryRepository το repository για τις κατηγορίες προϊόντων.
     * @param productCategoryMapper το mapper για μετατροπές DTO <-> Entity.
     */
    @Autowired
    public ProductCategoryImpl(ProductCategoryRepository productCategoryRepository,
                               ProductCategoryMapper productCategoryMapper) {
        this.productCategoryRepository = productCategoryRepository;
        this.productCategoryMapper = productCategoryMapper;
    }

    /**
     * Δημιουργεί μια νέα κατηγορία προϊόντος.
     *
     * @param productCategoryDTO τα δεδομένα της κατηγορίας προϊόντος.
     * @return το DTO της δημιουργημένης κατηγορίας προϊόντος.
     */
    public ProductCategoryDTO createProductCategory(ProductCategoryDTO productCategoryDTO) {
        logger.info("Ξεκινάει η δημιουργία κατηγορίας προϊόντος: {}", productCategoryDTO);

        ProductCategory productCategory = productCategoryMapper.toEntity(productCategoryDTO);
        ProductCategory savedCategory = productCategoryRepository.save(productCategory);

        logger.info("Η κατηγορία προϊόντος δημιουργήθηκε με ID: {}", savedCategory.getId());
        return productCategoryMapper.toDTO(savedCategory);
    }

    /**
     * Επιστρέφει όλες τις κατηγορίες προϊόντων.
     *
     * @return λίστα με όλες τις κατηγορίες προϊόντων.
     */
    public List<ProductCategoryDTO> getAllProductCategories() {
        logger.info("Αναζητούνται όλες οι κατηγορίες προϊόντων...");

        List<ProductCategory> categories = productCategoryRepository.findAll();

        logger.debug("Βρέθηκαν {} κατηγορίες προϊόντων.", categories.size());
        return categories.stream()
                .map(productCategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Επιστρέφει μια κατηγορία προϊόντος με βάση το ID της.
     *
     * @param id το ID της κατηγορίας προϊόντος.
     * @return το DTO της κατηγορίας προϊόντος.
     * @throws RuntimeException αν η κατηγορία προϊόντος δεν βρεθεί.
     */
    public ProductCategoryDTO getProductCategoryById(int id) {
        logger.info("Αναζήτηση κατηγορίας προϊόντος με ID: {}", id);

        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η κατηγορία προϊόντος με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Η κατηγορία προϊόντος με ID " + id + " δεν βρέθηκε.");
                });

        logger.debug("Βρέθηκε κατηγορία προϊόντος: {}", productCategory);
        return productCategoryMapper.toDTO(productCategory);
    }

    /**
     * Ενημερώνει μια κατηγορία προϊόντος με βάση το ID της.
     *
     * @param id το ID της κατηγορίας προϊόντος.
     * @param productCategoryDTO τα δεδομένα της ενημέρωσης.
     * @return το ενημερωμένο DTO της κατηγορίας προϊόντος.
     * @throws RuntimeException αν η κατηγορία προϊόντος δεν βρεθεί.
     */
    public ProductCategoryDTO updateProductCategory(int id, ProductCategoryDTO productCategoryDTO) {
        logger.info("Αίτημα ενημέρωσης κατηγορίας προϊόντος με ID: {}", id);

        ProductCategory existingCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η κατηγορία προϊόντος με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Η κατηγορία προϊόντος με ID " + id + " δεν βρέθηκε.");
                });

        logger.debug("Κατηγορία προϊόντος πριν την ενημέρωση: {}", existingCategory);

        existingCategory.setName(productCategoryDTO.getName());
        ProductCategory updatedCategory = productCategoryRepository.save(existingCategory);

        logger.info("Η κατηγορία προϊόντος με ID {} ενημερώθηκε με επιτυχία.", id);
        return productCategoryMapper.toDTO(updatedCategory);
    }

    /**
     * Διαγράφει μια κατηγορία προϊόντος με βάση το ID της.
     *
     * @param id το ID της κατηγορίας προϊόντος.
     * @throws RuntimeException αν η κατηγορία προϊόντος δεν βρεθεί.
     */
    public void deleteProductCategory(int id) {
        logger.info("Αίτημα διαγραφής κατηγορίας προϊόντος με ID: {}", id);

        if (!productCategoryRepository.existsById(id)) {
            logger.error("Η κατηγορία προϊόντος με ID {} δεν βρέθηκε.", id);
            throw new RuntimeException("Η κατηγορία προϊόντος με ID " + id + " δεν βρέθηκε.");
        }

        productCategoryRepository.deleteById(id);
        logger.info("Η κατηγορία προϊόντος με ID {} διαγράφηκε με επιτυχία.", id);
    }
}
