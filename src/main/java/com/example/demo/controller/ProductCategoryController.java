package com.example.demo.controller;

import com.example.demo.dto.ProductCategoryDTO;
import com.example.demo.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller για τη διαχείριση κατηγοριών προϊόντων.
 * Παρέχει endpoints για τη δημιουργία, ανάκτηση, ενημέρωση και διαγραφή κατηγοριών προϊόντων.
 */
@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    /**
     * Constructor για την εξάρτηση του ProductCategoryService.
     *
     * @param productCategoryService η υπηρεσία διαχείρισης κατηγοριών προϊόντων.
     */
    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    /**
     * Δημιουργεί μια νέα κατηγορία προϊόντος.
     *
     * @param productCategoryDTO τα δεδομένα της κατηγορίας που πρόκειται να δημιουργηθεί.
     * @return το DTO της δημιουργημένης κατηγορίας προϊόντος.
     */
    @PostMapping
    public ResponseEntity<ProductCategoryDTO> createProductCategory(@RequestBody ProductCategoryDTO productCategoryDTO) {
        ProductCategoryDTO createdCategory = productCategoryService.createProductCategory(productCategoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    /**
     * Επιστρέφει όλες τις κατηγορίες προϊόντων.
     *
     * @return λίστα με όλα τα DTO των κατηγοριών προϊόντων.
     */
    @GetMapping
    public ResponseEntity<List<ProductCategoryDTO>> getAllProductCategories() {
        List<ProductCategoryDTO> categories = productCategoryService.getAllProductCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    /**
     * Επιστρέφει την κατηγορία προϊόντος με βάση το ID της.
     *
     * @param id το ID της κατηγορίας προϊόντος.
     * @return το DTO της κατηγορίας προϊόντος.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductCategoryById(@PathVariable("id") int id) {
        ProductCategoryDTO category = productCategoryService.getProductCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * Ενημερώνει μια κατηγορία προϊόντος με βάση το ID της.
     *
     * @param id το ID της κατηγορίας που θα ενημερωθεί.
     * @param productCategoryDTO τα νέα δεδομένα της κατηγορίας προϊόντος.
     * @return το ενημερωμένο DTO της κατηγορίας προϊόντος.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> updateProductCategory(
            @PathVariable("id") int id,
            @RequestBody ProductCategoryDTO productCategoryDTO) {
        ProductCategoryDTO updatedCategory = productCategoryService.updateProductCategory(id, productCategoryDTO);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /**
     * Διαγράφει μια κατηγορία προϊόντος με βάση το ID της.
     *
     * @param id το ID της κατηγορίας που θα διαγραφεί.
     * @return HTTP Status NO_CONTENT (204) αν η διαγραφή ήταν επιτυχής.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable("id") int id) {
        productCategoryService.deleteProductCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
