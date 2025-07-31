package com.example.loanservice.controller;

import com.example.loanservice.dto.LoanRequestDto;
import com.example.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/status/{id}")
    public ResponseEntity<String> getStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(loanService.getStatus(id));
    }
}
