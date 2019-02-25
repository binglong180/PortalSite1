package com.eyas.business.service;

import com.eyas.business.model.jpa.TbService;
import com.eyas.business.model.jpa.Technology;
import com.eyas.utils.page.PageResult;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/25 09:40
 * @Description:
 */
public interface PublicService {
    public PageResult queryServices(int page, int limit);

    public TbService getServiceDetails(int serviceid);

    public PageResult queryTechnologys(int page, int limit);

    public Technology getTechnologysDetails(int technologyid);
}
