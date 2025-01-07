package com.example.demo.mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.model.ProductCategory;
import com.example.demo.model.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.typeMap(Product.class, ProductDTO.class).addMappings(mapper ->
                mapper.map(src -> src.getCategory().getName(), ProductDTO::setType)
        );
    }

    public ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product toEntity(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);

        if (productDTO.getSupplier() != null) {
            Supplier supplier = new Supplier();
            supplier.setId(productDTO.getSupplier().getId());
            product.setSupplier(supplier);
        }

        if (productDTO.getType() != null) {
            ProductCategory category = new ProductCategory();
            category.setName(productDTO.getType());
            product.setCategory(category);
        }

        return product;
    }

    public void updateEntityFromDTO(ProductDTO productDTO, Product existingProduct) {
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());

        if (productDTO.getType() != null) {
            ProductCategory category = new ProductCategory();
            category.setName(productDTO.getType());
            existingProduct.setCategory(category);
        }

        if (productDTO.getSupplier() != null) {
            Supplier supplier = new Supplier();
            supplier.setId(productDTO.getSupplier().getId());
            existingProduct.setSupplier(supplier);
        }
    }

    // Νέα μέθοδος: Μετατροπή μόνο του ID σε Product Entity
    public Product toEntityWithId(Integer productId) {
        Product product = new Product();
        product.setId(productId);
        return product;
    }
}
