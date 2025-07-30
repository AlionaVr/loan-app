package com.example.loanservice.dto;

import com.example.loanservice.enums.LoanStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class LoanResponseDTO {

    private Long id;
    private LoanStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}