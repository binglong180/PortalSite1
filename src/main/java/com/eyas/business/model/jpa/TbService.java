package com.eyas.business.model.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/11 15:31
 * @Description:
 */
@Entity
@Table(name = "tbservice")
public class TbService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceid;
    @Column
    private String title;
    @Column
    private String comments;
    @Column
    private String imgurl;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dmltime;
    @Lob
    @Column
    @Basic(fetch = FetchType.LAZY)
    private String details;
    @Column
    private boolean validflag;

    public int getServiceid() {
        return serviceid;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Date getDmltime() {
        return dmltime;
    }

    public void setDmltime(Date dmltime) {
        this.dmltime = dmltime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isValidflag() {
        return validflag;
    }

    public void setValidflag(boolean validflag) {
        this.validflag = validflag;
    }

    public TbService(String title, String comments, String imgurl, Date dmltime) {
        this.title = title;
        this.comments = comments;
        this.imgurl = imgurl;
        this.dmltime = dmltime;
    }

    public TbService(int serviceid,String title, String comments, String imgurl, Date dmltime) {
        this.serviceid = serviceid;
        this.title = title;
        this.comments = comments;
        this.imgurl = imgurl;
        this.dmltime = dmltime;
    }

    public TbService() {

    }

    public TbService(String title, String comments, String imgurl, Date dmltime, String details, boolean validflag) {
        this.title = title;
        this.comments = comments;
        this.imgurl = imgurl;
        this.dmltime = dmltime;
        this.details = details;
        this.validflag = validflag;
    }
}
