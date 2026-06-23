package com.homemadegoodies.api.service;

import com.homemadegoodies.api.dto.ProductRequest;
import com.homemadegoodies.api.dto.ProductResponse;
import com.homemadegoodies.api.model.Product;
import com.homemadegoodies.api.model.User;
import com.homemadegoodies.api.repository.ProductRepository;
import com.homemadegoodies.api.repository.UserRepository;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
    }

    public List<ProductResponse> getMyProducts(String email) {
        User owner = getUserByEmail(email);
        return productRepository.findByOwnerOrderByCreatedAtDesc(owner).stream().map(this::toResponse).toList();
    }

    public ProductResponse createProduct(ProductRequest request, String email) {
        User owner = getUserByEmail(email);
        Product product = new Product();
        applyRequest(product, request);
        product.setOwner(owner);
        return toResponse(productRepository.save(product));
    }

    public ProductResponse updateProduct(Long id, ProductRequest request, String email) {
        Product product = getProductForOwner(id, email);
        applyRequest(product, request);
        return toResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id, String email) {
        Product product = getProductForOwner(id, email);
        productRepository.delete(product);
    }

    private Product getProductForOwner(Long id, String email) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (!product.getOwner().getEmail().equals(email)) {
            throw new AccessDeniedException("You can only change your own products");
        }
        return product;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private void applyRequest(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory(), product.getImageUrl(), product.getOwner().getId(), product.getOwner().getName(), product.getCreatedAt());
    }
}
