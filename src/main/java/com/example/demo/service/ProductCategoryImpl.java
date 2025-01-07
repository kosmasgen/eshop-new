package com.example.demo.service;

import com.example.demo.dto.ProductCategoryDTO;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ProductCategory;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.mapper.ProductCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    private final MessageSource messageSource;

    /**
     * Κατασκευαστής με dependency injection.
     *
     * @param productCategoryRepository το repository για τις κατηγορίες προϊόντων.
     * @param productCategoryMapper το mapper για μετατροπές DTO <-> Entity.
     * @param messageSource το MessageSource για διεθνοποίηση.
     */
    @Autowired
    public ProductCategoryImpl(ProductCategoryRepository productCategoryRepository,
                               ProductCategoryMapper productCategoryMapper,
                               MessageSource messageSource) {
        this.productCategoryRepository = productCategoryRepository;
        this.productCategoryMapper = productCategoryMapper;
        this.messageSource = messageSource;
    }

    @Override
    public ProductCategoryDTO createProductCategory(ProductCategoryDTO productCategoryDTO) {
        logger.info("Ξεκινάει η δημιουργία κατηγορίας προϊόντος: {}", productCategoryDTO);

        ProductCategory productCategory = productCategoryMapper.toEntity(productCategoryDTO);
        ProductCategory savedCategory = productCategoryRepository.save(productCategory);

        logger.info("Η κατηγορία προϊόντος δημιουργήθηκε με ID: {}", savedCategory.getId());
        return productCategoryMapper.toDTO(savedCategory);
    }

    @Override
    public List<ProductCategoryDTO> getAllProductCategories() {
        logger.info("Αναζητούνται όλες οι κατηγορίες προϊόντων...");

        List<ProductCategory> categories = productCategoryRepository.findAll();

        logger.debug("Βρέθηκαν {} κατηγορίες προϊόντων.", categories.size());
        return categories.stream()
                .map(productCategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDTO getProductCategoryById(int id) {
        logger.info("Αναζήτηση κατηγορίας προϊόντος με ID: {}", id);

        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η κατηγορία προϊόντος με ID {} δεν βρέθηκε.", id);
                    return new ResourceNotFoundException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND, id);
                });

        logger.debug("Βρέθηκε κατηγορία προϊόντος: {}", productCategory);
        return productCategoryMapper.toDTO(productCategory);
    }

    @Override
    public ProductCategoryDTO updateProductCategory(int id, ProductCategoryDTO productCategoryDTO) {
        logger.info("Αίτημα ενημέρωσης κατηγορίας προϊόντος με ID: {}", id);

        ProductCategory existingCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Η κατηγορία προϊόντος με ID {} δεν βρέθηκε.", id);
                    return new ResourceNotFoundException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND, id);
                });

        logger.debug("Κατηγορία προϊόντος πριν την ενημέρωση: {}", existingCategory);

        existingCategory.setName(productCategoryDTO.getName());
        ProductCategory updatedCategory = productCategoryRepository.save(existingCategory);

        logger.info("Η κατηγορία προϊόντος με ID {} ενημερώθηκε με επιτυχία.", id);
        return productCategoryMapper.toDTO(updatedCategory);
    }

    @Override
    public void deleteProductCategory(int id) {
        logger.info("Αίτημα διαγραφής κατηγορίας προϊόντος με ID: {}", id);

        if (!productCategoryRepository.existsById(id)) {
            logger.error("Η κατηγορία προϊόντος με ID {} δεν βρέθηκε.", id);
            throw new ResourceNotFoundException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND, id);
        }

        productCategoryRepository.deleteById(id);
        logger.info("Η κατηγορία προϊόντος με ID {} διαγράφηκε με επιτυχία.", id);
    }
}
