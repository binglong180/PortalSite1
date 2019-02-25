package com.eyas.business.model.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Clob;
import java.util.Date;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/11 15:36
 * @Description:
 */
@Entity
@Table(name = "technology")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int technologyid;
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

    public int getTechnologyid() {
        return technologyid;
    }

    public void setTechnologyid(int technologyid) {
        this.technologyid = technologyid;
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
}
