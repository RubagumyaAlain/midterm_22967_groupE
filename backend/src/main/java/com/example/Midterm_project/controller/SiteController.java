package com.example.Midterm_project.controller;

import java.util.List;

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

import com.example.Midterm_project.dto.AssignEngineersRequest;
import com.example.Midterm_project.dto.CreateSiteRequest;
import com.example.Midterm_project.entity.Site;
import com.example.Midterm_project.service.SiteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Site create(@Valid @RequestBody CreateSiteRequest request) {
        return siteService.create(request);
    }

    @GetMapping("/{siteId}")
    public Site getById(@PathVariable Long siteId) {
        return siteService.getById(siteId);
    }

    @PutMapping("/{siteId}")
    public Site update(@PathVariable Long siteId, @Valid @RequestBody CreateSiteRequest request) {
        return siteService.update(siteId, request);
    }

    @PutMapping("/{siteId}/engineers")
    public Site assignEngineers(@PathVariable Long siteId, @Valid @RequestBody AssignEngineersRequest request) {
        return siteService.assignEngineers(siteId, request.engineerIds());
    }

    @GetMapping("/sorted")
    public List<Site> getSortedSites(
            @RequestParam(defaultValue = "siteName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return siteService.getSortedSites(sortBy, direction);
    }
}
