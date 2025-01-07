package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImpl implements ProductService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProductImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO getProductById(Integer productId) {
        logger.info("Αναζήτηση προϊόντος με ID: {}", productId);

        // Ανάκτηση του προϊόντος από το repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Το προϊόν με ID {} δεν βρέθηκε.", productId);
                    return new RuntimeException("Το προϊόν με ID " + productId + " δεν βρέθηκε.");
                });

        // Μετατροπή του Product σε ProductDTO χρησιμοποιώντας τον ProductMapper
        ProductDTO productDTO = productMapper.toDTO(product);

        return productDTO; // Επιστροφή του ProductDTO
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        logger.info("Ξεκινάει η δημιουργία προϊόντος: {}", productDTO);

        Product product = productMapper.toEntity(productDTO); // Μετατροπή σε Entity
        Product savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct); // Επιστροφή του DTO
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        logger.info("Αναζητούνται όλα τα προϊόντα...");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        logger.info("Αίτημα ενημέρωσης προϊόντος με ID: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Το προϊόν με ID {} δεν βρέθηκε.", id);
                    return new RuntimeException("Το προϊόν με ID " + id + " δεν βρέθηκε.");
                });

        productMapper.updateEntityFromDTO(productDTO, existingProduct); // Ενημέρωση Entity με τα δεδομένα του DTO
        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toDTO(updatedProduct); // Επιστροφή του DTO
    }

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

    @Override
    public List<ProductDTO> searchProductsByName(String name) {
        logger.info("Αναζητούνται προϊόντα με όνομα: {}", name);
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(name);
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }
}
