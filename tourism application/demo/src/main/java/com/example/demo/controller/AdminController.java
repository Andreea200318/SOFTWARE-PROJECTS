package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.model.DTO.AdminLoginDTO;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("")
    public List<Admin> getAdmins()
    {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{idAmin}")
    public Admin getAdmin(@PathVariable Integer idAmin)
    {
        return adminService.getAdminById(idAmin);
    }

    @PostMapping("/store")
    public Admin saveAdmin(@RequestBody Admin admin)
    {
        return adminService.create(admin);
    }

    @PutMapping("/update")
    public Admin updateAdmin(@RequestBody Admin admin)
    {
        return adminService.update(admin);
    }

    @DeleteMapping("/delete/{idAdmin}")
    public String deleteAdmin(@PathVariable Integer idAdmin)
    {
        return adminService.deleteAdmin(idAdmin);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginDTO adminLoginDTO) {
        // Extrage datele din JSON
        //String username = adminLoginDTO.getUsername();
        //String password = adminLoginDTO.getPassword(); // Asigură-te că frontend-ul trimite "parola"

        // Validare
        boolean isValid = adminService.validateAdmin(adminLoginDTO);

        if (isValid) {
            return ResponseEntity.ok("Login reușit!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Username sau parolă incorecte");
        }
    }
}
