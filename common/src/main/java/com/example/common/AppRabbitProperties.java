package com.example.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.rabbitmq")
public class AppRabbitProperties {
    private String exchange;
    private String queue;
    private String routingKey;
}
