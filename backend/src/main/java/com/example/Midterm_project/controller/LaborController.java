package com.example.Midterm_project.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Midterm_project.dto.CreateLaborRequest;
import com.example.Midterm_project.entity.Labor;
import com.example.Midterm_project.service.LaborService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/labors")
public class LaborController {

    private final LaborService laborService;

    public LaborController(LaborService laborService) {
        this.laborService = laborService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Labor create(@Valid @RequestBody CreateLaborRequest request) {
        return laborService.create(request);
    }

    @GetMapping("/{laborId}")
    public Labor getById(@PathVariable Long laborId) {
        return laborService.getById(laborId);
    }

    @PutMapping("/{laborId}")
    public Labor update(@PathVariable Long laborId, @Valid @RequestBody CreateLaborRequest request) {
        return laborService.update(laborId, request);
    }

    @GetMapping
    public Page<Labor> getLabors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "fullName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return laborService.getPaginatedLabors(page, size, sortBy, direction);
    }

    @GetMapping({"/by-location", "/by-province"})
    public List<Labor> getLaborsByLocation(
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String provinceName,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String cell,
            @RequestParam(required = false) String village) {
        String requestedProvince = province != null ? province : provinceName;
        return laborService.findByLocation(provinceCode, requestedProvince, district, sector, cell, village);
    }
}
