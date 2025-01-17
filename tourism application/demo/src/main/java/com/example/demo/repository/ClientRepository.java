package com.example.demo.repository;

import com.example.demo.model.Admin;
import com.example.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByEmail(String email); // Adăugat pentru verificare
    Client findByEmailAndPassword(String email, String password);
}
