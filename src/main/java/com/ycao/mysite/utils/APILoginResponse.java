package com.ycao.mysite.utils;

import lombok.Data;

@Data
public class APILoginResponse {
    private String code;
    private String msg;
    private String token;
    private String userId;
    private Integer userRole;
    private String name;

    private static final String CODE_SUCCESS = "success";
    private static final String CODE_FAIL = "fail";

    public APILoginResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public APILoginResponse(String code,String token,String userId,Integer userRole,String name) {
        this.code = code;
        this.msg = msg;
        this.token = token;
        this.userId = userId;
        this.userRole = userRole;
        this.name = name;
    }
    public static APILoginResponse failLogin(String msg) {
        return new APILoginResponse(CODE_FAIL, msg);
    }

    /**
     * used mainly in login
     * @param
     * @return
     */
    public static APILoginResponse successLogin(String token,String userId,Integer userRole,String name){
        return new APILoginResponse(CODE_SUCCESS,token,userId,userRole,name);
    }
}
