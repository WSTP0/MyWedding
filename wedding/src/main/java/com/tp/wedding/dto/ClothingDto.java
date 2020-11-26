package com.tp.wedding.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ClothingDto {

    private int id;
    private String clothingId;//编号
    private String clothingName;//名称
    private String category;//品类
    private String style;//款式
    private String size;//尺寸
    private String fabric;//面料
    private String source;//货源
    private String color;//颜色
    private String setMeal;//适用套餐
    private BigDecimal lendPrice;//出租价
    private BigDecimal buyPrice;//进货价
    private BigDecimal retailPrice;//零售价
    private String lendCount;//出租次数
    private Date lastLendDate;//上一次出租的时间（开始）
    private String lastLendOrderId;//上一次出租的订单号
    private String status;//状态，0正常，1出租中
    private Integer isDelete;//是否删除，0正常，1是删除
    private Date buyDate;//购买日期
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClothingId() {
        return clothingId;
    }

    public void setClothingId(String clothingId) {
        this.clothingId = clothingId;
    }

    public String getClothingName() {
        return clothingName;
    }

    public void setClothingName(String clothingName) {
        this.clothingName = clothingName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSetMeal() {
        return setMeal;
    }

    public void setSetMeal(String setMeal) {
        this.setMeal = setMeal;
    }

    public BigDecimal getLendPrice() {
        return lendPrice;
    }

    public void setLendPrice(BigDecimal lendPrice) {
        this.lendPrice = lendPrice;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getLendCount() {
        return lendCount;
    }

    public void setLendCount(String lendCount) {
        this.lendCount = lendCount;
    }

    public Date getLastLendDate() {
        return lastLendDate;
    }

    public void setLastLendDate(Date lastLendDate) {
        this.lastLendDate = lastLendDate;
    }

    public String getLastLendOrderId() {
        return lastLendOrderId;
    }

    public void setLastLendOrderId(String lastLendOrderId) {
        this.lastLendOrderId = lastLendOrderId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
