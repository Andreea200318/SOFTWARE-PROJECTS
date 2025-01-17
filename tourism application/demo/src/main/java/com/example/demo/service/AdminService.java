package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.model.DTO.AdminLoginDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface AdminService {
    public Admin create(Admin admin);

    public List<Admin> getAllAdmins();

    public Admin getAdminById(Integer idAdmin);

    public Admin update(Admin admin);

    public String deleteAdmin(Integer idAdmin);

    public Boolean validateAdmin(AdminLoginDTO adminLoginDTO);
}
