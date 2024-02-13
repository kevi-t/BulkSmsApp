package com.project.messageapp.services;

import com.project.messageapp.dtos.CustomersDTO;
import com.project.messageapp.models.Customers;
import com.project.messageapp.repositories.CustomersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomersService {

    private final CustomersRepository customerRepository;

    @Autowired
    public CustomersService(CustomersRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(CustomersDTO customersDTO) {

        List<Customers> existingCustomers = customerRepository.findAllByAccountNumberAndPhoneNumber(customersDTO.getAccountNumber(), customersDTO.getPhoneNumber());
        if (existingCustomers.isEmpty()) {
            Customers user = Customers.builder()
                    .accountNumber(customersDTO.getAccountNumber())
                    .phoneNumber(customersDTO.getPhoneNumber())
                    .fullName(customersDTO.getFullName())
                    .build();

            customerRepository.save(user);
            System.out.println("Success");
        }
        else {
            System.out.println("User Exists");
        }
    }
    public void saveListCustomers(List<Customers> customersDTOList){
        customerRepository.saveAll(customersDTOList);
    }

}