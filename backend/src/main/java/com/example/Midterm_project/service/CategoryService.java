package com.example.Midterm_project.service;

import org.springframework.stereotype.Service;

import com.example.Midterm_project.dto.CreateCategoryRequest;
import com.example.Midterm_project.entity.Category;
import com.example.Midterm_project.exception.DuplicateResourceException;
import com.example.Midterm_project.exception.ResourceNotFoundException;
import com.example.Midterm_project.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(CreateCategoryRequest request) {
        if (categoryRepository.existsByCategoryNameIgnoreCase(request.categoryName())) {
            throw new DuplicateResourceException("Category already exists: " + request.categoryName());
        }

        Category category = new Category();
        category.setCategoryName(request.categoryName());
        category.setDescription(request.description());

        return categoryRepository.save(category);
    }

    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }

    public Category update(Long categoryId, CreateCategoryRequest request) {
        Category category = getById(categoryId);

        if (!category.getCategoryName().equalsIgnoreCase(request.categoryName())
                && categoryRepository.existsByCategoryNameIgnoreCase(request.categoryName())) {
            throw new DuplicateResourceException("Category already exists: " + request.categoryName());
        }

        category.setCategoryName(request.categoryName());
        category.setDescription(request.description());

        return categoryRepository.save(category);
    }
}
