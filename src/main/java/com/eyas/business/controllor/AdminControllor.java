package com.eyas.business.controllor;

import com.eyas.business.model.jpa.TbService;
import com.eyas.business.model.jpa.Technology;
import com.eyas.business.model.pojo.ServicesDTO;
import com.eyas.business.model.pojo.ServicesQueryDTO;
import com.eyas.business.model.pojo.TechnologyDTO;
import com.eyas.business.model.propertiesmap.UploadPathInterface;
import com.eyas.business.service.AdminService;
import com.eyas.utils.FileUtils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/7 10:32
 * @Description:
 */
@Controller
@RequestMapping("/eyas/admin/")
public class AdminControllor {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UploadPathInterface uploadPathInterface;

    @RequestMapping(value = "/pagegoto/{pagename}",method = RequestMethod.GET)
    public String pageGoTo(@PathVariable("pagename")String pagename) {
        return pagename;
    }

    @RequestMapping(value = "/pagegoto/{dlv1}/{pagename}",method = RequestMethod.GET)
    public String pageGoTo(@PathVariable("dlv1") String dlv1, @PathVariable("pagename")String pagename) {
        return dlv1+"/"+pagename;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request,String username,String password) throws InterruptedException {
        String result = adminService.login(request,username,password);
        return result;
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @RequestMapping(value = "/modifypwd",method = RequestMethod.PUT)
    @ResponseBody
    public String modifypwd(String pwdold,String pwdnew,HttpSession session) {
        String result = adminService.modifypwd(pwdold,pwdnew,session);
        return result;
    }


    @RequestMapping(value = "/queryServices",method = RequestMethod.GET)
    @ResponseBody
    public String queryServices(ServicesQueryDTO dto, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        String result = adminService.queryServices(dto,page-1,limit);
        return result;
    }

    @RequestMapping(value = "/queryTechnology",method = RequestMethod.GET)
    @ResponseBody
    public String queryTechnology(ServicesQueryDTO dto, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
        String result = adminService.queryTechnology(dto,page-1,limit);
        return result;
    }


    //上传文件到backup路径
    @RequestMapping(value = "/uploadpic",method = RequestMethod.POST)
    @ResponseBody
    public String uploadpic(MultipartFile file) {
        //先将文件放置在备份文件夹下，等提交表单的时候将临时文件夹下的文件copy到static下
        //String backupPath = this.uploadPathInterface.getBcimgpath();
        String tmpPath = this.uploadPathInterface.getUnsubmitpath();
        String originalName = file.getOriginalFilename(); //文件名
        String suffex=originalName.substring(originalName.lastIndexOf(".")+1);  //文件后缀
        String fileName = new Date().getTime()+"_"+((int)(Math.random()*10000))+"."+suffex; //待保存的文件名
        String file_store_path = tmpPath+fileName;
        String basePath = AdminControllor.class.getResource("/").getPath()+"/static/upload/image/"; //获取server工程的绝对路径
        try {
            File file_store= FileUtils.createFile(file_store_path);
            file.transferTo(file_store);
            //文件备份成功后，直接写入工程路径进行展示
            FileUtils.copyFile(file_store,basePath);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"code\":1}";
        }

        return "{\"code\":0,\"url\":\"/upload/image/"+fileName+"\",\"fileName\":\""+fileName+"\"}";
    }

    @RequestMapping(value = "/addServices",method = RequestMethod.POST)
    @ResponseBody
    public String addServices(ServicesDTO dto) {
        String result = adminService.addServices(dto);
        return "{\"code\":0}";
    }

    @RequestMapping(value = "/deleteService/{serviceid}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteService(@PathVariable("serviceid") int serviceid) {
        adminService.deleteService(serviceid);
        return "{\"code\":0}";
    }

    @RequestMapping(value = "/deleteTechnology/{technologyid}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteTechnology(@PathVariable("technologyid") int technologyid) {
        adminService.deleteTechnology(technologyid);
        return "{\"code\":0}";
    }


    @RequestMapping(value = "/editservice/{serviceid}",method = RequestMethod.GET)
    public String editService(@PathVariable("serviceid") int serviceid, Model model) {
        TbService tbService = adminService.getServiceById(serviceid);
        model.addAttribute("tbService",tbService);
        return "/addservice";
    }

    @RequestMapping(value = "/editTechnology/{technologyid}",method = RequestMethod.GET)
    public String editTechnology(@PathVariable("technologyid") int technologyid, Model model) {
        Technology technology = adminService.getTechnologyById(technologyid);
        model.addAttribute("technology",technology);
        return "/addtechnology";
    }

    @RequestMapping(value = "/addTechnology",method = RequestMethod.POST)
    @ResponseBody
    public String addTechnology(TechnologyDTO dto) {
        String result = adminService.addTechnology(dto);
        return "{\"code\":0}";
    }
}
