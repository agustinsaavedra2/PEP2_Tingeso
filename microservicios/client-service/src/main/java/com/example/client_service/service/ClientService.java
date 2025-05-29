package com.example.client_service.service;

import com.example.client_service.entity.ClientEntity;
import com.example.client_service.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestTemplate restTemplate;

    public ClientEntity createClient(ClientEntity client){
        return clientRepository.save(client);
    }

    public ClientEntity findClientById(Long id){
        return clientRepository.findById(id).orElse(null);
    }

    public ClientEntity findClientByEmail(String email){
        return clientRepository.findByEmail(email).orElse(null);
    }

    public List<ClientEntity> getAllClients(){
        return clientRepository.findAll();
    }

    public ClientEntity updateClient(ClientEntity client){
        return clientRepository.save(client);
    }

    public void deleteClientById(Long id) throws Exception{
        try{
            clientRepository.deleteById(id);
        }
        catch(Exception e){
            throw new Exception("Error to delete client with id: " + id);
        }
    }
}