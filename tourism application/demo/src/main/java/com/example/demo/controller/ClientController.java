package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.DTO.AdminLoginDTO;
import com.example.demo.model.DTO.ClientLoginDTO;
import com.example.demo.model.DTO.ClientRegisterDTO;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173") // Permite accesul din frontend
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{idClient}")
    public Client getClient(@PathVariable Integer idClient) {
        return clientService.getClientById(idClient);
    }

    @PostMapping("/store")
    public Client saveClient(@RequestBody Client client) {
        return clientService.create(client);
    }

    @PutMapping("/update")
    public Client updateClient(@RequestBody Client client) {
        return clientService.update(client);
    }

    @DeleteMapping("/delete/{idClient}")
    public String deleteClient(@PathVariable Integer idClient) {
        return clientService.deleteClient(idClient);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody ClientRegisterDTO clientRegisterDTO) {
        Client existingClient = clientService.getClientByEmail(clientRegisterDTO.getEmail());
        if (existingClient != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        Client newClient = clientService.registerClient(clientRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody ClientLoginDTO clientLoginDTO) {
        // Extrage datele din JSON
        //String username = adminLoginDTO.getUsername();
        //String password = adminLoginDTO.getPassword(); // Asigură-te că frontend-ul trimite "parola"

        // Validare
        boolean isValid = clientService.validateClient(clientLoginDTO);
        Client client = clientService.getClientByEmail(clientLoginDTO.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("email", clientLoginDTO.getEmail());
        response.put("client_id",String.valueOf(client.getIdClient()));


        if (isValid) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Username sau parolă incorecte");
        }
    }
}
