package com.example.Midterm_project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Midterm_project.dto.ProvinceUsersResponse;
import com.example.Midterm_project.entity.Engineer;
import com.example.Midterm_project.entity.Labor;
import com.example.Midterm_project.entity.StaffAdministration;
import com.example.Midterm_project.exception.BadRequestException;
import com.example.Midterm_project.repository.EngineerRepository;
import com.example.Midterm_project.repository.LaborRepository;
import com.example.Midterm_project.repository.StaffAdministrationRepository;

@Service
public class UserLookupService {

    private final StaffAdministrationRepository staffAdministrationRepository;
    private final EngineerRepository engineerRepository;
    private final LaborRepository laborRepository;

    public UserLookupService(
            StaffAdministrationRepository staffAdministrationRepository,
            EngineerRepository engineerRepository,
            LaborRepository laborRepository) {
        this.staffAdministrationRepository = staffAdministrationRepository;
        this.engineerRepository = engineerRepository;
        this.laborRepository = laborRepository;
    }

    public ProvinceUsersResponse findUsersByProvince(String provinceCode, String provinceName) {
        String normalizedProvinceCode = normalize(provinceCode);
        String normalizedProvinceName = normalize(provinceName);

        if (normalizedProvinceCode == null && normalizedProvinceName == null) {
            throw new BadRequestException("Provide either provinceCode or provinceName");
        }

        if (normalizedProvinceCode != null) {
            List<StaffAdministration> administrators =
                    staffAdministrationRepository.findByProvinceCodeIgnoreCase(normalizedProvinceCode);
            List<Engineer> engineers = engineerRepository.findByProvinceCodeIgnoreCase(normalizedProvinceCode);
            List<Labor> labors = laborRepository.findByProvinceCodeIgnoreCase(normalizedProvinceCode);

            return new ProvinceUsersResponse(
                    "provinceCode",
                    normalizedProvinceCode,
                    administrators,
                    engineers,
                    labors);
        }

        List<StaffAdministration> administrators =
                staffAdministrationRepository.findByProvinceIgnoreCase(normalizedProvinceName);
        List<Engineer> engineers = engineerRepository.findByProvinceIgnoreCase(normalizedProvinceName);
        List<Labor> labors = laborRepository.findByProvinceIgnoreCase(normalizedProvinceName);

        return new ProvinceUsersResponse(
                "provinceName",
                normalizedProvinceName,
                administrators,
                engineers,
                labors);
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }

        String trimmedValue = value.trim();
        return trimmedValue.isEmpty() ? null : trimmedValue;
    }
}
