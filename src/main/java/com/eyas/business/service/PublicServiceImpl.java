package com.eyas.business.service;

import com.eyas.business.dao.TbServiceDao;
import com.eyas.utils.page.PageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/25 09:41
 * @Description:
 */
@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    private TbServiceDao tbServiceDao;

    @Override
    public PageResult queryServices(int page, int limit) {
        Pageable pageable = PageRequest.of(page,limit,new Sort(Sort.Direction.DESC,"dmltime","serviceid"));
        Page tbServicePage = this.tbServiceDao.findTbServices(pageable);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(tbServicePage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        PageResult pageRequest = new PageResult();
        pageRequest.setData(tbServicePage.getContent());
        pageRequest.setCount(tbServicePage.getTotalElements());
        pageRequest.setTotalpages(tbServicePage.getTotalPages());
        return pageRequest;
    }
}
