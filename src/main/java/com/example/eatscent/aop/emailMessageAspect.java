package com.example.eatscent.aop;

import com.example.eatscent.aop.inface.emailInfoIntroduction;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * @author 11397
 */
@Aspect
@Component
public class emailMessageAspect {
    /**
     * AOP引入，引入可以在不改变原有类的基础下加进新的方法和属性,要引入的方法必须定义接口
     * 在调用的时候需要把引入方法的类对象进行强转为引入的方法的接口的类型，同时把切面定义成一个bean
     * @DeclareParents(value = "com.example.eatscent.until.SendEmail",defaultImpl = emailInfoIntroductionimpl.class )
     * @DeclareParents 表明要引入的接口
     * value 要引入方法的类
     * +表示SendEmail所有的子类型,但是如果加+号的话会在启动的时候报错
     * defaultImpl 提供引入方法的接口实现类
     * @return
     */
    @DeclareParents(value = "com.example.eatscent.until.SendEmail",defaultImpl = emailInfoIntroductionimpl.class  )
    public static emailInfoIntroduction emailInfoIntroduction;

}
