package com.example.demo.mapper;

import com.example.demo.dto.SupplierDTO;
import com.example.demo.model.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Η κλάση SupplierMapper είναι υπεύθυνη για τη μετατροπή της οντότητας Supplier σε DTO SupplierDTO και το αντίστροφο.
 * Χρησιμοποιεί το ModelMapper για την αντιστοίχιση πεδίων μεταξύ των αντικειμένων.
 */
@Component
public class SupplierMapper {

    private final ModelMapper modelMapper;

    /**
     * Κατασκευαστής που δημιουργεί το αντικείμενο ModelMapper για τη μετατροπή των οντοτήτων.
     */
    public SupplierMapper() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Μετατρέπει μια οντότητα Supplier σε DTO SupplierDTO.
     *
     * @param supplier Η οντότητα Supplier.
     * @return Το αντικείμενο SupplierDTO που περιέχει τα δεδομένα από την οντότητα.
     */
    public SupplierDTO toDTO(Supplier supplier) {
        return modelMapper.map(supplier, SupplierDTO.class);
    }

    /**
     * Μετατρέπει ένα DTO SupplierDTO σε οντότητα Supplier.
     *
     * @param supplierDTO Το DTO που περιέχει τα δεδομένα του Supplier.
     * @return Η οντότητα Supplier.
     */
    public Supplier toEntity(SupplierDTO supplierDTO) {
        return modelMapper.map(supplierDTO, Supplier.class);
    }

    /**
     * Ενημερώνει την υπάρχουσα οντότητα Supplier με τα δεδομένα του SupplierDTO.
     *
     * @param supplierDTO Το DTO που περιέχει τα νέα δεδομένα.
     * @param supplier Η υπάρχουσα οντότητα Supplier που θα ενημερωθεί.
     * @throws IllegalArgumentException Αν το ID στο DTO είναι διαφορετικό από το ID της οντότητας.
     */
    public void updateEntityFromDTO(SupplierDTO supplierDTO, Supplier supplier) {
        // Έλεγχος αν το ID στο DTO είναι διαφορετικό από το ID της οντότητας
        if (supplierDTO.getId() != null && !supplierDTO.getId().equals(supplier.getId())) {
            throw new IllegalArgumentException("Δεν επιτρέπεται αλλαγή του ID.");
        }

        // Ενημέρωση των πεδίων χειροκίνητα
        supplier.setFirstName(supplierDTO.getFirstName());
        supplier.setLastName(supplierDTO.getLastName());
        supplier.setTelephone(supplierDTO.getTelephone());
        supplier.setAfm(supplierDTO.getAfm());
        supplier.setLocation(supplierDTO.getLocation());
    }
}
