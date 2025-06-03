package com.example.client_service.controller;

import com.example.client_service.entity.ClientEntity;
import com.example.client_service.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/client")
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
    public ResponseEntity<ClientEntity> findClientById(@PathVariable("id") Long id){
        ClientEntity client = clientService.findClientById(id);

        if(client == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientEntity> findClientByEmail(@PathVariable("email") String email){
        ClientEntity client = clientService.findClientByEmail(email);

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

    @PutMapping("/")
    public ResponseEntity<ClientEntity> updateClient(@RequestBody ClientEntity client){
        ClientEntity updatedClient = clientService.updateClient(client);

        if(updatedClient == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedClient);
    }
}
