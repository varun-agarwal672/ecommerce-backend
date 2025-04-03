package com.application.ecommerce.product;

import com.application.ecommerce.product.dto.ProductRequest;
import com.application.ecommerce.product.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RequestMapping(value = "/product")
public interface ProductApi {

    @GetMapping(value = "/{id}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable(value = "id") Long id);

    @PutMapping(value = "/{id}")
    ResponseEntity<ProductResponse> updateDetailOfProduct(@PathVariable(value = "id") Long id, @RequestBody ProductRequest productRequest);

    @GetMapping(value = "/category/{category-id}")
    ResponseEntity<List<ProductResponse>> getAllProductsOfCategory(@PathVariable(value = "category-id") Long categoryId);

    @PostMapping(value = "/category/{category-id}")
    ResponseEntity<ProductResponse> addProductToCategory(@PathVariable(value = "category-id") Long categoryId, @RequestBody ProductRequest productRequest);

}
