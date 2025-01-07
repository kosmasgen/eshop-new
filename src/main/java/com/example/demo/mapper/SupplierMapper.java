package com.example.demo.mapper;

import com.example.demo.dto.SupplierDTO;
import com.example.demo.model.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper για τη χαρτογράφηση μεταξύ της οντότητας Supplier και του DTO SupplierDTO.
 */
@Component
public class SupplierMapper {

    private final ModelMapper modelMapper;

    /**
     * Constructor που δέχεται ένα instance του ModelMapper.
     *
     * @param modelMapper το ModelMapper που θα χρησιμοποιηθεί.
     */
    public SupplierMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Μετατρέπει μια οντότητα Supplier σε DTO SupplierDTO.
     *
     * @param supplier η οντότητα Supplier.
     * @return το αντίστοιχο SupplierDTO.
     */
    public SupplierDTO toDTO(Supplier supplier) {
        return modelMapper.map(supplier, SupplierDTO.class);
    }

    /**
     * Μετατρέπει ένα DTO SupplierDTO σε οντότητα Supplier.
     *
     * @param supplierDTO το DTO SupplierDTO.
     * @return η αντίστοιχη οντότητα Supplier.
     */
    public Supplier toEntity(SupplierDTO supplierDTO) {
        return modelMapper.map(supplierDTO, Supplier.class);
    }

    /**
     * Ενημερώνει μια υπάρχουσα οντότητα Supplier με δεδομένα από ένα SupplierDTO.
     *
     * @param supplierDTO το DTO με τα νέα δεδομένα.
     * @param supplier    η υπάρχουσα οντότητα που θα ενημερωθεί.
     */
    public void updateEntityFromDTO(SupplierDTO supplierDTO, Supplier supplier) {
        if (supplierDTO.getId() != null && !supplierDTO.getId().equals(supplier.getId())) {
            throw new IllegalArgumentException("Δεν επιτρέπεται αλλαγή του ID.");
        }
        modelMapper.map(supplierDTO, supplier);
    }
    public Supplier toEntityWithId(Integer supplierId) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        return supplier;
    }

}
