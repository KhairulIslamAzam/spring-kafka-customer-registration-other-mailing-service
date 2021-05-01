package com.example.user.controller;

import com.example.user.entity.Customer;
import com.example.user.kafka.producer.CustomerEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Khairul Islam Azam
 * @created 27/04/2021 - 12:42 PM
 * @project user registration email procedure microservice with kafka
 */

@RestController
@Slf4j
public class UserController {

    @Autowired
    private CustomerEventProducer sender;
    //    @Autowired
//    private KafkaTemplate<String, Customer> kafkaTemplate;
    @Value(value = "${customer.topic.name.mail}")
    private String mailTopic;

    @Value(value = "${customer.topic.name.message}")
    private String messageTopic;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        ResponseEntity<Customer> body;
        try {
            sender.sendLibraryEventV2(mailTopic, customer);
            body = ResponseEntity.status(HttpStatus.OK).body(customer);
            return body;
        } catch (JsonProcessingException e) {
            log.error("failed to produce customer registration mailing");
        }

        body = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customer);
        return body;
    }

    @PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register() {
        ResponseEntity<String> body;
        try {
            sender.sendLibraryEventV3(messageTopic, "sending message");
            body = ResponseEntity.status(HttpStatus.OK).body("sending message");
            return body;
        } catch (JsonProcessingException e) {
            log.error("failed to produce customer registration mailing");
        }

        body = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sending message");
        return body;
    }

//    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> message(@RequestBody String message) {
//
//        ResponseEntity<String> body;
//        try {
//            sender.sendLibraryEventV2(messageTopic, message);
//            body = ResponseEntity.status(HttpStatus.OK).body(message);
//            return body;
//        } catch (JsonProcessingException e) {
//            log.error("failed to produce customer registration mailing");
//        }
//
//        body = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
//        return body;
//    }
}
