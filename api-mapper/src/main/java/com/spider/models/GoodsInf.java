package com.spider.models;

import com.beidou.common.dto.BaseDTO;
import java.util.Date;

public class GoodsInf extends BaseDTO {
    private Integer id;

    private String shopGoodName;

    private String goodNo;

    private String tbGoodId;

    private String tbSkuId;

    private String wlbGoodName;

    private String wlbGoodId;

    private String shopName;

    private String isSuit;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopGoodName() {
        return shopGoodName;
    }

    public void setShopGoodName(String shopGoodName) {
        this.shopGoodName = shopGoodName == null ? null : shopGoodName.trim();
    }

    public String getGoodNo() {
        return goodNo;
    }

    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo == null ? null : goodNo.trim();
    }

    public String getTbGoodId() {
        return tbGoodId;
    }

    public void setTbGoodId(String tbGoodId) {
        this.tbGoodId = tbGoodId == null ? null : tbGoodId.trim();
    }

    public String getTbSkuId() {
        return tbSkuId;
    }

    public void setTbSkuId(String tbSkuId) {
        this.tbSkuId = tbSkuId == null ? null : tbSkuId.trim();
    }

    public String getWlbGoodName() {
        return wlbGoodName;
    }

    public void setWlbGoodName(String wlbGoodName) {
        this.wlbGoodName = wlbGoodName == null ? null : wlbGoodName.trim();
    }

    public String getWlbGoodId() {
        return wlbGoodId;
    }

    public void setWlbGoodId(String wlbGoodId) {
        this.wlbGoodId = wlbGoodId == null ? null : wlbGoodId.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getIsSuit() {
        return isSuit;
    }

    public void setIsSuit(String isSuit) {
        this.isSuit = isSuit == null ? null : isSuit.trim();
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