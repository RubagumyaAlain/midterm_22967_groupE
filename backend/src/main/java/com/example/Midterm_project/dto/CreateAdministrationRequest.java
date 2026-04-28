package com.example.Midterm_project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAdministrationRequest(
        @NotBlank(message = "Admin code is required") String adminCode,
        @NotBlank(message = "Full name is required") String fullName,
        @Email(message = "Email format is invalid") @NotBlank(message = "Email is required") String email,
        @Valid @NotNull(message = "Location is required") CreateLocationRequest location) {
}
