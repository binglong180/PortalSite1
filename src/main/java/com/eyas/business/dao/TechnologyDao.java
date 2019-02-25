package com.eyas.business.dao;

import com.eyas.business.model.jpa.Technology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/23 10:04
 * @Description:
 */
public interface TechnologyDao extends JpaRepository<Technology,Integer> {
    public Page<Technology> findAll(Specification<Technology> spec, Pageable pageable);

    @Query(value = "select new Technology (technologyid,title,comments,imgurl,dmltime) from Technology where validflag=true",
            countQuery = "select count(technologyid) from Technology where validflag=true")
    public Page<Technology> findTechnology(Pageable pageable);
}
