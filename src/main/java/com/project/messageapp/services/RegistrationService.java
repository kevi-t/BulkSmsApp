package com.project.messageapp.services;

import com.project.messageapp.dtos.RegistrationDTO;
import com.project.messageapp.models.BulkSmsUsers;
import com.project.messageapp.repositories.BulkSmsUsersRepository;
import com.project.messageapp.repositories.KplcSmsBatchRepository;
import com.project.messageapp.responses.UniversalResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {
    private  final BulkSmsUsersRepository bulkSmsUsersRepository;
    private final PasswordEncoder passwordEncoder;
    private final KplcSmsBatchRepository kplcSmsBatchRepository;

    public UniversalResponse register(RegistrationDTO request) {
        try{
            if (bulkSmsUsersRepository.findBulkSmsUsersByEmail(request.getEmail()) != null) {
                String email = request.getEmail();
                System.out.println(kplcSmsBatchRepository.findAll());
                return UniversalResponse.builder().message("User with email"+" "+email+ " "+ "exists login").status("0").build();
            }
            else {
                var user = BulkSmsUsers.builder().
                        staffNo(request.getStaffNo()).
                        firstName(request.getFirstName()).
                        lastName(request.getLastName()).
                        email(request.getEmail()).
                        password(passwordEncoder.encode(request.getPassword())).
                        build();
               bulkSmsUsersRepository.save(user);
               return UniversalResponse.builder().message("Registration successful").status("0").build();
            }
        }
        catch (Exception e){
            return  UniversalResponse.builder().message("Registration failed").status("0").build();
        }
    }
}