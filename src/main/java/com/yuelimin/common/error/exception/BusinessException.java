package com.yuelimin.common.error.exception;

import com.yuelimin.common.code.error.IErrorCode;

/**
 * @author yuelimin
 */
public class BusinessException extends Exception {
    private static final long serialVersionUID = -6110197080072605851L;

    private IErrorCode errorCode;

    public BusinessException() {
        super();
    }

    public BusinessException(IErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BusinessException(String arg0) {
        super(arg0);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(IErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}


