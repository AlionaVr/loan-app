package com.example.loanservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class LoanRequestDto {
    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "1000.0", message = "Minimum loan amount is 1000")
    private BigDecimal loanAmount;

    @NotNull(message = "Loan term is required")
    @Min(value = 1, message = "Minimum loan term is 1 month")
    @Max(value = 360, message = "Maximum loan term is 360 months")
    private Integer loanTermMonths;

    @NotNull(message = "User income is required")
    @DecimalMin(value = "0.0", message = "Income cannot be negative")
    private BigDecimal userIncome;

    @NotNull(message = "Current debt is required")
    @DecimalMin(value = "0.0", message = "Current debt cannot be negative")
    private BigDecimal currentDebt;

    @NotNull(message = "Credit rating is required")
    @Min(value = 300, message = "Minimum credit rating is 300")
    @Max(value = 850, message = "Maximum credit rating is 850")
    private Integer loanRating;
}