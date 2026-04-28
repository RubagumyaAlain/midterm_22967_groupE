package com.example.Midterm_project.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateLocationRequest(
        @NotBlank(message = "Province code is required") String provinceCode,
        @NotBlank(message = "Province is required") String province,
        @NotBlank(message = "District is required") String district,
        @NotBlank(message = "Sector is required") String sector,
        @NotBlank(message = "Cell is required") String cell,
        @NotBlank(message = "Village is required") String village) {
}
