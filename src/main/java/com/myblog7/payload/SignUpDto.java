package com.myblog7.payload;
//DTO class also known as plain old java object(pojo).meaning this class dealing with encapsulation
//using getter and setter method.and variables.
import lombok.Data;
@Data
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
}