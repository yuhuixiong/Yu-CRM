package com.fisher.web.handler;

import com.alibaba.fastjson.JSON;
import com.fisher.annotation.ApiLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * 接口日志
 */

@Aspect
@Component
public class ApiLogHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogHandler.class);

    @Pointcut("@annotation(com.fisher.annotation.ApiLog)")
    public void controllerAspect(){}


    // 控制层环绕日志
    @Around("controllerAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        Object result = new Object();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        ApiLog logController = null;
        if(method != null){
            logController = method.getAnnotation(ApiLog.class);
        }
        if(logController == null){
            return result;
        }
        LOGGER.info("===============================控制层消息===============================");
        LOGGER.info("请求时间:    " + new Date());
        LOGGER.info("请求接口:    " + request.getMethod() + "[" + request.getRequestURL() + "]");
        LOGGER.info("请求参数:    " + Arrays.toString(joinPoint.getArgs()));
        // 执行切点方法
        result = joinPoint.proceed();
        LOGGER.info("接口返回:    " + JSON.toJSONString(result));
        LOGGER.info("========================================================================");
        return result;
    }

    //服务层异常日志
//    @AfterThrowing(pointcut = "serviceAspect()",throwing = "e")
//    public void doAfterThrowing(JoinPoint joinPoint, exception e){
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//
//        String ip = request.getRemoteAddr();
//        String params = "";
//        if(joinPoint.getArgs() != null && joinPoint.getArgs().length>0){
//            for (int i=0;i<joinPoint.getArgs().length;i++){
//                params += JSON.toJSONString(joinPoint.getArgs()[i]);
//            }
//        }
//        try {
//            /** 控制台输出 **/
//            LOGGER.info("异常通知开始>>");
//            LOGGER.info("异常代码:" + e.getClass().getName());
//            LOGGER.info("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
//            LOGGER.info("请求IP:" + ip);
//            LOGGER.info("请求参数:" + params);
//        }catch (exception ex) {
//            //记录本地异常日志
//            LOGGER.error("异常通知异常>>");
//            LOGGER.error("异常信息:{}", ex.getMessage());
//        }
//    }

}
