package com.example.client_service.controller;

import com.example.client_service.entity.ClientEntity;
import com.example.client_service.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("api/client")
@AllArgsConstructor
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/")
    public ResponseEntity<ClientEntity> createClient(@RequestBody ClientEntity client){
        ClientEntity newClient = clientService.createClient(client);

        if(newClient == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(newClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> findClientById(@PathVariable Long id){
        ClientEntity client = clientService.findClientById(id);

        if(client == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @GetMapping("/")
    public ResponseEntity<List<ClientEntity>> getAllClients(){
        List<ClientEntity> clients = clientService.getAllClients();

        return ResponseEntity.ok(clients);
    }
}
