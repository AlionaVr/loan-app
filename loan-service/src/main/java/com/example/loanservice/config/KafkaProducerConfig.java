package com.example.loanservice.config;

import com.example.common.dto.event.LoanEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProps;

    @Bean
    public ProducerFactory<String, LoanEvent> producerFactory() {
        // from application.properties
        var props = kafkaProps.buildProducerProperties();

        var serializer = new JsonSerializer<LoanEvent>();

        return new DefaultKafkaProducerFactory<>(
                props,
                new StringSerializer(),
                serializer
        );
    }

    @Bean
    public KafkaTemplate<String, LoanEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}