package com.eyas.business.model.propertiesmap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/16 15:43
 * @Description:
 */
@PropertySource("classpath:uploadfile.properties")
@Component
@Profile("linux")
public class UploadPathLinux implements UploadPathInterface {
    @Value("${springboot.linux.upload.backup.imapge.path}")
    private String bcimgpath; //upload image backup path

    @Value("${springboot.linux.upload.unsubmit.imapge.path}")
    private String unsubmitpath;

    public String getBcimgpath() {
        return bcimgpath;
    }

    public void setBcimgpath(String bcimgpath) {
        this.bcimgpath = bcimgpath;
    }

    public String getUnsubmitpath() {
        return unsubmitpath;
    }

    public void setUnsubmitpath(String unsubmitpath) {
        this.unsubmitpath = unsubmitpath;
    }
}
