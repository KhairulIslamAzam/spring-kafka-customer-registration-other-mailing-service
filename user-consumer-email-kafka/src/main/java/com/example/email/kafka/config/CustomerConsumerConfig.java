package com.example.email.kafka.config;

import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

/**
 * @author Khairul Islam Azam
 * @created 27/04/2021 - 12:55 PM
 * @project user registration email consumer microservice with kafka
 */
@Configuration
@EnableKafka
public class CustomerConsumerConfig {

//    @Bean
//    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
//            ConcurrentKafkaListenerContainerFactoryConfigurer configurer, ConsumerFactory<Object, Object> kafkaConsumerFactory) {
//
//        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        configurer.configure(factory, kafkaConsumerFactory);
//        /*this conCurrency three means 3 instance for each threads
//        // where each instance assign to each partition
//        before setCurrency all thread are same */
////        factory.setConcurrency(3);
//        return factory;
//    }

}
