//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xescm.ofc.utils;

import com.github.pagehelper.Page;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 4893280118017319089L;
    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MESSAGE = "操作成功";
    public static final int CAPTCHA_CODE_ERROR = 600;
    public static final int ERROR_CODE = 500;
    public static final String ERROR_MESSAGE = "内部异常";
    public static final int ILLEGAL_ARGUMENT_CODE_ = 100;
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "参数非法";
    private int code;
    private String message;
    private T result;

    public Response() {
        this(200, "操作成功");
    }

    public Response(int code, String message) {
        this.code(code).message(message);
    }

    public Response(int code, String message, T result) {
        this.code(code).message(message).result(result);
    }

    public Response(int code, String message, T result, Page<T> page) {
        this.code(code).message(message).result(result);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Response<T> code(int code) {
        this.setCode(code);
        return this;
    }

    public Response<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public Response<T> result(T result) {
        this.setResult(result);
        return this;
    }
}
