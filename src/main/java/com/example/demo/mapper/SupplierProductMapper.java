package com.example.demo.mapper;

import com.example.demo.dto.SupplierProductDTO;
import com.example.demo.model.SupplierProduct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Η κλάση SupplierProductMapper είναι υπεύθυνη για τη μετατροπή της οντότητας SupplierProduct σε DTO SupplierProductDTO
 * και το αντίστροφο.
 * Χρησιμοποιεί το ModelMapper για την αντιστοίχιση πεδίων μεταξύ των αντικειμένων.
 */
@Component
public class SupplierProductMapper {

    private final ModelMapper modelMapper;

    /**
     * Κατασκευαστής που δημιουργεί το αντικείμενο ModelMapper για τη μετατροπή των οντοτήτων.
     */
    public SupplierProductMapper() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Μετατρέπει μια οντότητα SupplierProduct σε DTO SupplierProductDTO.
     *
     * @param supplierProduct Η οντότητα SupplierProduct.
     * @return Το αντικείμενο SupplierProductDTO που περιέχει τα δεδομένα από την οντότητα.
     */
    public SupplierProductDTO toDTO(SupplierProduct supplierProduct) {
        return modelMapper.map(supplierProduct, SupplierProductDTO.class);
    }

    /**
     * Μετατρέπει ένα DTO SupplierProductDTO σε οντότητα SupplierProduct.
     *
     * @param supplierProductDTO Το DTO που περιέχει τα δεδομένα του SupplierProduct.
     * @return Η οντότητα SupplierProduct.
     */
    public SupplierProduct toEntity(SupplierProductDTO supplierProductDTO) {
        return modelMapper.map(supplierProductDTO, SupplierProduct.class);
    }
}
