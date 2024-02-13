package com.project.messageapp.controllers;

import com.project.messageapp.dtos.CustomersDTO;
import com.project.messageapp.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/msgApp/")
public class CustomersController {

    private final CustomersService customersService;

    @Autowired
    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostMapping("/register/customer")
    public void saveCustomer(@RequestBody CustomersDTO customersDTO) {
        customersService.saveCustomer(customersDTO);
    }

}