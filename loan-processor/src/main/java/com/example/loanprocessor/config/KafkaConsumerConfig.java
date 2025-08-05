package com.example.loanprocessor.config;

import com.example.common.dto.event.LoanEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProps;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LoanEvent> kafkaListenerContainerFactory() {
        var consumerProps = kafkaProps.buildConsumerProperties();

        JsonDeserializer<LoanEvent> deserializer =
                new JsonDeserializer<>(LoanEvent.class, false);

        var cf = new DefaultKafkaConsumerFactory<>(
                consumerProps,
                new StringDeserializer(),
                deserializer
        );

        var factory = new ConcurrentKafkaListenerContainerFactory<String, LoanEvent>();
        factory.setConsumerFactory(cf);
        return factory;
    }
}