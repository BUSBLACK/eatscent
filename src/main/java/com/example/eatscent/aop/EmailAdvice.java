package com.example.eatscent.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 为邮件发送提供的AOP
 * @author 11397
 */
@Aspect
public class EmailAdvice {
    Logger logger = LoggerFactory.getLogger(EmailAdvice.class);
    @Pointcut("execution(* com.example.eatscent.until.SendEmail.Email_Send_HTML(..))")
    public void Email_Send_HTML(){}
    @Before("Email_Send_HTML()")
    public void sendBefore(){
        logger.info("Begin Send Email");
    }
    @After("Email_Send_HTML()")
    public void sendAfter(){
        logger.info("End Send Email");
    }
    @AfterThrowing("Email_Send_HTML()")
    public void sendAfterThrowing(){
        logger.error("Send Email is fail");
    }
    @AfterReturning("Email_Send_HTML()")
    public void sendAfterReturning(){
        logger.info("Send Email is success");
    }

    /**
     * 环绕通知
     * @param joinPoint
     */
    @Around("Email_Send_HTML()")
    public boolean sendAround(ProceedingJoinPoint joinPoint) {
        try {
            logger.info("Begin Send Email Around");
            joinPoint.proceed();
            logger.info("End Send Email Around");
            logger.info("Send Email is success Around");
            return true;
        } catch (Throwable throwable) {
            logger.error("Send Email is fail Around");
            throwable.printStackTrace();
            return false;
        }
    }
}
