package com.eyas.business.config.springmvc;

import com.eyas.business.model.propertiesmap.UploadPathInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/16 16:07
 * @Description:
 */
@Configuration
@PropertySource("classpath:uploadfile.properties")
@Profile("linux")
public class FileUploadConfigLinux {
    @Value("${springboot.linux.upload.tmp.imapge.path}")
    private String location;
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize = "1MB";
    private String maxRequestSize = "10MB";
    @Value("${spring.servlet.multipart.file-size-threshold}")
    private String fileSizeThreshold = "0";

    @Bean
    public MultipartConfigElement createMultipartConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        if(StringUtils.hasText(this.fileSizeThreshold)) {
            factory.setFileSizeThreshold(this.fileSizeThreshold);
        }

        if(StringUtils.hasText(this.location)) {
            File file = new File(this.location);
            if(!file.exists())
                file.mkdirs();
            factory.setLocation(this.location);
        }

        if(StringUtils.hasText(this.maxRequestSize)) {
            factory.setMaxRequestSize(this.maxRequestSize);
        }

        if(StringUtils.hasText(this.maxFileSize)) {
            factory.setMaxFileSize(this.maxFileSize);
        }

        return factory.createMultipartConfig();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getMaxRequestSize() {
        return maxRequestSize;
    }

    public void setMaxRequestSize(String maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    public String getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    public void setFileSizeThreshold(String fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }
}
