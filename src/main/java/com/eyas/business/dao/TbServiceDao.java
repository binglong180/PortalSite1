package com.eyas.business.dao;

import com.eyas.business.model.jpa.TbService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/11 17:57
 * @Description:
 */
public interface TbServiceDao extends JpaRepository<TbService,Integer> {
    public Page<TbService> findAll(Specification<TbService> spec, Pageable pageable);
    @Query(value = "select new TbService(serviceid,title,comments,imgurl,dmltime) from TbService where validflag=true",
    countQuery = "select count(serviceid) from TbService where validflag=true")
    public Page<TbService> findTbServices(Pageable pageable);
}
