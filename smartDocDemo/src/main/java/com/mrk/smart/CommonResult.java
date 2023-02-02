package com.mrk.smart;

import java.io.Serializable;

public class CommonResult<T> extends BaseResult implements Serializable {

    private static final long serialVersionUID = -7268040542410707954L;

    public CommonResult() {

    }

    public CommonResult(boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    public CommonResult(boolean success) {
        this.setSuccess(success);
    }

    public CommonResult(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public CommonResult(boolean success, String message, T data) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setData(data);
    }

    public static CommonResult ok() {
        return ok("success");
    }

    public static <T> CommonResult<T> ok(String msg) {
        return baseCreate("200", msg, true);
    }
    public static CommonResult fail(String code, String msg) {
        return baseCreate(code, msg, false);
    }

    private static <T> CommonResult<T> baseCreate(String code, String msg, boolean success) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setSuccess(success);
        result.setMessage(msg);
        return result;
    }

    public CommonResult<T> setResult(T data) {
        this.setData(data);
        return this;
    }

    public T getData() {
        return (T) super.getData();
    }
}