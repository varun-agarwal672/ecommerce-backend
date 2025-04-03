package com.application.ecommerce.product;

import com.application.ecommerce.product.dto.ProductRequest;
import com.application.ecommerce.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse getProductById(Long id);

    ProductResponse updateDetailOfProduct(Long id, ProductRequest productRequest);

    List<ProductResponse> getAllProductsOfCategory(Long categoryId);

    ProductResponse addProductToCategory(Long categoryId, ProductRequest productRequest);
}
