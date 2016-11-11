package com.spider.models;

import com.beidou.common.dto.BaseDTO;
import java.util.Date;

public class GoodsInfSuit extends BaseDTO {
    private Integer id;

    private String wlbGoodId;

    private String wlbGoodNo;

    private String wlbGoodName;

    private Integer num;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWlbGoodId() {
        return wlbGoodId;
    }

    public void setWlbGoodId(String wlbGoodId) {
        this.wlbGoodId = wlbGoodId == null ? null : wlbGoodId.trim();
    }

    public String getWlbGoodNo() {
        return wlbGoodNo;
    }

    public void setWlbGoodNo(String wlbGoodNo) {
        this.wlbGoodNo = wlbGoodNo == null ? null : wlbGoodNo.trim();
    }

    public String getWlbGoodName() {
        return wlbGoodName;
    }

    public void setWlbGoodName(String wlbGoodName) {
        this.wlbGoodName = wlbGoodName == null ? null : wlbGoodName.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}