package com.example.Midterm_project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLaborRequest(
        @NotBlank(message = "Employee code is required") String employeeCode,
        @NotBlank(message = "Full name is required") String fullName,
        @NotBlank(message = "Phone number is required") String phoneNumber,
        @NotNull(message = "Efficiency score is required") @Min(value = 0, message = "Efficiency score must be at least 0") @Max(value = 100, message = "Efficiency score must not exceed 100") Integer efficiencyScore,
        @NotNull(message = "Administrator id is required") Long administratorId,
        @NotNull(message = "Category id is required") Long categoryId,
        @NotNull(message = "Site id is required") Long siteId,
        @Valid @NotNull(message = "Location is required") CreateLocationRequest location) {
}
