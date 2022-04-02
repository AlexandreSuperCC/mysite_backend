package com.ycao.mysite.model;


import lombok.Data;

/*
user model
 */
@Data
public class UserDomain {
    private Integer uid;
    private String username;
    private String password;
    private String email;
}
