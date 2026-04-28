package com.example.Midterm_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.Midterm_project.entity.Labor;

public interface LaborRepository extends JpaRepository<Labor, Long>, JpaSpecificationExecutor<Labor> {

    boolean existsByEmployeeCodeIgnoreCase(String employeeCode);

    List<Labor> findByProvinceCodeIgnoreCase(String provinceCode);

    List<Labor> findByProvinceIgnoreCase(String province);
}
