package com.example.Midterm_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Midterm_project.entity.StaffAdministration;

public interface StaffAdministrationRepository extends JpaRepository<StaffAdministration, Long> {

    boolean existsByAdminCodeIgnoreCase(String adminCode);

    boolean existsByEmailIgnoreCase(String email);

    List<StaffAdministration> findByProvinceCodeIgnoreCase(String provinceCode);

    List<StaffAdministration> findByProvinceIgnoreCase(String province);
}
