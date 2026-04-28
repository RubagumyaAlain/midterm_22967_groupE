package com.example.Midterm_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Midterm_project.dto.CreateCategoryRequest;
import com.example.Midterm_project.entity.Category;
import com.example.Midterm_project.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.create(request);
    }

    @GetMapping("/{categoryId}")
    public Category getById(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }

    @PutMapping("/{categoryId}")
    public Category update(@PathVariable Long categoryId, @Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.update(categoryId, request);
    }
}
