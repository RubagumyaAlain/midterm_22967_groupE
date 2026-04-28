package com.example.Midterm_project.service;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.example.Midterm_project.dto.CreateEngineerRequest;
import com.example.Midterm_project.dto.CreateLocationRequest;
import com.example.Midterm_project.entity.Engineer;
import com.example.Midterm_project.entity.EngineerSpecialty;
import com.example.Midterm_project.exception.BadRequestException;
import com.example.Midterm_project.exception.DuplicateResourceException;
import com.example.Midterm_project.exception.ResourceNotFoundException;
import com.example.Midterm_project.repository.EngineerRepository;

@Service
public class EngineerService {

    private final EngineerRepository engineerRepository;

    public EngineerService(EngineerRepository engineerRepository) {
        this.engineerRepository = engineerRepository;
    }

    public Engineer create(CreateEngineerRequest request) {
        if (engineerRepository.existsByEngineerCodeIgnoreCase(request.engineerCode())) {
            throw new DuplicateResourceException("Engineer code already exists: " + request.engineerCode());
        }

        Engineer engineer = new Engineer();
        applyRequest(engineer, request);

        return engineerRepository.save(engineer);
    }

    public Engineer getById(Long engineerId) {
        return engineerRepository.findById(engineerId)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer not found with id: " + engineerId));
    }

    public Engineer update(Long engineerId, CreateEngineerRequest request) {
        Engineer engineer = getById(engineerId);

        if (!engineer.getEngineerCode().equalsIgnoreCase(request.engineerCode())
                && engineerRepository.existsByEngineerCodeIgnoreCase(request.engineerCode())) {
            throw new DuplicateResourceException("Engineer code already exists: " + request.engineerCode());
        }

        applyRequest(engineer, request);
        return engineerRepository.save(engineer);
    }

    private EngineerSpecialty parseSpecialty(String specialty) {
        try {
            return EngineerSpecialty.valueOf(specialty.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException(
                    "Invalid specialty. Use one of: STRUCTURAL, ELECTRICAL, CIVIL, ARCHITECT, SUPERVISOR");
        }
    }

    private void applyRequest(Engineer engineer, CreateEngineerRequest request) {
        engineer.setEngineerCode(request.engineerCode());
        engineer.setFullName(request.fullName());
        engineer.setSpecialty(parseSpecialty(request.specialty()));
        applyLocation(engineer, request.location());
    }

    private void applyLocation(Engineer engineer, CreateLocationRequest location) {
        engineer.setProvinceCode(location.provinceCode());
        engineer.setProvince(location.province());
        engineer.setDistrict(location.district());
        engineer.setSector(location.sector());
        engineer.setCell(location.cell());
        engineer.setVillage(location.village());
    }
}
