package com.application.ecommerce.category;

import com.application.ecommerce.category.dto.CategoryRequest;
import com.application.ecommerce.category.dto.CategoryResponse;
import com.application.ecommerce.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    CategoryResponse createCategory(CategoryRequest categoryRequest);
}
