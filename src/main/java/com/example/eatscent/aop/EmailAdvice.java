package com.example.eatscent.aop;

import com.example.eatscent.aop.inface.emailInfoIntroduction;
import com.example.eatscent.until.info.Email_info;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 为邮件发送提供的AOP切面，切面= 切点+通知
 * @author 11397
 */
@Aspect
public class EmailAdvice {
    Logger logger = LoggerFactory.getLogger(EmailAdvice.class);
    /**
     * 定义切点，一般使用空方法，execution(* com.example.eatscent.until.SendEmail.Email_Send_HTML(..))
     * execution方法执行时触发 *返回任意类型 com.example.eatscent.until.SendEmail方法所属类 Email_Send_HTML 方法 （..）任意参数类型
     * 触发方式有很多 可以根据需要选择
     * 带参数的切点定义
     * execution(* com.example.eatscent.until.SendEmail.Email_Send_HTML(int)) && args(Num)
     * execution方法执行时触发 *返回任意类型 com.example.eatscent.until.SendEmail方法所属类 Email_Send_HTML 方法 （int）int类型 args(Num) 指定参数 Num
     * 切点带参数可以处理一些特定需求，比如说可以结合redis缓存，记录系统发送邮件数目
     */
    @Pointcut("execution(* com.example.eatscent.until.SendEmail.Email_Send_HTML(..))")
    public void Email_Send_HTML(){}
//    @Before("Email_Send_HTML()")
//    public void sendBefore(){
//        logger.info("Begin Send Email");
//    }
//    @After("Email_Send_HTML()")
//    public void sendAfter(){
//        logger.info("End Send Email");
//    }
//    @AfterThrowing("Email_Send_HTML()")
//    public void sendAfterThrowing(){
//        logger.error("Send Email is fail");
//    }
//    @AfterReturning("Email_Send_HTML()")
//    public void sendAfterReturning(){
//        logger.info("Send Email is success");
//    }

    /**
     * 环绕通知，可以当作Before、After、AfterReturning、AfterThrowing的组合来使用
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
