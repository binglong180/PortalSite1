package com.eyas.utils.FileUtils;

import org.springframework.util.StringUtils;

import java.io.*;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/16 15:03
 * @Description:
 */
public class FileUtils {
    public static void copyFile(File source,String newPath) throws IOException {
        if(source==null||!source.exists())
            throw new IOException("source stream not valid");

        //构造新的路径文件
        if(newPath.lastIndexOf(".")>-1&&newPath.length()>newPath.lastIndexOf(".")+2&&newPath.length()<=newPath.lastIndexOf(".")+4) { //判断存在文件格式,小于4是为了保证
            ///opt/apache-tomcat-9.0.16/webapps/eyaswebsite/WEB-INF/classes//static/upload/image/  这种路径中含有“.”的情况发生

        }else {
            newPath = newPath+"/"+source.getName();
        }
        File newFile = FileUtils.createFile(newPath);
        try(InputStream inputStream = new BufferedInputStream(new FileInputStream(source));
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newFile))) {
            byte[] readbytes = new byte[4096];
            int buffercount = 0;
            while((buffercount=inputStream.read(readbytes))!=-1) {
                outputStream.write(readbytes,0,buffercount);
            }
        }catch (IOException e) {
            e.printStackTrace();
            throw new IOException("file copy fialed");
        }

    }

    public static File createFile(String filePath) throws IOException {
        if(!StringUtils.hasText(filePath))
            throw new IOException("Paht["+filePath+"] is empty");
        File file = new File(filePath);
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }else {
            file.createNewFile();
        }
        return file;
    }
}
