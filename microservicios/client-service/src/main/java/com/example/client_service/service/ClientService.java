package com.example.client_service.service;

import com.example.client_service.entity.ClientEntity;
import com.example.client_service.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

}
