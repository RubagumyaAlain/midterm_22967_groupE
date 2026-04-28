package com.example.Midterm_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Midterm_project.dto.ProvinceUsersResponse;
import com.example.Midterm_project.service.UserLookupService;

@RestController
@RequestMapping("/api/users")
public class UserLookupController {

    private final UserLookupService userLookupService;

    public UserLookupController(UserLookupService userLookupService) {
        this.userLookupService = userLookupService;
    }

    @GetMapping("/by-province")
    public ProvinceUsersResponse getUsersByProvince(
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) String provinceName) {
        return userLookupService.findUsersByProvince(provinceCode, provinceName);
    }
}
