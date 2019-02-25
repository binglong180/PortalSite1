package com.eyas.business.config.springmvc;

import com.eyas.utils.FileUtils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/17 14:14
 * @Description:
 */
public class ServerStartUp {
    public void resetStaticFile(String backupPath,String staticPath) {
        File file = new File(backupPath);
        if(!file.exists()) {
            file.mkdirs();
            return;
        }
        File[] images = file.listFiles();
        if(images!=null&&images.length>0) {
            for(int i=0;i<images.length;i++) {
                String imageName = images[i].getName();
                if(new File(staticPath+imageName).exists())
                    continue;
                try {
                    FileUtils.copyFile(images[i],staticPath+imageName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
