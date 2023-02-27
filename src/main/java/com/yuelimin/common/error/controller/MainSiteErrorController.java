package com.yuelimin.common.error.controller;

import com.yuelimin.common.code.error.EnumCommonErrorCode;
import com.yuelimin.common.rest.RestBaseResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuelimin
 */
@RestController
public class MainSiteErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public RestBaseResponse<EnumCommonErrorCode> handleError(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        if (status == 401) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return RestBaseResponse.fail(EnumCommonErrorCode.E_800401);
        }

        if (status == 403) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return RestBaseResponse.fail(EnumCommonErrorCode.E_800403);
        }

        if (status == 404) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return RestBaseResponse.fail(EnumCommonErrorCode.E_800404);
        }

        if (status == 405) {
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            return RestBaseResponse.fail(EnumCommonErrorCode.E_800405);
        }

        if (status == 406) {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return RestBaseResponse.fail(EnumCommonErrorCode.E_800406);
        }

        if (status == 415) {
            response.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
            return RestBaseResponse.fail(EnumCommonErrorCode.E_800415);
        }

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return RestBaseResponse.fail(EnumCommonErrorCode.SERVICE_EXCEPTION);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}


