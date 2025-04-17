package com.jwelkam.jewelrymanagement.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwelkam.jewelrymanagement.dto.request.AdminRequest;
import com.jwelkam.jewelrymanagement.dto.response.AdminResponse;
import com.jwelkam.jewelrymanagement.service.AdminService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private AdminService adminService;

    public AdminController(@Autowired AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/create")
    public ResponseEntity<AdminResponse> createAdmin(@RequestBody AdminRequest adminRequest) throws BadRequestException {
        return new ResponseEntity<>(adminService.createAdmin(adminRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getAdminById(@PathVariable Long id) {
        return new ResponseEntity<>(adminService.getAdminById(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AdminResponse> getAdminByEmail(@PathVariable String email) {
        return new ResponseEntity<>(adminService.getAdminByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/getAllAdmins")
    public ResponseEntity<List<AdminResponse>> getAllAdmins() {
        return new ResponseEntity<>(adminService.getAllAdmin(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdminResponse> updateAdmin(@PathVariable Long id, AdminRequest adminRequest) {
        return new ResponseEntity<>(adminService.updateAdmin(id, adminRequest), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
