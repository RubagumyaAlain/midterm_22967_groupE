package com.example.Midterm_project.dto;

import java.util.List;

import com.example.Midterm_project.entity.Engineer;
import com.example.Midterm_project.entity.Labor;
import com.example.Midterm_project.entity.StaffAdministration;

public record ProvinceUsersResponse(
        String lookupField,
        String lookupValue,
        List<StaffAdministration> administrators,
        List<Engineer> engineers,
        List<Labor> labors) {
}
