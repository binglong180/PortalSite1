package com.eyas.business.controllor;

import com.eyas.business.model.jpa.TbService;
import com.eyas.business.service.PublicService;
import com.eyas.utils.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/25 09:38
 * @Description:
 */
@RestController
@RequestMapping("/eyas/public/")
public class PublicControllor {

    @Autowired
    private PublicService publicService;

    @GetMapping("/queryServices")
    public PageResult queryServices(int page,int limit) {
        PageResult pageResult = publicService.queryServices(page-1,limit);
        return pageResult;
    }

    @GetMapping("/getServiceDetails")
    public TbService getServiceDetails(int serviceid) {
        TbService tbService = publicService.getServiceDetails(serviceid);
        return tbService;
    }
}
