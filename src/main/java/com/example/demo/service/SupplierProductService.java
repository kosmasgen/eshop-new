package com.example.demo.service;

import com.example.demo.dto.SupplierProductDTO;
import java.util.List;

public interface SupplierProductService {

    SupplierProductDTO createSupplierProduct(SupplierProductDTO supplierProductDTO);

    List<SupplierProductDTO> getAllSupplierProducts();

    SupplierProductDTO getSupplierProductById(int id);

    SupplierProductDTO updateSupplierProduct(int id, SupplierProductDTO supplierProductDTO);

    void deleteSupplierProduct(int id);
}
