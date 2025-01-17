package com.example.demo.service.impl;

import com.example.demo.model.Client;
import com.example.demo.model.DTO.ClientLoginDTO;
import com.example.demo.model.DTO.ClientRegisterDTO;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client create(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Integer idClient) {
        return clientRepository.findById(idClient).orElse(null);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public String deleteClient(Integer idClient) {
        clientRepository.deleteById(idClient);
        return "Client deleted successfully!";
    }

    @Override
    public Client registerClient(ClientRegisterDTO clientRegisterDTO) {
        Client client = new Client();
        client.setEmail(clientRegisterDTO.getEmail());
        client.setPassword(clientRegisterDTO.getPassword());

        return clientRepository.save(client);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Boolean validateClient(ClientLoginDTO clientLoginDTO) {
        Client client = clientRepository.findByEmailAndPassword(clientLoginDTO.getEmail(), clientLoginDTO.getPassword());
        return client != null;
    }
}
