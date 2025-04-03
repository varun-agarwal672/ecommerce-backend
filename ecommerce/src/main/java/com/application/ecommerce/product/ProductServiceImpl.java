package com.application.ecommerce.product;

import com.application.ecommerce.category.CategoryRepository;
import com.application.ecommerce.model.Category;
import com.application.ecommerce.model.Product;
import com.application.ecommerce.product.dto.ProductRequest;
import com.application.ecommerce.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id"));
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImageUrl());
    }

    @Override
    public ProductResponse updateDetailOfProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with given id"));

        product.setName(productRequest.getName() !=null && !productRequest.getName().isBlank() ? productRequest.getName() : product.getName());
        product.setDescription(productRequest.getDescription() !=null && !productRequest.getDescription().isBlank() ? productRequest.getDescription() : product.getDescription());
        product.setPrice(productRequest.getName() !=null && productRequest.getPrice() != null ? productRequest.getPrice() : product.getPrice());
        product.setStock(productRequest.getStock() != null ? productRequest.getStock() : product.getStock());
        product.setImageUrl(productRequest.getImageUrl() !=null && !productRequest.getImageUrl().isBlank() ? productRequest.getImageUrl() : product.getImageUrl());

        Product updatedProduct = productRepository.save(product);
        return new ProductResponse(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice(), updatedProduct.getImageUrl());
    }

    @Override
    public List<ProductResponse> getAllProductsOfCategory(Long categoryId) {
        List<Product> productList = productRepository.findByCategoryId(categoryId);
        return productList.stream().map(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponse.setImageUrl(product.getImageUrl());
            return productResponse;
        }).toList();
    }

    @Override
    public ProductResponse addProductToCategory(Long categoryId, ProductRequest productRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setImageUrl(productRequest.getImageUrl());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return new ProductResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice(), savedProduct.getImageUrl());
    }
}
