package com.eyas.business.model.pojo;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/23 10:19
 * @Description:
 */
public class TechnologyDTO {
    private Integer technologyid;
    private String title;
    private String imgurl;
    private String servicedetails;
    private String comments;
    private String detailimgs;

    public Integer getTechnologyid() {
        return technologyid;
    }

    public void setTechnologyid(Integer technologyid) {
        this.technologyid = technologyid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getServicedetails() {
        return servicedetails;
    }

    public void setServicedetails(String servicedetails) {
        this.servicedetails = servicedetails;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDetailimgs() {
        return detailimgs;
    }

    public void setDetailimgs(String detailimgs) {
        this.detailimgs = detailimgs;
    }
}
