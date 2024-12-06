package com.example.demo.mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Η κλάση ProductMapper είναι υπεύθυνη για τη μετατροπή της οντότητας Product σε DTO και το αντίστροφο.
 * Χρησιμοποιεί το ModelMapper για την αντιστοίχιση πεδίων μεταξύ των αντικειμένων.
 */
@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    /**
     * Κατασκευαστής που δημιουργεί το αντικείμενο ModelMapper για τη μετατροπή των οντοτήτων.
     */
    public ProductMapper() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Μετατρέπει μια οντότητα Product σε DTO ProductDTO.
     *
     * @param product Η οντότητα Product.
     * @return Το αντικείμενο ProductDTO που περιέχει τα δεδομένα από την οντότητα.
     */
    public ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    /**
     * Μετατρέπει ένα DTO ProductDTO σε οντότητα Product.
     *
     * @param productDTO Το DTO που περιέχει τα δεδομένα του Product.
     * @return Η οντότητα Product.
     */
    public Product toEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
