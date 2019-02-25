package com.eyas.business.service;

import com.eyas.business.config.springmvc.interceptor.AdminLoginInterceptor;
import com.eyas.business.controllor.AdminControllor;
import com.eyas.business.dao.AdminUserDao;
import com.eyas.business.dao.TbServiceDao;
import com.eyas.business.dao.TechnologyDao;
import com.eyas.business.model.jpa.AdminUser;
import com.eyas.business.model.jpa.TbService;
import com.eyas.business.model.jpa.Technology;
import com.eyas.business.model.pojo.ServicesDTO;
import com.eyas.business.model.pojo.ServicesQueryDTO;
import com.eyas.business.model.pojo.TechnologyDTO;
import com.eyas.business.model.propertiesmap.UploadPathInterface;
import com.eyas.utils.FileUtils.FileUtils;
import com.eyas.utils.page.PageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/7 19:30
 * @Description:
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminUserDao adminUserDao;

    @Autowired
    private TbServiceDao tbServiceDao;

    @Autowired
    private TechnologyDao technologyDao;

    @Autowired
    private UploadPathInterface uploadPathInterface;

    @Override
    public String login(HttpServletRequest request, String username, String password) {
        AdminUser adminUser = adminUserDao.getAdminUserByLoginnameAndPasswordAndValidflag(username,password,true);
        if(adminUser==null) {
            return "{\"success\":false}";
        }else {
            request.getSession().setAttribute(AdminLoginInterceptor.LONGIN_SESSION_NAME,adminUser);
        }
        return "{\"success\":true}";
    }

    @Override
    public String modifypwd(String pwdold, String pwdnew, HttpSession session) {
        AdminUser adminUser = (AdminUser)session.getAttribute(AdminLoginInterceptor.LONGIN_SESSION_NAME);
        if(pwdold.equalsIgnoreCase(adminUser.getPassword())) {
            adminUser.setPassword(pwdnew);
            adminUserDao.save(adminUser);
        }else {
            return "{\"success\":false,\"errormsg\":\"旧密码错误\"}";
        }
        return "{\"success\":true}";
    }

    @Override
    public String queryServices(ServicesQueryDTO dto, int page, int limit) {
        Pageable pageable = PageRequest.of(page,limit,new Sort(Sort.Direction.DESC,"dmltime","serviceid"));
        Specification<TbService> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if(StringUtils.hasText(dto.getTitle())) {
                    Predicate liketitle = criteriaBuilder.like(root.get("title"),"%"+dto.getTitle()+"%");
                    predicates.add(liketitle);
                }
                if(dto.getStarttime()!=null) {
                    Predicate starttime = criteriaBuilder.greaterThanOrEqualTo(root.get("dmltime"),dto.getStarttime());
                    predicates.add(starttime);
                }
                if(dto.getEndtime()!=null) {
                    Predicate starttime = criteriaBuilder.lessThanOrEqualTo(root.get("dmltime"),dto.getEndtime());
                    predicates.add(starttime);
                }

                Predicate starttime = criteriaBuilder.equal(root.get("validflag"),dto.isValidflag());
                predicates.add(starttime);

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<TbService> pages = tbServiceDao.findAll(specification,pageable);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = "";
        PageResult pageResult = new PageResult();
        pageResult.setData(pages.getContent());
        pageResult.setCount(pages.getTotalElements());
        try {
            result = objectMapper.writeValueAsString(pageResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            pageResult.setCode(1);
            pageResult.setMsg("Json映射出错");
        }
        return result;
    }

    @Override
    public String addServices(ServicesDTO dto) {
        TbService tbService = null;
        if(dto.getServiceid()!=null&&dto.getServiceid()>0) {
            tbService = tbServiceDao.getOne(dto.getServiceid());
        }
        if(tbService==null)
            tbService = new TbService();
        tbService.setTitle(dto.getTitle());
        tbService.setImgurl(dto.getImgurl());
        tbService.setDmltime(new Date());
        tbService.setComments(dto.getComments());
        tbService.setDetails(dto.getServicedetails());
        tbServiceDao.save(tbService);


        String backupPath = this.uploadPathInterface.getBcimgpath();
        String tmppath = this.uploadPathInterface.getUnsubmitpath();
        //开始处理image
        if(StringUtils.hasText(dto.getImgurl())) {
            String FileName = dto.getImgurl().substring(dto.getImgurl().lastIndexOf("/"));
            File tmpfile = new File(tmppath+FileName);
            try {
                FileUtils.copyFile(tmpfile,backupPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("文件备份失败，请重新提交");
            }
        }

        if(StringUtils.hasText(dto.getDetailimgs())) {
            String[] detailimgs = dto.getDetailimgs().split(",");
            for(int i=0;i<detailimgs.length;i++) {
                String fileName = detailimgs[i];
                if(StringUtils.hasText(fileName)) {
                    System.out.println(tmppath+fileName);
                    File tmpfile = new File(tmppath+fileName);
                    try {
                        FileUtils.copyFile(tmpfile,backupPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException("文件备份失败，请重新提交");
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void deleteService(int serviceid) {
        TbService tbService = tbServiceDao.getOne(serviceid);
        tbService.setValidflag(!tbService.isValidflag());
        tbServiceDao.save(tbService);
    }

    @Override
    public TbService getServiceById(int serviceid) {
        TbService tbService = tbServiceDao.getOne(serviceid);
        return tbService;
    }

    @Override
    public String queryTechnology(ServicesQueryDTO dto, int page, int limit) {
        Pageable pageable = PageRequest.of(page,limit,new Sort(Sort.Direction.DESC,"dmltime","technologyid"));
        Specification<Technology> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if(StringUtils.hasText(dto.getTitle())) {
                    Predicate liketitle = criteriaBuilder.like(root.get("title"),"%"+dto.getTitle()+"%");
                    predicates.add(liketitle);
                }
                if(dto.getStarttime()!=null) {
                    Predicate starttime = criteriaBuilder.greaterThanOrEqualTo(root.get("dmltime"),dto.getStarttime());
                    predicates.add(starttime);
                }
                if(dto.getEndtime()!=null) {
                    Predicate starttime = criteriaBuilder.lessThanOrEqualTo(root.get("dmltime"),dto.getEndtime());
                    predicates.add(starttime);
                }

                Predicate starttime = criteriaBuilder.equal(root.get("validflag"),dto.isValidflag());
                predicates.add(starttime);

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<Technology> pages = technologyDao.findAll(specification,pageable);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = "";
        PageResult pageResult = new PageResult();
        pageResult.setData(pages.getContent());
        pageResult.setCount(pages.getTotalElements());
        try {
            result = objectMapper.writeValueAsString(pageResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            pageResult.setCode(1);
            pageResult.setMsg("Json映射出错");
        }
        return result;
    }

    @Override
    public void deleteTechnology(int technologyid) {
        Technology technology = technologyDao.getOne(technologyid);
        technology.setValidflag(!technology.isValidflag());
        technologyDao.save(technology);
    }

    @Override
    public Technology getTechnologyById(int technologyid) {
        Technology technology = technologyDao.getOne(technologyid);
        return technology;
    }

    @Override
    public String addTechnology(TechnologyDTO dto) {
        Technology technology = null;
        if(dto.getTechnologyid()!=null&&dto.getTechnologyid()>0) {
            technology = technologyDao.getOne(dto.getTechnologyid());
        }
        if(technology==null)
            technology = new Technology();
        technology.setTitle(dto.getTitle());
        technology.setImgurl(dto.getImgurl());
        technology.setDmltime(new Date());
        technology.setComments(dto.getComments());
        technology.setDetails(dto.getServicedetails());
        technologyDao.save(technology);

        String backupPath = this.uploadPathInterface.getBcimgpath();
        String tmppath = this.uploadPathInterface.getUnsubmitpath();
        //开始处理image
        if(StringUtils.hasText(dto.getImgurl())) {
            String FileName = dto.getImgurl().substring(dto.getImgurl().lastIndexOf("/"));
            File tmpfile = new File(tmppath+FileName);
            try {
                FileUtils.copyFile(tmpfile,backupPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("文件备份失败，请重新提交");
            }
        }

        if(StringUtils.hasText(dto.getDetailimgs())) {
            String[] detailimgs = dto.getDetailimgs().split(",");
            for(int i=0;i<detailimgs.length;i++) {
                String fileName = detailimgs[i];
                if(StringUtils.hasText(fileName)) {
                    File tmpfile = new File(tmppath+fileName);
                    try {
                        FileUtils.copyFile(tmpfile,backupPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException("文件备份失败，请重新提交");
                    }
                }
            }
        }
        return null;
    }


}
