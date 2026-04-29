package com.example.Midterm_project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Midterm_project.dto.AdminLoginRequest;
import com.example.Midterm_project.dto.AdminLoginResponse;
import com.example.Midterm_project.service.StaffAdministrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final StaffAdministrationService staffAdministrationService;

    public AuthController(StaffAdministrationService staffAdministrationService) {
        this.staffAdministrationService = staffAdministrationService;
    }

    @PostMapping("/login")
    public AdminLoginResponse login(@Valid @RequestBody AdminLoginRequest request) {
        return staffAdministrationService.authenticate(request);
    }
}
