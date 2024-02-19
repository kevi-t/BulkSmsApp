package com.project.messageapp.repositories;

import com.project.messageapp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findAllByAccountNumberAndPhoneNumber(String accountNumber, String phoneNumber);
    Users findUsersByAccountNumber(String accountNumber);
}