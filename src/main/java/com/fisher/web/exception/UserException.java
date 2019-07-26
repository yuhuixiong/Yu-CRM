package com.fisher.web.exception;

/**
 * 输入异常类
 */
public class UserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserException() {
    }

    public UserException(String message) {
        super(message);// 把参数传递给Throwable的带String参数的构造方法
    }

}
