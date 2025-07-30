package com.example.loanservice.repository;

import com.example.loanservice.entity.Loan;
import com.example.loanservice.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findById(Long id);

    List<Loan> findByStatus(LoanStatus status);
}
