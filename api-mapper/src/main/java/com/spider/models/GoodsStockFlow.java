package com.spider.models;

import com.beidou.common.dto.BaseDTO;
import java.math.BigDecimal;
import java.util.Date;

public class GoodsStockFlow extends BaseDTO {
    private Integer id;

    private String shopName;

    private String wldOrderSn;

    private String goodsName;

    private String goodsNoPlatform;

    private String goodsNoWlb;

    private String warehouse;

    private Date operateTime;

    private String operateType;

    private String stockType;

    private String operateNumber;

    private String balanceNumber;

    private String erpOrderSn;

    private String outOrderSn;

    private Date addTime;

    private BigDecimal totalAmount;

    private BigDecimal orderPaid;

    private BigDecimal totalPost;

    private BigDecimal orderPost;

    private BigDecimal totalTax;

    private BigDecimal orderTax;

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

    public String getWldOrderSn() {
        return wldOrderSn;
    }

    public void setWldOrderSn(String wldOrderSn) {
        this.wldOrderSn = wldOrderSn == null ? null : wldOrderSn.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsNoPlatform() {
        return goodsNoPlatform;
    }

    public void setGoodsNoPlatform(String goodsNoPlatform) {
        this.goodsNoPlatform = goodsNoPlatform == null ? null : goodsNoPlatform.trim();
    }

    public String getGoodsNoWlb() {
        return goodsNoWlb;
    }

    public void setGoodsNoWlb(String goodsNoWlb) {
        this.goodsNoWlb = goodsNoWlb == null ? null : goodsNoWlb.trim();
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse == null ? null : warehouse.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType == null ? null : stockType.trim();
    }

    public String getOperateNumber() {
        return operateNumber;
    }

    public void setOperateNumber(String operateNumber) {
        this.operateNumber = operateNumber == null ? null : operateNumber.trim();
    }

    public String getBalanceNumber() {
        return balanceNumber;
    }

    public void setBalanceNumber(String balanceNumber) {
        this.balanceNumber = balanceNumber == null ? null : balanceNumber.trim();
    }

    public String getErpOrderSn() {
        return erpOrderSn;
    }

    public void setErpOrderSn(String erpOrderSn) {
        this.erpOrderSn = erpOrderSn == null ? null : erpOrderSn.trim();
    }

    public String getOutOrderSn() {
        return outOrderSn;
    }

    public void setOutOrderSn(String outOrderSn) {
        this.outOrderSn = outOrderSn == null ? null : outOrderSn.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getOrderPaid() {
        return orderPaid;
    }

    public void setOrderPaid(BigDecimal orderPaid) {
        this.orderPaid = orderPaid;
    }

    public BigDecimal getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(BigDecimal totalPost) {
        this.totalPost = totalPost;
    }

    public BigDecimal getOrderPost() {
        return orderPost;
    }

    public void setOrderPost(BigDecimal orderPost) {
        this.orderPost = orderPost;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getOrderTax() {
        return orderTax;
    }

    public void setOrderTax(BigDecimal orderTax) {
        this.orderTax = orderTax;
    }
}