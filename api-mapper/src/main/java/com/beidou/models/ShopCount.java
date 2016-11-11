package com.beidou.models;

import com.beidou.common.dto.BaseDTO;
import java.math.BigDecimal;
import java.util.Date;

public class ShopCount extends BaseDTO {
    private Integer id;

    private String shopName;

    private BigDecimal totalInAmount;

    private BigDecimal totalOutAmount;

    private BigDecimal count;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public BigDecimal getTotalInAmount() {
        return totalInAmount;
    }

    public void setTotalInAmount(BigDecimal totalInAmount) {
        this.totalInAmount = totalInAmount;
    }

    public BigDecimal getTotalOutAmount() {
        return totalOutAmount;
    }

    public void setTotalOutAmount(BigDecimal totalOutAmount) {
        this.totalOutAmount = totalOutAmount;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
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