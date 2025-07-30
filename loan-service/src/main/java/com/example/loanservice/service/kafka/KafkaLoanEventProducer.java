package com.example.loanservice.service.kafka;

import com.example.loanservice.dto.event.LoanEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaLoanEventProducer {
    private static final String TOPIC = "loan-events";
    private final KafkaTemplate<String, LoanEvent> kafkaTemplate;

    public void send(LoanEvent event) {
        log.info("Sending credit application event to Kafka: {}", event);
        kafkaTemplate.send(TOPIC, event.getId().toString(), event);
    }
}


