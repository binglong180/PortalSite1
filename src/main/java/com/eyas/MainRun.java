package com.eyas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/6 20:08
 * @Description:
 */
@SpringBootApplication(scanBasePackages = "com.eyas.business",exclude = ErrorMvcAutoConfiguration.class)  //去掉error自动跳转/error请求
@EnableTransactionManagement
@EnableConfigurationProperties
@EntityScan({"com.eyas.business.model.jpa"})
@EnableJpaRepositories
public class MainRun extends SpringBootServletInitializer {
    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(MainRun.class);
        return builder;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainRun.class,args);
    }
}
