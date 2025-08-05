package com.example.loanservice.service;

import com.example.loanservice.dto.LoanRequestDto;
import com.example.loanservice.dto.event.LoanDecisionEvent;
import com.example.loanservice.dto.event.LoanEvent;
import com.example.loanservice.entity.Loan;
import com.example.loanservice.enums.LoanStatus;
import com.example.loanservice.repository.LoanRepository;
import com.example.loanservice.service.kafka.KafkaLoanEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository repository;
    private final KafkaLoanEventProducer kafkaProducer;

    @Transactional
    public Long send(LoanRequestDto request) {
        log.info("Loan application received: {}", request);

        Loan loan = Loan.builder()
                .loanAmount(request.getLoanAmount())
                .loanTermMonths(request.getLoanTermMonths())
                .userIncome(request.getUserIncome())
                .currentDebt(request.getCurrentDebt())
                .loanRating(request.getLoanRating())
                .status(LoanStatus.IN_PROGRESS)
                .build();

        Loan savedLoan = repository.save(loan);
        log.info("Application saved in the database with ID: {}", loan.getId());

        LoanEvent event = LoanEvent.builder()
                .id(savedLoan.getId())
                .loanAmount(savedLoan.getLoanAmount())
                .loanTermMonths(savedLoan.getLoanTermMonths())
                .userIncome(savedLoan.getUserIncome())
                .currentDebt(savedLoan.getCurrentDebt())
                .loanRating(savedLoan.getLoanRating())
                .build();

        kafkaProducer.send(event);

        return savedLoan.getId();
    }

    @Transactional(readOnly = true)
    public String getStatus(Long id) {
        return repository.findById(id)
                .map(loan -> loan.getStatus().name())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "loan application not found"));
    }

    @Transactional
    public void updateLoanStatus(LoanDecisionEvent decisionEvent) {
        log.info("updating status queries {} based on decision: {}",
                decisionEvent.getLoanId(), decisionEvent.isApproved());
        Loan loan = repository.findById(decisionEvent.getLoanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "loan application not found"));

        loan.setStatus(decisionEvent.isApproved() ?
                LoanStatus.APPROVED : LoanStatus.REJECTED);

        repository.save(loan);

        log.info("Application status {} updated to {}",
                loan.getId(), loan.getStatus());
    }
}
