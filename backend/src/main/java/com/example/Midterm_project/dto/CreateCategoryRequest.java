package com.example.Midterm_project.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "Category name is required") String categoryName,
        @NotBlank(message = "Description is required") String description) {
}
