package com.example.demo.service;

import com.example.demo.dto.SupplierProductDTO;
import java.util.List;

public interface SupplierProductService {

    SupplierProductDTO createSupplierProduct(SupplierProductDTO supplierProductDTO);

    List<SupplierProductDTO> getAllSupplierProducts();

    SupplierProductDTO getSupplierProductById(Integer id);

    SupplierProductDTO updateSupplierProduct(Integer id, SupplierProductDTO supplierProductDTO);

    void deleteSupplierProduct(Integer id);
}
