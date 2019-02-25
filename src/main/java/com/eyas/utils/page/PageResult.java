package com.eyas.utils.page;

import java.util.List;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/17 15:11
 * @Description:
 */
public class PageResult {
    private List<?> data;
    private long count;
    private int code = 0;
    private int totalpages;
    private String msg;

    public PageResult() {

    }

    public PageResult(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }
}
