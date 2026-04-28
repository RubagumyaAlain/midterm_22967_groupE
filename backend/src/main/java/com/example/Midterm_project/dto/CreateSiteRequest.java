package com.example.Midterm_project.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSiteRequest(
        @NotBlank(message = "Site code is required") String siteCode,
        @NotBlank(message = "Site name is required") String siteName,
        @Valid @NotNull(message = "Location is required") CreateLocationRequest location) {
}
