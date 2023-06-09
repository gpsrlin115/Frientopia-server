package com.hnu.capstone.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PayRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findById(Long id);
    List<Payment> findByUser(User user);
    Optional<Payment> findByImpUID(String impUID);
}
