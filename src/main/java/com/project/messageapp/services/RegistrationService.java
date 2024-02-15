package com.project.messageapp.services;

import com.project.messageapp.dtos.RegistrationDTO;
import com.project.messageapp.models.Users;
import com.project.messageapp.repositories.UsersRepository;
import com.project.messageapp.responses.UniversalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RegistrationService {
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public RegistrationService(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UniversalResponse register(RegistrationDTO request) {
        try {
            List<Users> existingCustomers = userRepository.findAllByAccountNumberAndPhoneNumber(request.getAccountNumber(), request.getPhoneNumber());
            if (existingCustomers.isEmpty()) {
                Users user = Users.builder()
                        .accountNumber(request.getAccountNumber())
                        .phoneNumber(request.getPhoneNumber())
                        .fullName(request.getFullName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .build();
                userRepository.save(user);

                return UniversalResponse.builder()
                        .message("User registered successfully")
                        .status("0")
                        .build();

            }
            else {
                return UniversalResponse.builder()
                        .message("User already registered, please login")
                        .status("1")
                        .build();
            }
        }
        catch (Exception ex) {
            System.out.println("User Registration Failed{}" + ex);

            return UniversalResponse.builder()
                    .message("User Registration Failed{}"+ex)
                    .status("1")
                    .build();
        }

    }

    public void saveListCustomers(List<Users> customersDTOList){
       userRepository.saveAll(customersDTOList);
    }

}