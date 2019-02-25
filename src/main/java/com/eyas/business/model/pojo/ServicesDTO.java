package com.eyas.business.model.pojo;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/16 21:43
 * @Description:
 */
public class ServicesDTO {
    private Integer serviceid;
    private String title;
    private String imgurl;
    private String servicedetails;
    private String comments;
    private String detailimgs;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getServiceid() {
        return serviceid;
    }

    public void setServiceid(Integer serviceid) {
        this.serviceid = serviceid;
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

    public String getDetailimgs() {
        return detailimgs;
    }

    public void setDetailimgs(String detailimgs) {
        this.detailimgs = detailimgs;
    }
}
