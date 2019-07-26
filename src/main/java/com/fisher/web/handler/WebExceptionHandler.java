package com.fisher.web.handler;

import com.fisher.web.Result;
import com.fisher.web.exception.InputException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public Result unknownAccount(UnknownAccountException e) {
        LOGGER.error("账号不存在", e);
        return Result.error(1, "账号不存在");
    }

    @ExceptionHandler
    public Result incorrectCredentials(IncorrectCredentialsException e) {
        LOGGER.error("密码错误", e);
        return Result.error(-2, "密码错误");
    }

    public Result inputException(InputException e){
        LOGGER.error("输入异常", e);
        return Result.error(-3,"请检查提交数据是否有误："+e);
    }

    @ExceptionHandler
    public Result unknownException(Exception e) {
        LOGGER.error("发生了未知异常", e);
        // 发送邮件通知技术人员.
        return Result.error(-99, "系统出现错误, 请联系网站管理员!");
    }

}
