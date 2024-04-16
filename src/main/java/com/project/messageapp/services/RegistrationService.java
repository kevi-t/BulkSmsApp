package com.project.messageapp.services;

import com.project.messageapp.dtos.RegistrationDTO;
import com.project.messageapp.enums.UserRole;
import com.project.messageapp.models.SmsUsers;
import com.project.messageapp.repositories.SmsUsersRepository;
import com.project.messageapp.responses.UniversalResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {
    private final SmsUsersRepository smsUsersRepository;
    private final PasswordEncoder passwordEncoder;

    public UniversalResponse register(RegistrationDTO request) {
        try{
            if (smsUsersRepository.findSmsUsersByUserEmail(request.getEmail()) != null) {
                String email = request.getEmail();
                return UniversalResponse.builder().message("User with email"+" "+email+ " "+ "exists login").status("0").build();
            }
            else {
                var user = SmsUsers.builder().userName(request.getUsername()).userEmail(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).userRole(UserRole.ADMIN).enabled(true).locked(false).build();
                smsUsersRepository.save(user);
                return UniversalResponse.builder().message("Registration successful").status("0").build();
            }
        }
        catch (Exception e){
            return  UniversalResponse.builder().message("Registration failed").status("0").build();
        }
    }
}