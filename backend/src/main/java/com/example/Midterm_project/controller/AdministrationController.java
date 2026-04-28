package com.example.Midterm_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Midterm_project.dto.CreateAdministrationRequest;
import com.example.Midterm_project.entity.StaffAdministration;
import com.example.Midterm_project.service.StaffAdministrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/administrators")
public class AdministrationController {

    private final StaffAdministrationService staffAdministrationService;

    public AdministrationController(StaffAdministrationService staffAdministrationService) {
        this.staffAdministrationService = staffAdministrationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StaffAdministration create(@Valid @RequestBody CreateAdministrationRequest request) {
        return staffAdministrationService.create(request);
    }

    @GetMapping("/{administratorId}")
    public StaffAdministration getById(@PathVariable Long administratorId) {
        return staffAdministrationService.getById(administratorId);
    }

    @PutMapping("/{administratorId}")
    public StaffAdministration update(
            @PathVariable Long administratorId,
            @Valid @RequestBody CreateAdministrationRequest request) {
        return staffAdministrationService.update(administratorId, request);
    }
}
