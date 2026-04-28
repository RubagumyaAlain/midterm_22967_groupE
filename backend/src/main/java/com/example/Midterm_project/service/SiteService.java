package com.example.Midterm_project.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Midterm_project.dto.CreateLocationRequest;
import com.example.Midterm_project.dto.CreateSiteRequest;
import com.example.Midterm_project.entity.Engineer;
import com.example.Midterm_project.entity.Location;
import com.example.Midterm_project.entity.Site;
import com.example.Midterm_project.exception.DuplicateResourceException;
import com.example.Midterm_project.exception.ResourceNotFoundException;
import com.example.Midterm_project.repository.EngineerRepository;
import com.example.Midterm_project.repository.SiteRepository;

@Service
public class SiteService {

    private final SiteRepository siteRepository;
    private final EngineerRepository engineerRepository;

    public SiteService(SiteRepository siteRepository, EngineerRepository engineerRepository) {
        this.siteRepository = siteRepository;
        this.engineerRepository = engineerRepository;
    }

    public Site create(CreateSiteRequest request) {
        if (siteRepository.existsBySiteCodeIgnoreCase(request.siteCode())) {
            throw new DuplicateResourceException("Site code already exists: " + request.siteCode());
        }

        Site site = new Site();
        applyRequest(site, request);

        return siteRepository.save(site);
    }

    public Site getById(Long siteId) {
        return siteRepository.findById(siteId)
                .orElseThrow(() -> new ResourceNotFoundException("Site not found with id: " + siteId));
    }

    public Site update(Long siteId, CreateSiteRequest request) {
        Site site = getById(siteId);

        if (!site.getSiteCode().equalsIgnoreCase(request.siteCode())
                && siteRepository.existsBySiteCodeIgnoreCase(request.siteCode())) {
            throw new DuplicateResourceException("Site code already exists: " + request.siteCode());
        }

        applyRequest(site, request);
        return siteRepository.save(site);
    }

    @Transactional
    public Site assignEngineers(Long siteId, Set<Long> engineerIds) {
        Site site = getById(siteId);

        Set<Engineer> engineers = new LinkedHashSet<>(engineerRepository.findAllById(engineerIds));
        if (engineers.size() != engineerIds.size()) {
            throw new ResourceNotFoundException("One or more engineer ids were not found");
        }

        for (Engineer engineer : engineers) {
            site.addEngineer(engineer);
        }

        return siteRepository.save(site);
    }

    public List<Site> getSortedSites(String sortBy, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.ASC);
        return siteRepository.findAll(Sort.by(sortDirection, sortBy));
    }

    private void applyRequest(Site site, CreateSiteRequest request) {
        site.setSiteCode(request.siteCode());
        site.setSiteName(request.siteName());

        Location location = site.getLocation();
        if (location == null) {
            location = new Location();
            site.setLocation(location);
        }

        applyLocation(location, request.location());
    }

    private void applyLocation(Location location, CreateLocationRequest request) {
        location.setProvinceCode(request.provinceCode());
        location.setProvince(request.province());
        location.setDistrict(request.district());
        location.setSector(request.sector());
        location.setCell(request.cell());
        location.setVillage(request.village());
    }
}
