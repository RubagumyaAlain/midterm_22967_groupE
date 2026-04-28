package com.example.Midterm_project.service;

import org.springframework.stereotype.Service;

import com.example.Midterm_project.dto.CreateAdministrationRequest;
import com.example.Midterm_project.dto.CreateLocationRequest;
import com.example.Midterm_project.entity.StaffAdministration;
import com.example.Midterm_project.exception.DuplicateResourceException;
import com.example.Midterm_project.exception.ResourceNotFoundException;
import com.example.Midterm_project.repository.StaffAdministrationRepository;

@Service
public class StaffAdministrationService {

    private final StaffAdministrationRepository staffAdministrationRepository;

    public StaffAdministrationService(StaffAdministrationRepository staffAdministrationRepository) {
        this.staffAdministrationRepository = staffAdministrationRepository;
    }

    public StaffAdministration create(CreateAdministrationRequest request) {
        if (staffAdministrationRepository.existsByAdminCodeIgnoreCase(request.adminCode())) {
            throw new DuplicateResourceException("Administrator code already exists: " + request.adminCode());
        }

        if (staffAdministrationRepository.existsByEmailIgnoreCase(request.email())) {
            throw new DuplicateResourceException("Administrator email already exists: " + request.email());
        }

        StaffAdministration administrator = new StaffAdministration();
        applyRequest(administrator, request);

        return staffAdministrationRepository.save(administrator);
    }

    public StaffAdministration getById(Long administratorId) {
        return staffAdministrationRepository.findById(administratorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Administrator not found with id: " + administratorId));
    }

    public StaffAdministration update(Long administratorId, CreateAdministrationRequest request) {
        StaffAdministration administrator = getById(administratorId);

        if (!administrator.getAdminCode().equalsIgnoreCase(request.adminCode())
                && staffAdministrationRepository.existsByAdminCodeIgnoreCase(request.adminCode())) {
            throw new DuplicateResourceException("Administrator code already exists: " + request.adminCode());
        }

        if (!administrator.getEmail().equalsIgnoreCase(request.email())
                && staffAdministrationRepository.existsByEmailIgnoreCase(request.email())) {
            throw new DuplicateResourceException("Administrator email already exists: " + request.email());
        }

        applyRequest(administrator, request);
        return staffAdministrationRepository.save(administrator);
    }

    private void applyRequest(StaffAdministration administrator, CreateAdministrationRequest request) {
        administrator.setAdminCode(request.adminCode());
        administrator.setFullName(request.fullName());
        administrator.setEmail(request.email());
        applyLocation(administrator, request.location());
    }

    private void applyLocation(StaffAdministration administrator, CreateLocationRequest location) {
        administrator.setProvinceCode(location.provinceCode());
        administrator.setProvince(location.province());
        administrator.setDistrict(location.district());
        administrator.setSector(location.sector());
        administrator.setCell(location.cell());
        administrator.setVillage(location.village());
    }
}
