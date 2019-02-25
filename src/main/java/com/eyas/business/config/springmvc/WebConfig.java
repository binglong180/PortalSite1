package com.eyas.business.config.springmvc;

import com.eyas.business.config.springmvc.interceptor.AdminLoginInterceptor;
import com.eyas.business.controllor.AdminControllor;
import com.eyas.business.model.propertiesmap.UploadPathInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.MultipartConfigElement;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/7 10:18
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UploadPathInterface uploadPathInterface;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/private/**").addResourceLocations("/WEB-INF/");
    }

    /**
     * setUseSuffixPatternMatch 后缀模式匹配  request:"/user"  controller用"/user.html也可以访问"
     * setUseTrailingSlashMatch 自动后缀路径模式匹配  "/user"是否匹配"/user/"
     *
     * @param configurer
     */
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginInterceptor()).addPathPatterns("/eyas/admin/**").order(1);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/html/index.html");
    }

    @Bean
    public ServerStartUp serverStartUp() {
        ServerStartUp serverStartUp = new ServerStartUp();
        serverStartUp.resetStaticFile(uploadPathInterface.getBcimgpath(),AdminControllor.class.getResource("/").getPath()+"/static/upload/image/");
        return serverStartUp;
    }


}
