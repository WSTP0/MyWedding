package com.tp.wedding.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PackageInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String packageCode;//套餐Code
    private String clothingIds;//包含的衣服
    private Integer LendCount;//租赁次数
    private Integer isDelete;//是否删除，0正常，1删除
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getClothingIds() {
        return clothingIds;
    }

    public void setClothingIds(String clothingIds) {
        this.clothingIds = clothingIds;
    }

    public Integer getLendCount() {
        return LendCount;
    }

    public void setLendCount(Integer lendCount) {
        LendCount = lendCount;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
