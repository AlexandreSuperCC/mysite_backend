package com.ycao.mysite.exception;

/**
 * unified exception handler class
 * Created by ycao on 10/24
 */
public class  BusinessException extends RuntimeException{
    protected String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String[] errMessageArguments;

    public static BusinessException withErrorCode(String errorCode){
        BusinessException businessException = new BusinessException();
        businessException.errorCode = errorCode;
        return businessException;
    }

    public BusinessException withErrMessageArguments(String... errMessageArguments) {
        this.errMessageArguments = errMessageArguments;
        return this;
    }
}
