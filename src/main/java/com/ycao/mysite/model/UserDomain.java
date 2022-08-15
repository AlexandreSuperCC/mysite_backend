package com.ycao.mysite.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
user model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain {
    private Integer uid;
    private String username;
    private String password;
    private String email;
    private Integer role;
}
