package com.project.messageapp.services;

import com.project.messageapp.dtos.AuthenticationDTO;
import com.project.messageapp.jwt.JwtTokenUtil;
import com.project.messageapp.models.BulkSmsUsers;
import com.project.messageapp.repositories.BulkSmsUsersRepository;
import com.project.messageapp.responses.UniversalResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final BulkSmsUsersRepository bulkSmsUsersRepository;

    public UniversalResponse login(AuthenticationDTO request) {
        try {
            BulkSmsUsers user = bulkSmsUsersRepository.findBulkSmsUsersByStaffNo(request.getStaffNo());
            if (user == null) {
                return UniversalResponse.builder().message("User not found please register").status("0").build();
            }
            else {
                try {
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getStaffNo(), request.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    var jwtToken = jwtTokenUtil.generateToken((BulkSmsUsers) authentication.getPrincipal());
                    return UniversalResponse.builder().message("login successful").status("0").data(jwtToken).build();
                }
                catch (AuthenticationException e) {
                    log.error("Authentication failed: " + e.getMessage());
                    return UniversalResponse.builder().message("User or password incorrect").status("0").build();
                }
            }
        }
        catch (DataAccessException e) {
            log.error("Database access error: " + e.getMessage());
            return UniversalResponse.builder().message("Database access error").status("1").build();
        }
        catch (RuntimeException e) {
            log.error("Unexpected error occurred: " + e.getMessage());
            return UniversalResponse.builder().message("Unexpected error occurred").status("1").build();
        }
    }
}