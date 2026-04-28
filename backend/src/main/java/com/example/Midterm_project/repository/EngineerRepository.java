package com.example.Midterm_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Midterm_project.entity.Engineer;

public interface EngineerRepository extends JpaRepository<Engineer, Long> {

    boolean existsByEngineerCodeIgnoreCase(String engineerCode);

    List<Engineer> findByProvinceCodeIgnoreCase(String provinceCode);

    List<Engineer> findByProvinceIgnoreCase(String province);
}
