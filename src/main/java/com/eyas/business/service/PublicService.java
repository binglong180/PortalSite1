package com.eyas.business.service;

import com.eyas.utils.page.PageResult;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/25 09:40
 * @Description:
 */
public interface PublicService {
    public PageResult queryServices(int page, int limit);
}
