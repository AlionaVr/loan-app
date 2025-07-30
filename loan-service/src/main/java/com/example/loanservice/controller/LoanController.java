package com.example.loanservice.controller;

import com.example.loanservice.dto.LoanRequestDto;
import com.example.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/apply")
    public ResponseEntity<String> applyForCredit(
            @Validated @RequestBody LoanRequestDto request) {

        Long requestId = loanService.send(request);
        return ResponseEntity.ok("The loan application has been sent. Loan ID: " + requestId);
    }
}
