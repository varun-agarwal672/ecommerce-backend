package com.application.ecommerce.product;

import com.application.ecommerce.product.dto.ProductRequest;
import com.application.ecommerce.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi{

    private final ProductService productService;

    @Override
    public ResponseEntity<ProductResponse> getProductById(Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Override
    public ResponseEntity<ProductResponse> updateDetailOfProduct(Long id, ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateDetailOfProduct(id, productRequest));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getAllProductsOfCategory(Long categoryId) {
        return ResponseEntity.ok(productService.getAllProductsOfCategory(categoryId));
    }

    @Override
    public ResponseEntity<ProductResponse> addProductToCategory(Long categoryId, ProductRequest productRequest) {
        return ResponseEntity.ok(productService.addProductToCategory(categoryId, productRequest));
    }
}
