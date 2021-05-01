package com.example.user.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


/**
 * @author Khairul Islam Azam
 * @created 27/04/2021 - 12:55 PM
 * @project user registration email microservice with kafka
 */
@Configuration
public class CustomerProducerConfig {

    @Value(value = "${customer.topic.name.mail}")
    private String mailTopic;

    @Value(value = "${customer.topic.name.message}")
    private String messageTopic;

    @Bean
    public NewTopic userMailingEvent(){
        return TopicBuilder.name(mailTopic)
                .partitions(2)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic userMessageEvent(){
        return TopicBuilder.name(messageTopic)
                .partitions(2)
                .replicas(2)
                .build();
    }
}
