package com.eventRegistration.eventRegistrationSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventRegistration.eventRegistrationSystem.model.Visitor;
import java.util.List;


@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer> {

     // Custom query method to find a visitor by their email
     Optional<Visitor> findByEmail(String email);

     // Custom query method to find a visitor by their phone
     Optional<Visitor> findByPhone(String phone);
     
     // Custom query method to find a visitor by their full name
     Optional<Visitor> findByFullName(String fullName);
}
