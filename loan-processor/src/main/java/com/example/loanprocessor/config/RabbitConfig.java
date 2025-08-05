package com.example.loanprocessor.config;

import com.example.common.AppRabbitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AppRabbitProperties.class)
public class RabbitConfig {

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
    public Binding loanDecisionBinding() {
        return BindingBuilder
                .bind(loanDecisionQueue())
                .to(loanDecisionExchange())
                .with(props.getRoutingKey());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}
