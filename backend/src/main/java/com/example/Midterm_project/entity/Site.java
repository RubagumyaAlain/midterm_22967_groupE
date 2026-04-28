package com.example.Midterm_project.entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sites")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String siteCode;

    @Column(nullable = false)
    private String siteName;

    @OneToOne(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"site"})
    private Location location;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Labor> labors = new ArrayList<>();

    // The join table stores site_id + engineer_id pairs and is the many-to-many bridge.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "site_engineer_assignments",
            joinColumns = @JoinColumn(name = "site_id"),
            inverseJoinColumns = @JoinColumn(name = "engineer_id"))
    @JsonIgnoreProperties({"sites"})
    private Set<Engineer> engineers = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Location getLocation() {
        return location;
    }

    public List<Labor> getLabors() {
        return labors;
    }

    public Set<Engineer> getEngineers() {
        return engineers;
    }

    // Keeping both sides in sync prevents a broken one-to-one foreign key.
    public void setLocation(Location location) {
        this.location = location;
        if (location != null) {
            location.setSite(this);
        }
    }

    public void addEngineer(Engineer engineer) {
        engineers.add(engineer);
        engineer.getSites().add(this);
    }
}
