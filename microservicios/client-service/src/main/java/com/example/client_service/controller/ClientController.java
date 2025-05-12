package com.example.client_service.controller;

import com.example.client_service.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequestMapping("api/client")
@AllArgsConstructor
public class ClientController {
    @Autowired
    private ClientService clientService;
}
