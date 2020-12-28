package cn.comesaday.coe.core.config.web;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <Description>
 *
 * @author ChenWei
 * @CreateAt 2020-08-16 15:40
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 默认跳转-不带业务
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("app-index");
    }

    /**
     * 视图解析器配置
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setViewClass(JstlView.class);
        internalResourceViewResolver.setPrefix("/page/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    /**
     * 视图解析器注册
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
    }


    /**
     * 拦截所有未登录-迁移至spring security管理
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> paths = Arrays.asList("/", "/frame/login.do", "/frame/loginVerify.do", "/**/*.css",
//                "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif");
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(paths);
//    }

    /**
     * 静态资源放行-迁移至spring security管理
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    /**
     * 路径匹配
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // setUseSuffixPatternMatch true: 自动过滤后缀'.do'
        // setUseTrailingSlashMatch true: 自动去掉'/'
        configurer.setUseSuffixPatternMatch(true)
                  .setUseRegisteredSuffixPatternMatch(false);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
    }

    @Bean
    public MultipartConfigElement fileSize() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        return factory.createMultipartConfig();
    }
}
