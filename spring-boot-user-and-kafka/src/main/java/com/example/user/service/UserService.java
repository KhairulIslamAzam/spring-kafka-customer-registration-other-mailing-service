//package com.example.user.service;
//
//import com.example.user.entity.User;
//import com.example.user.kafka.producer.UserEventProducer;
////import com.example.user.repository.UserRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @author Khairul Islam Azam
// * @created 27/04/2021 - 12:51 PM
// * @project IntelliJ IDEA
// */
//@Service
//public class UserService {
////    @Autowired
////    private UserRepository userRepository;
//    @Autowired
//    private UserEventProducer userEventProducer;
//    public void registerUser(User user,String topic)  {
////        User createdUser = userRepository.save(user);
//        userEventProducer.sendLibraryEventV2(topic, user);
////        return createdUser;
//    }
//
//}
