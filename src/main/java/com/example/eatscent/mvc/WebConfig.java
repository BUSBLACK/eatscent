package com.example.eatscent.mvc;

import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;


/**
 *配置DispatcherServlet上下文
 * @author 11397
 */
@Configuration
//定义mvc扫描的包
@ComponentScan(value = "com.*",includeFilters = {@ComponentScan.Filter (type = FilterType.ANNOTATION,value = Controller.class)})
//启动Spring MVC配置
@EnableWebMvc
public class WebConfig {
    /**
     * 通过注解@Bean初始化视图解析器
     * @return

    @Bean(name = "internalResourceViewResolver")
    public ViewResolver initViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
     */

    /**
     * 初始化RequestMappingHandlerAdapter并加载Http的JSON转换器
     * @return

    @Bean(name = "requestMappingHandlerAdapter")
    public HandlerAdapter initRequestMappingHandlerAdapter(){
        //创建RequestMappingHandlerAdapter适配器
        RequestMappingHandlerAdapter rmhd = new RequestMappingHandlerAdapter();
        //HTTP JSON转换器
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        //MappingJackson2HttpMessageConverter接收JSON类型消息转换
        MediaType medisType = MediaType.APPLICATION_JSON_UTF8;
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(medisType);
        //加入转换器支持类型
        jsonConverter.setSupportedMediaTypes(mediaTypes);
        //给适配器加入JSON转换器
        rmhd.getMessageConverters().add(jsonConverter);
        return rmhd;
    }
     */

}
