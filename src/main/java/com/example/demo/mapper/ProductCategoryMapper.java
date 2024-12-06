package com.example.demo.mapper;

import com.example.demo.dto.ProductCategoryDTO;
import com.example.demo.model.ProductCategory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Η κλάση ProductCategoryMapper είναι υπεύθυνη για τη μετατροπή της οντότητας ProductCategory σε DTO και το αντίστροφο.
 * Χρησιμοποιεί το ModelMapper για την αντιστοίχιση πεδίων μεταξύ των αντικειμένων.
 */
@Component
public class ProductCategoryMapper {

    private final ModelMapper modelMapper;

    /**
     * Κατασκευαστής που δημιουργεί το αντικείμενο ModelMapper για τη μετατροπή των οντοτήτων.
     */
    public ProductCategoryMapper() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Μετατρέπει μια οντότητα ProductCategory σε DTO ProductCategory.
     *
     * @param productCategory Η οντότητα ProductCategory.
     * @return Το αντικείμενο ProductCategoryDTO που περιέχει τα δεδομένα από την οντότητα.
     */
    public ProductCategoryDTO toDTO(ProductCategory productCategory) {
        return modelMapper.map(productCategory, ProductCategoryDTO.class);
    }

    /**
     * Μετατρέπει ένα DTO ProductCategory σε οντότητα ProductCategory.
     *
     * @param productCategoryDTO Το DTO που περιέχει τα δεδομένα του ProductCategory.
     * @return Η οντότητα ProductCategory.
     */
    public ProductCategory toEntity(ProductCategoryDTO productCategoryDTO) {
        return modelMapper.map(productCategoryDTO, ProductCategory.class);
    }

}
