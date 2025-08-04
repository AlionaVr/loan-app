package com.example.loanprocessor.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanDecisionEvent {
    private Long loanId;
    private boolean approved;
}
