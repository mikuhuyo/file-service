package com.yuelimin.common.code.error;

/**
 * @author yuelimin
 */
public enum EnumCommonErrorCode implements IErrorCode {
    /**
     * 状态码
     */
    SUCCESS(0, "成功"),

    E_800401(800401, "用户未认证"),
    E_800403(800403, "用户无权访问"),
    E_800404(800404, "资源丢失"),
    E_800405(800405, "服务器不支持该请求方式"),
    E_800406(800406, "缺少条件"),
    E_800415(800415, "服务器不支持该媒体类型"),

    PARAMS_EXCEPTION(999996, "参数校验异常"),
    SERVICE_EXCEPTION(999997, "服务执行异常"),
    BUSINESS_EXCEPTION(999998, "业务异常"),
    UNKNOWN_EXCEPTION(999999, "系统未知异常"),

    ;

    private final int code;
    private final String desc;

    EnumCommonErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}

