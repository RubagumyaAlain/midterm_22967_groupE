package com.example.Midterm_project.dto;

public record AdminLoginResponse(
        Long administratorId,
        String fullName,
        String email,
        String role) {
}
