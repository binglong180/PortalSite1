package com.eyas.business.service;

import com.eyas.business.dao.TbServiceDao;
import com.eyas.business.dao.TechnologyDao;
import com.eyas.business.model.jpa.TbService;
import com.eyas.business.model.jpa.Technology;
import com.eyas.business.model.pojo.TbServiceDTO;
import com.eyas.business.model.pojo.TechnologyD;
import com.eyas.utils.page.PageResult;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private TechnologyDao technologyDao;

    @Override
    public PageResult queryServices(int page, int limit) {
        Pageable pageable = PageRequest.of(page,limit,new Sort(Sort.Direction.DESC,"dmltime","serviceid"));
        Page tbServicePage = this.tbServiceDao.findTbServices(pageable);
        PageResult pageRequest = new PageResult();
        pageRequest.setData(tbServicePage.getContent());
        pageRequest.setCount(tbServicePage.getTotalElements());
        pageRequest.setTotalpages(tbServicePage.getTotalPages());
        return pageRequest;
    }

    @Override
    public TbService getServiceDetails(int serviceid) {
        TbService tbService = tbServiceDao.getOne(serviceid);
        TbServiceDTO tbServiceDTO = new TbServiceDTO();
        BeanUtils.copyProperties(tbService,tbServiceDTO);
        return tbServiceDTO;
    }

    @Override
    public PageResult queryTechnologys(int page, int limit) {
        Pageable pageable = PageRequest.of(page,limit,new Sort(Sort.Direction.DESC,"dmltime","technologyid"));
        Page technologyPage = this.technologyDao.findTechnology(pageable);
        PageResult pageRequest = new PageResult();
        pageRequest.setData(technologyPage.getContent());
        pageRequest.setCount(technologyPage.getTotalElements());
        pageRequest.setTotalpages(technologyPage.getTotalPages());
        return pageRequest;
    }

    @Override
    public Technology getTechnologysDetails(int technologyid) {
        Technology technology = technologyDao.getOne(technologyid);
        TechnologyD technologyD = new TechnologyD();
        BeanUtils.copyProperties(technology,technologyD);
        return technologyD;
    }
}
