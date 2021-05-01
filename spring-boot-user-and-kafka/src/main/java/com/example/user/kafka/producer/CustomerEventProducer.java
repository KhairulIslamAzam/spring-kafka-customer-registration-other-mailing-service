package com.example.user.kafka.producer;

import com.example.user.entity.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import java.util.List;

/**
 * @author Khairul Islam Azam
 * @created 26/04/2021 - 9:19 AM
 * @project IntelliJ IDEA
 */
@Component
@Slf4j
public class CustomerEventProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public CustomerEventProducer() {
        objectMapper = new ObjectMapper();
    }

//    public void sendLibraryEvent(Customer customer) throws JsonProcessingException {
//        String key = customer.getUserId();
//        String value = objectMapper.writeValueAsString(customer);
//
//        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
//        listenableFuture.addCallback(new ListenableFutureCallback<>() {  //same as new ListenableFutureCallback<SendResult<Integer, String>>()
//            @Override
//            public void onFailure(Throwable ex) {
//                handleFailure(key, value, ex);
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                handleSuccess(key, value, result);
//            }
//        });
//    }

    public void sendLibraryEventV2(String topic, Customer customer) throws JsonProcessingException {

        String key = customer.getUserId();
        String value = objectMapper.writeValueAsString(customer);

//        ProducerRecord<String, String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<String, String>> listenableFuture;
        listenableFuture = kafkaTemplate.send(topic,key,value);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {  //same as new ListenableFutureCallback<SendResult<Integer, String>>()

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(customer, ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                handleSuccess(customer, result);
            }
        });
    }

    public void sendLibraryEventV3(String topic, String message) throws JsonProcessingException {


        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic,message);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {  //same as new ListenableFutureCallback<SendResult<Integer, String>>()

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(message, ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                handleSuccess(message, result);
            }
        });
    }

//    private ProducerRecord<String, String> buildProducerRecord(String key, String value, String topic) {
//
//        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));
//        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
//    }

//    public SendResult<String, String> sendLibraryEventSynchronous(Customer customer) throws JsonProcessingException, InterruptedException, ExecutionException, TimeoutException {
//
//        String key = customer.getUserId();
//        String value = objectMapper.writeValueAsString(customer);
//        SendResult<String, String> sendResult;
//        try {
//            sendResult = kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS);
//        } catch (ExecutionException | InterruptedException e) {
//            log.error("ExecutionException | InterruptedException in sending the message and exception is {} " + e.getMessage());
//            throw e;
//        } catch (Exception e) {
//            log.error("Exception sending the message and the exception is {} " + e.getMessage());
//            throw e;
//        }
//        return sendResult;
//    }


    private void handleFailure(Object object, Throwable ex) {
        log.error("Error sending the message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in onFailure: {}", throwable.getMessage());
        }
    }

    private void handleSuccess(Object object, SendResult<String, String> result) {
        log.info("Message sent Successfully for the key and the value is {} , partitions is {}",
                object.toString(), result.getProducerRecord().partition());
    }
}
