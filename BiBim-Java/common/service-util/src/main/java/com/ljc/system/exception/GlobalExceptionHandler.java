package com.ljc.system.exception;

import com.ljc.common.result.Result;
import com.ljc.model.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.security.access.AccessDeniedException;


/**
 * 全局异常处理器
 *
 * @author dachengzi
 * @date 2023-01-12 22:08
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常处理方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globalException(Exception e) {
        log.error("全局异常处理：{}", e);
        return Result.fail().message("出现异常，请参考日志文件");
    }

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BiBimException.class)
    @ResponseBody
    public Result error(BiBimException e) {
        log.error("自定义异常处理：{}", e);
        return Result.fail().code(e.getResultEnum().getCode()).message(e.getResultEnum().getMessage());
    }

    /**
     * SpringSecurity访问未授权异常
     *
     * @param e
     * @return
     * @throws AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.fail().code(ResultCodeEnum.USER_ERROR_A0301.getCode()).message(ResultCodeEnum.USER_ERROR_A0301.getMessage());
    }
}
