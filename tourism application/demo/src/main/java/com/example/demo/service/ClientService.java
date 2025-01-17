package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.DTO.AdminLoginDTO;
import com.example.demo.model.DTO.ClientLoginDTO;
import com.example.demo.model.DTO.ClientRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    Client create(Client client);

    List<Client> getAllClients();

    Client getClientById(Integer idClient);

    Client update(Client client);

    String deleteClient(Integer idClient);

    Client registerClient(ClientRegisterDTO clientRegisterDTO);

    Client getClientByEmail(String email); // Adaugat pentru verificare email

    public Boolean validateClient(ClientLoginDTO clientLoginDTO);
}
