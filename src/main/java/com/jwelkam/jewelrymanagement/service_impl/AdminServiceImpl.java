package com.jwelkam.jewelrymanagement.service_impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import java.lang.reflect.Type;
import org.modelmapper.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwelkam.jewelrymanagement.dto.request.AdminRequest;
import com.jwelkam.jewelrymanagement.dto.response.AdminResponse;
import com.jwelkam.jewelrymanagement.entity.Admin;
import com.jwelkam.jewelrymanagement.exception.BadRequestException;
import com.jwelkam.jewelrymanagement.exception.ResourceNotFoundException;
import com.jwelkam.jewelrymanagement.repository.AdminRepository;
import com.jwelkam.jewelrymanagement.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    //@Autowired
    private AdminRepository adminRepository;

    //@Autowired
    private ModelMapper modelMapper;

    public AdminServiceImpl(@Autowired AdminRepository adminRepository,@Autowired ModelMapper modelMapper){
        this.adminRepository=adminRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public AdminResponse createAdmin(AdminRequest adminRequest) {

        if (adminRepository.findByEmail(adminRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Admin with this email already exists");
        }

        Admin admin = Admin.builder().name(adminRequest.getName())
                .email(adminRequest.getEmail())
                .password(adminRequest.getPassword())
                .mobileNumber(adminRequest.getMobileNumber())
                .build();

        Admin savedAdmin = adminRepository.save(admin);

        return modelMapper.map(savedAdmin, AdminResponse.class);
    }

    @Override
    public AdminResponse getAdminById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
        return modelMapper.map(admin, AdminResponse.class);
    }

    @Override
    public AdminResponse getAdminByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", "email", email));
        return modelMapper.map(admin, AdminResponse.class);
    }

    @Override
    public List<AdminResponse> getAllAdmin() {
        List<Admin> admin = adminRepository.findAll();
        Type listType = new TypeToken<List<AdminResponse>>() {
        }.getType();
        return modelMapper.map(admin, listType);
    }

    @Override
    public AdminResponse updateAdmin(Long id, AdminRequest adminRequest) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));

        if (adminRequest.getEmail() == null || adminRequest.getEmail().isEmpty()) {
            throw new BadRequestException("Email cannot be null or empty");
        }
    
        if (!admin.getEmail().equals(adminRequest.getEmail()) &&
                adminRepository.findByEmail(adminRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Admin with this email already exists");
        }
        Admin updatedAdmin = Admin.builder().name(adminRequest.getName())
                .email(adminRequest.getEmail())
                .password(adminRequest.getPassword())
                .mobileNumber(adminRequest.getMobileNumber())
                .build();

        Admin savedAdmin = adminRepository.save(updatedAdmin);

        return modelMapper.map(savedAdmin, AdminResponse.class);
    }

    @Override
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
        adminRepository.delete(admin);
    }
}
