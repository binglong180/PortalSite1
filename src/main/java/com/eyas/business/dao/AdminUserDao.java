package com.eyas.business.dao;

import com.eyas.business.model.jpa.AdminUser;
import org.springframework.data.repository.CrudRepository;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/7 19:38
 * @Description:
 */
public interface AdminUserDao extends CrudRepository<AdminUser,Integer> {
    public AdminUser getAdminUserByLoginnameAndPasswordAndValidflag(String loginname,String password,boolean validflag);
}
