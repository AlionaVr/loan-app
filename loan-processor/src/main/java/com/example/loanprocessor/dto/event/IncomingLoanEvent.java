package com.example.loanprocessor.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomingLoanEvent {

    private Long id;
    private BigDecimal loanAmount;
    private int loanTermMonths;
    private BigDecimal userIncome;
    private BigDecimal currentDebt;
    private Integer loanRating;
}