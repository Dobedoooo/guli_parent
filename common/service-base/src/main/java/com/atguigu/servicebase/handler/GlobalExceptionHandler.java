package com.atguigu.servicebase.handler;

import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.exception.GuliException;
import com.atguigu.commonutils.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e) {
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
