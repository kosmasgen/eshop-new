package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller για τη διαχείριση προϊόντων.
 * Παρέχει endpoints για τη δημιουργία, ανάκτηση, ενημέρωση και διαγραφή προϊόντων.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

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
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Επιστρέφει όλα τα προϊόντα.
     *
     * @return λίστα με DTO όλων των προϊόντων με HTTP Status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Επιστρέφει το προϊόν με βάση το ID του.
     *
     * @param id το ID του προϊόντος.
     * @return το DTO του προϊόντος με HTTP Status 200 (OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") int id) {
        ProductDTO product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
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
            @PathVariable("id") int id,
            @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    /**
     * Διαγράφει ένα προϊόν με βάση το ID του.
     *
     * @param id το ID του προϊόντος που θα διαγραφεί.
     * @return HTTP Status 204 (No Content) αν η διαγραφή ήταν επιτυχής.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
