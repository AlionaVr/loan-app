package com.example.loanprocessor.service.producer;

import com.example.loanprocessor.dto.event.LoanDecisionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitDecisionProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    public void sendDecision(LoanDecisionEvent event) {
        try {
            log.info("Sending the loan decision to RabbitMQ: {}", event);
            rabbitTemplate.convertAndSend(exchange, routingKey, event);
            log.info("Loan decision with ID {} successfully submitted", event.getLoanId());
        } catch (Exception e) {
            log.error("Failed to send the loan decision with ID {} to RabbitMQ:{}", event.getLoanId(), e.getMessage());
        }
    }
}
