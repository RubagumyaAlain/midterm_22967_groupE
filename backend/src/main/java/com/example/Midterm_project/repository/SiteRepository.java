package com.example.Midterm_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Midterm_project.entity.Site;

public interface SiteRepository extends JpaRepository<Site, Long> {

    boolean existsBySiteCodeIgnoreCase(String siteCode);
}
