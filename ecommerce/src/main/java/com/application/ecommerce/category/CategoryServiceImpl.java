package com.application.ecommerce.category;

import com.application.ecommerce.category.dto.CategoryRequest;
import com.application.ecommerce.category.dto.CategoryResponse;
import com.application.ecommerce.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category -> new CategoryResponse(category.getId(), category.getName(), category.getIconUrl())).toList();
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        Category createdCategory = categoryRepository.save(category);
        return new CategoryResponse(createdCategory.getId(), createdCategory.getName(), createdCategory.getIconUrl());
    }
}
