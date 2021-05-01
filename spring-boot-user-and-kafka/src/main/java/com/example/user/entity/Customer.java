package com.example.user.entity;

import lombok.*;

/**
 * @author Khairul Islam Azam
 * @created 27/04/2021 - 12:42 PM
 * @project user registration email microservice with kafka
 */

@Getter
@Setter
@ToString
public class Customer {

    private String userId;
    private String username;
    private String password;
    private String email;

}
