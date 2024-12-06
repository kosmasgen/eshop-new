package com.example.demo.service;

import com.example.demo.dto.ProductCategoryDTO;
import java.util.List;

public interface ProductCategoryService {

    ProductCategoryDTO createProductCategory(ProductCategoryDTO productCategoryDTO);

    List<ProductCategoryDTO> getAllProductCategories();

    ProductCategoryDTO getProductCategoryById(int id);

    ProductCategoryDTO updateProductCategory(int id, ProductCategoryDTO productCategoryDTO);

    void deleteProductCategory(int id);
}
