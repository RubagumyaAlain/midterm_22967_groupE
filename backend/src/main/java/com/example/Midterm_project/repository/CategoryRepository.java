package com.example.Midterm_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Midterm_project.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
