package com.eyas.business.service;

import com.eyas.business.model.jpa.TbService;
import com.eyas.business.model.jpa.Technology;
import com.eyas.business.model.pojo.ServicesDTO;
import com.eyas.business.model.pojo.ServicesQueryDTO;
import com.eyas.business.model.pojo.TechnologyDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/7 19:30
 * @Description:
 */
public interface AdminService {
    /**
     * 后台管理登录
     * @param request
     * @param username
     * @param password
     * @return
     */
    public String login(HttpServletRequest request, String username, String password);

    /**
     * 修改密码
     * @param pwdold
     * @param pwdnew
     * @param session
     * @return
     */
    public String modifypwd(String pwdold, String pwdnew, HttpSession session);

    /**
     * 分页查询服务项目信息
     * @param dto
     * @param page
     * @param limit
     * @return
     */
    public String queryServices(ServicesQueryDTO dto, int page, int limit);

    /**
     * save or update tbservice
     * @param dto
     * @return
     */
    public String addServices(ServicesDTO dto);

    /**
     * 将其置为隐藏状态
     * @param serviceid
     */
    public void deleteService(int serviceid);

    /**
     * 根据serviceid查询TbService
     * @param serviceid
     * @return
     */
    public TbService getServiceById(int serviceid);

    /**
     * 查询技术应用列表
     * @param dto
     * @param page
     * @param limit
     * @return
     */
    public String queryTechnology(ServicesQueryDTO dto, int page, int limit);

    /**
     * 修改技术应该为无效状态
     * @param technologyid
     */
    public void deleteTechnology(int technologyid);

    /**
     * 根据ID获取技术应用
     * @param technologyid
     * @return
     */
    public Technology getTechnologyById(int technologyid);

    /**
     * save or update 技术应用
     * @param dto
     * @return
     */
    public String addTechnology(TechnologyDTO dto);
}
