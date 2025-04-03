package com.application.ecommerce.category;

import com.application.ecommerce.category.dto.CategoryRequest;
import com.application.ecommerce.category.dto.CategoryResponse;
import com.application.ecommerce.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RequestMapping(value = "/category")
public interface CategoryApi {

    @GetMapping
    ResponseEntity<List<CategoryResponse>> getAllCategories();

    @PostMapping
    ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest);
}
