package com.example.backend.admin.controller;

import com.example.backend.admin.service.AdminService;
import com.example.common.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String login(@RequestBody UserDTO reqDTO){
        boolean res = this.adminService.login(reqDTO);
        return "loginView";
    }

    @PostMapping("/login-proc")
    public String loginProc(@RequestBody UserDTO reqDTO){
        return "loginView";
    }
}
