package com.example.Midterm_project.service;

import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.Midterm_project.dto.CreateLaborRequest;
import com.example.Midterm_project.dto.CreateLocationRequest;
import com.example.Midterm_project.entity.Category;
import com.example.Midterm_project.entity.Labor;
import com.example.Midterm_project.entity.Site;
import com.example.Midterm_project.entity.StaffAdministration;
import com.example.Midterm_project.exception.BadRequestException;
import com.example.Midterm_project.exception.DuplicateResourceException;
import com.example.Midterm_project.exception.ResourceNotFoundException;
import com.example.Midterm_project.repository.CategoryRepository;
import com.example.Midterm_project.repository.LaborRepository;
import com.example.Midterm_project.repository.SiteRepository;
import com.example.Midterm_project.repository.StaffAdministrationRepository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

@Service
public class LaborService {

    private final LaborRepository laborRepository;
    private final StaffAdministrationRepository staffAdministrationRepository;
    private final CategoryRepository categoryRepository;
    private final SiteRepository siteRepository;

    public LaborService(
            LaborRepository laborRepository,
            StaffAdministrationRepository staffAdministrationRepository,
            CategoryRepository categoryRepository,
            SiteRepository siteRepository) {
        this.laborRepository = laborRepository;
        this.staffAdministrationRepository = staffAdministrationRepository;
        this.categoryRepository = categoryRepository;
        this.siteRepository = siteRepository;
    }

    public Labor create(CreateLaborRequest request) {
        if (laborRepository.existsByEmployeeCodeIgnoreCase(request.employeeCode())) {
            throw new DuplicateResourceException("Employee code already exists: " + request.employeeCode());
        }

        Labor labor = new Labor();
        applyRequest(labor, request);

        return laborRepository.save(labor);
    }

    public Labor getById(Long laborId) {
        return laborRepository.findById(laborId)
                .orElseThrow(() -> new ResourceNotFoundException("Labor not found with id: " + laborId));
    }

    public Labor update(Long laborId, CreateLaborRequest request) {
        Labor labor = getById(laborId);

        if (!labor.getEmployeeCode().equalsIgnoreCase(request.employeeCode())
                && laborRepository.existsByEmployeeCodeIgnoreCase(request.employeeCode())) {
            throw new DuplicateResourceException("Employee code already exists: " + request.employeeCode());
        }

        applyRequest(labor, request);
        return laborRepository.save(labor);
    }

    public Page<Labor> getPaginatedLabors(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.ASC);

        // PageRequest combines page number, page size, and sorting into one database-friendly query.
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return laborRepository.findAll(pageRequest);
    }

    public List<Labor> findByLocation(
            String provinceCode,
            String province,
            String district,
            String sector,
            String cell,
            String village) {
        String normalizedProvinceCode = normalizeLocationPart(provinceCode);
        String normalizedProvince = normalizeLocationPart(province);
        String normalizedDistrict = normalizeLocationPart(district);
        String normalizedSector = normalizeLocationPart(sector);
        String normalizedCell = normalizeLocationPart(cell);
        String normalizedVillage = normalizeLocationPart(village);

        if (normalizedProvinceCode == null
                && normalizedProvince == null
                && normalizedDistrict == null
                && normalizedSector == null
                && normalizedCell == null
                && normalizedVillage == null) {
            throw new BadRequestException(
                    "Provide at least one location value: provinceCode, province, district, sector, cell, or village");
        }

        return laborRepository.findAll(bySiteLocation(
                normalizedProvinceCode,
                normalizedProvince,
                normalizedDistrict,
                normalizedSector,
                normalizedCell,
                normalizedVillage));
    }

    private String normalizeLocationPart(String value) {
        if (value == null) {
            return null;
        }

        String trimmedValue = value.trim();
        return trimmedValue.isEmpty() ? null : trimmedValue;
    }

    private void applyRequest(Labor labor, CreateLaborRequest request) {
        StaffAdministration administrator = staffAdministrationRepository.findById(request.administratorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Administrator not found with id: " + request.administratorId()));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.categoryId()));

        Site site = siteRepository.findById(request.siteId())
                .orElseThrow(() -> new ResourceNotFoundException("Site not found with id: " + request.siteId()));

        labor.setEmployeeCode(request.employeeCode());
        labor.setFullName(request.fullName());
        labor.setPhoneNumber(request.phoneNumber());
        labor.setEfficiencyScore(request.efficiencyScore());
        applyLocation(labor, request.location());
        labor.setAdministrator(administrator);
        labor.setCategory(category);
        labor.setSite(site);
    }

    private void applyLocation(Labor labor, CreateLocationRequest location) {
        labor.setProvinceCode(location.provinceCode());
        labor.setProvince(location.province());
        labor.setDistrict(location.district());
        labor.setSector(location.sector());
        labor.setCell(location.cell());
        labor.setVillage(location.village());
    }

    private Specification<Labor> bySiteLocation(
            String provinceCode,
            String province,
            String district,
            String sector,
            String cell,
            String village) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            Join<Object, Object> siteJoin = root.join("site", JoinType.INNER);
            Join<Object, Object> locationJoin = siteJoin.join("location", JoinType.INNER);

            List<jakarta.persistence.criteria.Predicate> predicates = new java.util.ArrayList<>();

            addLocationPredicate(predicates, criteriaBuilder, locationJoin, "provinceCode", provinceCode);
            addLocationPredicate(predicates, criteriaBuilder, locationJoin, "province", province);
            addLocationPredicate(predicates, criteriaBuilder, locationJoin, "district", district);
            addLocationPredicate(predicates, criteriaBuilder, locationJoin, "sector", sector);
            addLocationPredicate(predicates, criteriaBuilder, locationJoin, "cell", cell);
            addLocationPredicate(predicates, criteriaBuilder, locationJoin, "village", village);

            return criteriaBuilder.and(predicates.toArray(jakarta.persistence.criteria.Predicate[]::new));
        };
    }

    private void addLocationPredicate(
            List<jakarta.persistence.criteria.Predicate> predicates,
            jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder,
            Join<Object, Object> locationJoin,
            String fieldName,
            String value) {
        if (value != null) {
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(locationJoin.get(fieldName)),
                    value.toLowerCase(Locale.ROOT)));
        }
    }
}
