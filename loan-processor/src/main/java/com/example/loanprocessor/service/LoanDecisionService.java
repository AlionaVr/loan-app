package com.example.loanprocessor.service;

import com.example.loanprocessor.dto.event.IncomingLoanEvent;
import com.example.loanprocessor.dto.event.LoanDecisionEvent;
import com.example.loanprocessor.service.producer.RabbitDecisionProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanDecisionService {

    private static final BigDecimal MAX_DEBT_TO_INCOME_RATIO = BigDecimal.valueOf(0.5);
    private final RabbitDecisionProducer producer;

    public void process(IncomingLoanEvent event) {
        log.info("Processing a loan application: {}", event);

        BigDecimal maxAllowedDebt = event.getUserIncome().multiply(MAX_DEBT_TO_INCOME_RATIO);
        BigDecimal totalDebtAfterLoan = event.getCurrentDebt()
                .add(calculateMonthlyPayment(event));

        boolean approved = totalDebtAfterLoan.compareTo(maxAllowedDebt) <= 0;

        LoanDecisionEvent decision = LoanDecisionEvent.builder()
                .loanId(event.getId())
                .approved(approved)
                .build();

        log.info("proposed decision on request {}: {}", event.getId(), approved ? "APPROVED" : "REJECTED");

        producer.sendDecision(decision);
    }

    private BigDecimal calculateMonthlyPayment(IncomingLoanEvent event) {
        return event.getLoanAmount().divide(
                BigDecimal.valueOf(event.getLoanTermMonths()),
                2, RoundingMode.HALF_UP);
    }
}