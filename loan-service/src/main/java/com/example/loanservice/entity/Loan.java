package com.example.loanservice.entity;

import com.example.loanservice.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "loan_applications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;

    @Column(name = "loan_term_months", nullable = false)
    private Integer loanTermMonths;

    @Column(name = "user_income", nullable = false, precision = 15, scale = 2)
    private BigDecimal userIncome;

    @Column(name = "current_debt", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentDebt;

    @Column(name = "loan_rating", nullable = false)
    private Integer loanRating;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status", nullable = false)
    private LoanStatus status = LoanStatus.IN_PROGRESS;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
