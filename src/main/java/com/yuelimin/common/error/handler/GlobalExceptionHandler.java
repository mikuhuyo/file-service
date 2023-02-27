package com.yuelimin.common.error.handler;

import com.yuelimin.common.code.error.EnumCommonErrorCode;
import com.yuelimin.common.error.exception.BusinessException;
import com.yuelimin.common.rest.RestBaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yuelimin
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {BusinessException.class})
    public RestBaseResponse<EnumCommonErrorCode> handlerBusinessException(BusinessException e) {
        if (e.getErrorCode() != null) {
            log.error("[系统业务异常]-{}", e.getErrorCode().getDesc(), e);
            return RestBaseResponse.fail(e.getErrorCode().getCode(), e.getErrorCode().getDesc());
        }

        log.error("[系统业务异常]-{}", e.getMessage(), e);
        return RestBaseResponse.fail(EnumCommonErrorCode.BUSINESS_EXCEPTION.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestBaseResponse<EnumCommonErrorCode> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[系统参数校验异常]-{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), e);

        EnumCommonErrorCode u = EnumCommonErrorCode.PARAMS_EXCEPTION;
        return RestBaseResponse.fail(u.getCode(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public RestBaseResponse<EnumCommonErrorCode> handlerUnknownException(Exception e) {
        log.error("[系统未知异常]-{}", e.getMessage(), e);
        return RestBaseResponse.fail(EnumCommonErrorCode.UNKNOWN_EXCEPTION);
    }
}

