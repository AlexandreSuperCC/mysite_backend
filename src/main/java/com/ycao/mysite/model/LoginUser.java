package com.ycao.mysite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "login form")
public class LoginUser {

    @ApiModelProperty(value = "login name", name = "username", required = true, example = "admin")
    private String username;

    @ApiModelProperty(value = "login password", name = "password", required = true, example = "0423")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
