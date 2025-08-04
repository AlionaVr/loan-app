package com.example.loanprocessor.service.consumer;

import com.example.loanprocessor.dto.event.IncomingLoanEvent;
import com.example.loanprocessor.service.LoanDecisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaLoanConsumer {

    private final LoanDecisionService loanDecisionService;

    @KafkaListener(topics = "loan-events", groupId = "loan-group")
    public void listen(@Payload IncomingLoanEvent event,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                       @Header(KafkaHeaders.OFFSET) long offset) {

        log.info("Message received from Kafka - Topic: {}, Partition: {}, Offset: {}, Event: {}",
                topic, partition, offset, event);
        try {
            loanDecisionService.process(event);
            log.info("Application with ID {} successfully processed", event.getId());
        } catch (Exception e) {
            log.error("Error when processing an application with ID {}: {}", event.getId(), e.getMessage(), e);
        }
    }
}
