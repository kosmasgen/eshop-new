package com.example.demo.mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;
    private final ProductMapper productMapper;
    private final SupplierMapper supplierMapper;

    public OrderMapper(ModelMapper modelMapper, ProductMapper productMapper, SupplierMapper supplierMapper) {
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
        this.supplierMapper = supplierMapper;
    }

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);
        dto.setProductId(order.getProduct().getId());
        dto.setSupplier(supplierMapper.toDTO(order.getSupplier()));
        return dto;
    }

    public Order createOrderFromProduct(Product product, int quantity) {
        Order order = new Order();
        order.setProduct(product);
        order.setSupplier(product.getSupplier());
        order.setQuantity(quantity);
        order.setPrice(product.getPrice());
        order.setTotalPrice(quantity * product.getPrice());
        return order;
    }

    public void updateOrderFromDTO(Order existingOrder, Product product, Supplier supplier, OrderDTO orderDTO) {
        existingOrder.setProduct(product);
        existingOrder.setSupplier(supplier);
        existingOrder.setQuantity(orderDTO.getQuantity());
        existingOrder.setPrice(product.getPrice());
        existingOrder.setTotalPrice(orderDTO.getQuantity() * product.getPrice());
    }

    // Νέα Μέθοδος για τη Δημιουργία Order από ProductDTO
    public Order createOrderFromProductDTO(ProductDTO productDTO, int quantity) {
        Product product = productMapper.toEntity(productDTO);
        Supplier supplier = product.getSupplier();

        Order order = new Order();
        order.setProduct(product);
        order.setSupplier(supplier);
        order.setQuantity(quantity);
        order.setPrice(product.getPrice());
        order.setTotalPrice(quantity * product.getPrice());
        return order;
    }

    public Order toEntity(OrderDTO orderDTO, Product product, Supplier supplier) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setProduct(product);
        order.setSupplier(supplier);
        return order;
    }
}
