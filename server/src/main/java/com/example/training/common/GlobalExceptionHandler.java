package com.example.training.common;

import com.example.training.common.enums.StatusEnum;
import com.example.training.common.exceptions.BizException;
import com.example.training.common.exceptions.PermissionDenyException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(BizException.class)
    public ResultResponse<?> handleBizException(BizException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("请求地址:{}, 发生业务异常:{}.", requestURI, e.getMessage());
        return ResultResponse.error(StatusEnum.SERVICE_ERROR, e.getMessage());
    }
    
    @ExceptionHandler(PermissionDenyException.class)
    public ResultResponse<?> handlePermissionDenyException(PermissionDenyException e, HttpServletRequest request) {
        return ResultResponse.error(StatusEnum.FORBIDDEN);
    }

    /**
     * 未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultResponse<?> handleAccessDeniedException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("请求地址:{}, 发生未知异常.", requestURI, e);
        return ResultResponse.error(StatusEnum.SERVICE_ERROR);
    }
}
