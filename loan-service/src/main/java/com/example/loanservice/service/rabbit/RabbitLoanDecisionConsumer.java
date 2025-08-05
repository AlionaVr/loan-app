package com.example.loanservice.service.rabbit;

import com.example.loanservice.dto.event.LoanDecisionEvent;
import com.example.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitLoanDecisionConsumer {

    private final LoanService loanService;

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void handleLoanDecision(@Payload LoanDecisionEvent decisionEvent,
                                   @Header Map<String, Object> headers) {
        log.info("Loan decision received from RabbitMQ: {}", decisionEvent);
        try {
            loanService.updateLoanStatus(decisionEvent);
            log.info("Application status with ID {} successfully updated", decisionEvent.getLoanId());
        } catch (Exception e) {
            log.error("Error processing loan decision for loan ID {}: {}", decisionEvent.getLoanId(), e.getMessage(), e);
        }
    }
}
