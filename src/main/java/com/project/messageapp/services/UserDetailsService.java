package com.project.messageapp.services;

import com.project.messageapp.repositories.CustomersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final CustomersRepository customersRepository;
    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        return customersRepository.findCustomersByAccountNumber(accountNumber);
    }
}
