package com.project.messageapp.controllers;

import com.project.messageapp.dtos.AuthenticationDTO;
import com.project.messageapp.dtos.RegistrationDTO;
import com.project.messageapp.responses.UniversalResponse;
import com.project.messageapp.services.AuthenticationService;
import com.project.messageapp.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/msgApp/")
@AllArgsConstructor
public class UsersController {
    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UniversalResponse> register(@RequestBody @Valid RegistrationDTO request)
    {
        try{
            return ResponseEntity.ok(registrationService.register(request));
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<UniversalResponse> login(@RequestBody @Valid AuthenticationDTO request)
    {
        try{
            return ResponseEntity.ok(authenticationService.login(request));
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}