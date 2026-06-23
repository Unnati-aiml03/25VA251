package com.homemadegoodies.api.controller;

import com.homemadegoodies.api.dto.ApiMessage;
import com.homemadegoodies.api.dto.ProductRequest;
import com.homemadegoodies.api.dto.ProductResponse;
import com.homemadegoodies.api.service.ProductService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping
    public List<ProductResponse> allProducts() { return productService.getAllProducts(); }

    @GetMapping("/my")
    public List<ProductResponse> myProducts(Principal principal) { return productService.getMyProducts(principal.getName()); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request, Principal principal) { return productService.createProduct(request, principal.getName()); }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request, Principal principal) { return productService.updateProduct(id, request, principal.getName()); }

    @DeleteMapping("/{id}")
    public ApiMessage delete(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(id, principal.getName());
        return new ApiMessage("Product deleted");
    }
}
