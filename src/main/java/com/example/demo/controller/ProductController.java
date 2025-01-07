package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller για τη διαχείριση προϊόντων.
 * Παρέχει endpoints για τη δημιουργία, ανάκτηση, ενημέρωση και διαγραφή προϊόντων.
 */
@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    /**
     * Constructor για την εξάρτηση του ProductService.
     *
     * @param productService η υπηρεσία διαχείρισης προϊόντων.
     */
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Δημιουργεί ένα νέο προϊόν.
     *
     * @param productDTO τα δεδομένα του προϊόντος που πρόκειται να δημιουργηθεί.
     * @return το DTO του δημιουργημένου προϊόντος με HTTP Status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductDTO productDTO) {
        logger.info("Αίτημα δημιουργίας προϊόντος: {}", productDTO);
        ProductDTO createdProduct = productService.createProduct(productDTO);
        logger.info("Το προϊόν δημιουργήθηκε με επιτυχία: {}", createdProduct);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Επιστρέφει όλα τα προϊόντα.
     *
     * @return λίστα με DTO όλων των προϊόντων με HTTP Status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        logger.info("Αίτημα ανάκτησης όλων των προϊόντων.");
        List<ProductDTO> products = productService.getAllProducts();
        logger.info("Ανακτήθηκαν {} προϊόντα.", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Επιστρέφει το προϊόν με βάση το ID του.
     *
     * @param id το ID του προϊόντος.
     * @return το DTO του προϊόντος με HTTP Status 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Integer id) {
        logger.info("Αίτημα ανάκτησης προϊόντος με ID: {}", id);
        try {
            ProductDTO product = productService.getProductById(id);
            logger.info("Βρέθηκε προϊόν: {}", product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Το προϊόν με ID {} δεν βρέθηκε.", id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Ενημερώνει ένα προϊόν με βάση το ID του.
     *
     * @param id         το ID του προϊόντος που θα ενημερωθεί.
     * @param productDTO τα νέα δεδομένα του προϊόντος.
     * @return το ενημερωμένο DTO του προϊόντος με HTTP Status 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable("id") Integer id,
            @Validated @RequestBody ProductDTO productDTO) {
        logger.info("Αίτημα ενημέρωσης προϊόντος με ID: {}", id);
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        logger.info("Το προϊόν με ID {} ενημερώθηκε με επιτυχία: {}", id, updatedProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    /**
     * Διαγράφει ένα προϊόν με βάση το ID του.
     *
     * @param id το ID του προϊόντος που θα διαγραφεί.
     * @return HTTP Status 204 (No Content) αν η διαγραφή ήταν επιτυχής.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer id) {
        logger.info("Αίτημα διαγραφής προϊόντος με ID: {}", id);
        productService.deleteProduct(id);
        logger.info("Το προϊόν με ID {} διαγράφηκε με επιτυχία.", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Αναζητά προϊόντα με βάση το όνομα.
     *
     * @param name το όνομα ή τμήμα του ονόματος του προϊόντος.
     * @return λίστα με DTO των προϊόντων που ταιριάζουν με το όνομα.
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam("name") String name) {
        logger.info("Αίτημα αναζήτησης προϊόντων με όνομα: {}", name);
        List<ProductDTO> products = productService.searchProductsByName(name);
        logger.info("Βρέθηκαν {} προϊόντα για το όνομα: {}", products.size(), name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
