package com.example.loanservice.config;

import com.example.common.AppRabbitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AppRabbitProperties.class)
public class RabbitConsumerConfig {

    private final AppRabbitProperties props;

    @Bean
    public TopicExchange loanDecisionExchange() {
        return new TopicExchange(props.getExchange(), true, false);
    }

    @Bean
    public Queue loanDecisionQueue() {
        return QueueBuilder.durable(props.getQueue()).build();
    }

    @Bean
    public Binding loanDecisionBinding(Queue loanDecisionQueue, TopicExchange loanDecisionExchange) {
        return BindingBuilder
                .bind(loanDecisionQueue)
                .to(loanDecisionExchange)
                .with(props.getRoutingKey());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());

        factory.setDefaultRequeueRejected(false);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);

        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
