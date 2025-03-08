package com.py.utils;

import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    // 默认构造方法
    public Result() {
    }

    // 带数据的构造方法
    public Result(T data) {
        this.data = data;
    }

    // 成功地响应，返回没有数据的结果
    public static Result success() {
        Result result = new Result<>();
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

    // 成功地响应，返回包含数据的结果
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

    // 错误的响应，返回带有错误码和错误信息的结果
    public static <T> Result<T> error(String code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    // 链式调用的方式
    public Result<T> withCode(String code) {
        this.setCode(code);
        return this;
    }

    public Result<T> withMsg(String msg) {
        this.setMsg(msg);
        return this;
    }
}
