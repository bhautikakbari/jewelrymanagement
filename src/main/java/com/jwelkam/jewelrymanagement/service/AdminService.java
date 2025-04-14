package com.jwelkam.jewelrymanagement.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.jwelkam.jewelrymanagement.dto.request.AdminRequest;
import com.jwelkam.jewelrymanagement.dto.response.AdminResponse;

public interface AdminService {
    
    AdminResponse createAdmin(AdminRequest adminRequest) throws BadRequestException;

    AdminResponse getAdminById(Long id);

    AdminResponse getAdminByEmail(String email);

    List<AdminResponse> getAllAdmin();

    AdminResponse updateAdmin(Long id, AdminRequest adminRequest);

    void deleteAdmin(Long id);
}
