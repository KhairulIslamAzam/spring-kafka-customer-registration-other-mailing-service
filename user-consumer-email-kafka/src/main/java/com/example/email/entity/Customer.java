package com.example.email.entity;

import lombok.*;

/**
 * @author Khairul Islam Azam
 * @created 27/04/2021 - 12:42 PM
 * @project IntelliJ IDEA
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {


    private String userId;
    private String username;
    private String password;
    private String email;

}
