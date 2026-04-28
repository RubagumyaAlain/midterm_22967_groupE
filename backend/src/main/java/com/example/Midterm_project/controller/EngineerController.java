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

import com.example.Midterm_project.dto.CreateEngineerRequest;
import com.example.Midterm_project.entity.Engineer;
import com.example.Midterm_project.service.EngineerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/engineers")
public class EngineerController {

    private final EngineerService engineerService;

    public EngineerController(EngineerService engineerService) {
        this.engineerService = engineerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Engineer create(@Valid @RequestBody CreateEngineerRequest request) {
        return engineerService.create(request);
    }

    @GetMapping("/{engineerId}")
    public Engineer getById(@PathVariable Long engineerId) {
        return engineerService.getById(engineerId);
    }

    @PutMapping("/{engineerId}")
    public Engineer update(@PathVariable Long engineerId, @Valid @RequestBody CreateEngineerRequest request) {
        return engineerService.update(engineerId, request);
    }
}
