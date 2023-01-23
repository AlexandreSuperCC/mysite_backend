package com.ycao.mysite.utils;

import lombok.Data;

/**
 * api response after used
 * Created by ycao on 10/24
 */
@Data
public class APIResponse <T> {
    private String code;
    private T data;
    private String msg;
    private String token;
    private String userId;

    public APIResponse(String code) {
        this.code = code;
    }

    public APIResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public APIResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }



    public APIResponse(String code, T data,String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    private static final String CODE_SUCCESS = "success";
    private static final String CODE_FAIL = "fail";

    public static APIResponse fail(String msg) {
        return new APIResponse(CODE_FAIL, msg);
    }
    public static APIResponse fail(Object data,String msg) {
        return new APIResponse(CODE_FAIL, data, msg);
    }
    public static APIResponse success() {
        return new APIResponse(CODE_SUCCESS);
    }

    public static APIResponse success(Object data){
        return new APIResponse(CODE_SUCCESS,data);
    }

    public static APIResponse success(String msg){
        return new APIResponse(CODE_SUCCESS,msg);
    }

    /**
     * used in index to get article
     * @param
     * @return
     */
    public static APIResponse success(Object data,String msg){
        return new APIResponse(CODE_SUCCESS,data,msg);
    }


}
