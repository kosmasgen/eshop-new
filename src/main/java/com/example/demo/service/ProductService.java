package com.example.demo.service;

import com.example.demo.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(int id);
    ProductDTO updateProduct(int id, ProductDTO productDTO);
    void deleteProduct(int id);
}
