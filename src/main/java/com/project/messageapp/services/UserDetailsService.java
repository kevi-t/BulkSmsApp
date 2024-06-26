package com.project.messageapp.services;

import com.project.messageapp.repositories.BulkSmsUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private  final BulkSmsUsersRepository bulkSmsUsersRepository;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return bulkSmsUsersRepository.findBulkSmsUsersByStaffNo(userEmail);
    }
}