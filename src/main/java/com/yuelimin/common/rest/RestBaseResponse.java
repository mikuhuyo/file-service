package com.yuelimin.common.rest;

import com.yuelimin.common.code.error.EnumCommonErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yuelimin
 */
@Data
public class RestBaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1989765099779053390L;

    private int code;

    private String msg;

    private T result;

    public RestBaseResponse() {
        this(0, "success");
    }

    public RestBaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestBaseResponse(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static <T> RestBaseResponse<T> success() {
        return new RestBaseResponse<T>();
    }

    public static <T> RestBaseResponse<T> success(T result) {
        RestBaseResponse<T> response = new RestBaseResponse<T>();
        response.setResult(result);
        return response;
    }

    public static <T> RestBaseResponse<T> fail(String msg) {
        return new RestBaseResponse<>(-1, msg);
    }

    public static <T> RestBaseResponse<T> fail(EnumCommonErrorCode enumCommonErrorCode) {
        return new RestBaseResponse<>(enumCommonErrorCode.getCode(), enumCommonErrorCode.getDesc());
    }

    public static <T> RestBaseResponse<T> fail(int code, String msg) {
        return new RestBaseResponse<>(code, msg);
    }
}

