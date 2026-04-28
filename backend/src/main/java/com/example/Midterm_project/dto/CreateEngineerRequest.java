package com.example.Midterm_project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEngineerRequest(
        @NotBlank(message = "Engineer code is required") String engineerCode,
        @NotBlank(message = "Full name is required") String fullName,
        @NotBlank(message = "Specialty is required") String specialty,
        @Valid @NotNull(message = "Location is required") CreateLocationRequest location) {
}
