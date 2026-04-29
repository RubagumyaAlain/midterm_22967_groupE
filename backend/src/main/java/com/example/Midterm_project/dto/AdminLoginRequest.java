package com.example.Midterm_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequest(
        @Email(message = "Email format is invalid") @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "Password is required") String password,
        @NotBlank(message = "Role is required") String role) {
}
