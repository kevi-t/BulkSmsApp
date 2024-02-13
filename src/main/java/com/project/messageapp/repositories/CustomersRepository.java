package com.project.messageapp.repositories;

import com.project.messageapp.models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {
    List<Customers> findAllByAccountNumberAndPhoneNumber(String accountNumber,String phoneNumber);
    Customers findCustomersByAccountNumber(String accountNumber);
}
