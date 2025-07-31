package com.example.loanservice.service;

import com.example.loanservice.dto.LoanRequestDto;
import com.example.loanservice.dto.event.LoanEvent;
import com.example.loanservice.entity.Loan;
import com.example.loanservice.enums.LoanStatus;
import com.example.loanservice.repository.LoanRepository;
import com.example.loanservice.service.kafka.KafkaLoanEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository repository;
    private final KafkaLoanEventProducer kafkaProducer;

    public Long send(LoanRequestDto request) {
        Loan loan = Loan.builder()
                .loanAmount(request.getLoanAmount())
                .loanTermMonths(request.getLoanTermMonths())
                .userIncome(request.getUserIncome())
                .currentDebt(request.getCurrentDebt())
                .loanRating(request.getLoanRating())
                .status(LoanStatus.IN_PROGRESS)
                .build();

        Loan savedLoan = repository.save(loan);

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

    public String getStatus(Long id) {
        return repository.findById(id)
                .map(loan -> loan.getStatus().name())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "loan application not found"));
    }
}
