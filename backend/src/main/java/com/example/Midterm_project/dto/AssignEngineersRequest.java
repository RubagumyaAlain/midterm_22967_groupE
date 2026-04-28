package com.example.Midterm_project.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public record AssignEngineersRequest(
        @NotEmpty(message = "At least one engineer id is required") Set<Long> engineerIds) {
}
