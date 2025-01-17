package com.example.demo.repository;

import com.example.demo.model.Admin;
import com.example.demo.model.DTO.AdminLoginDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsernameAndPassword(String username, String password);
}
