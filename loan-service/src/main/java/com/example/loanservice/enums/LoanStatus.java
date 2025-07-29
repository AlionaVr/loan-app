package com.example.loanservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoanStatus {
    IN_PROGRESS("в обработке"),
    APPROVED("одобрено"),
    REJECTED("отказано");

    private final String description;


}