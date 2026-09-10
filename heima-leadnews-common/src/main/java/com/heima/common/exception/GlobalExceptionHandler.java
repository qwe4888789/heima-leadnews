package com.heima.common.exception;

import com.heima.common.dtos.Result;
import com.heima.common.dtos.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice  // @RestControllerAdvice  = @ControllerAdvice + @ResposneBody
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(value = LeadNewsException.class)
    public Result handleLeadNewsException(LeadNewsException e){
        return Result.errorMessage(e.getMessage(),e.getStatus(),null);
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e){
        return Result.errorMessage(e.getMessage(), StatusCode.FAILURE.code(),null);
    }
}
