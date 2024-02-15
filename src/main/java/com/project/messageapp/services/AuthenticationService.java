package com.project.messageapp.services;

import com.project.messageapp.dtos.AuthenticationDTO;
import com.project.messageapp.jwt.JwtTokenService;
import com.project.messageapp.models.Users;
import com.project.messageapp.repositories.UsersRepository;
import com.project.messageapp.responses.UniversalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final UsersRepository userRepository;
    private final UniversalResponse response;

    public AuthenticationService( JwtTokenService jwtTokenService,
                                  AuthenticationManager authenticationManager,
                                  UsersRepository userRepository,
                                  UniversalResponse response) {
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.response = response;
    }

    public UniversalResponse login(AuthenticationDTO request) {
        try {
            Users user = userRepository.findUsersByAccountNumber(request.getAccountNumber());
            if (user == null) {
                log.info("User not found please register");

                return  UniversalResponse.builder()
                        .message("User not found please register")
                        .status("1")
                        .build();
            }
            else {
                try {
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getAccountNumber(), request.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    var jwtToken = jwtTokenService.generateToken((Users) authentication.getPrincipal());

                    return UniversalResponse.builder()
                            .message("Login successful")
                            .status("0")
                            .data(jwtToken)
                            .build();
                }
                catch (AuthenticationException ex) {
                    return UniversalResponse.builder()
                            .message("Incorrect account number or password")
                            .status("1")
                            .build();
                }
                catch (Exception ex) {
                    // Log the exception using a logging framework
                    return UniversalResponse.builder()
                            .message("Failed to generate token: " + ex.getMessage())
                            .status("1")
                            .build();
                }
            }

        }
        catch (Exception ex){
            log.info("Error while finding user"+ex);
            ex.printStackTrace();

        }
        return response;
    }
}