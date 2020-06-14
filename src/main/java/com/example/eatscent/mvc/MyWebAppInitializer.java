package com.example.eatscent.mvc;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**通过继承AbstractAnnotationConfigDispatcherServletInitializer的方式来配置Spring MVC 的DispatcherServlet
 * @author 11397
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * Spring IOC 配置
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //可以返回Spring的Java配置文件数组
        return new Class<?>[]{};
    }

    /**
     * DispatcherServlet的URI映射关系配置
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        //可以返回Spring的Java配置文件数组
        return new Class<?>[]{WebConfig.class};
    }

    /**
     * DispatcherServlet拦截请求匹配
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.do"};
    }
}
