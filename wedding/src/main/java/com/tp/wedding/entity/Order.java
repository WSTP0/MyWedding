package com.tp.wedding.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Order {

    @Id
    @GeneratedValue
    private int id;
    private String orderId;
    private String renterName;//租借人的名字
    private String renterPhone;//租借人的手机号
    private BigDecimal lendPrice;//租金
    private BigDecimal deposit;//定金
    private BigDecimal balance;//尾款+押金
    private String source;//渠道（客源）
    private String useAddress;//使用的场所
    private Date useStartTime;//使用开始日期
    private Date useEndTime;//使用结束日期
    private String remark;//备注
    private Integer isDelete;//是否删除，0正常，1删除
    private String status;//0未付款，1已付定金，2已付尾款押金（全部），3已发货，4已归还，5已完成，6已取消
    private Date orderTime;//下单时间
    private Date payDepositTime;//定金付款时间
    private Date payBalanceTime;//尾款押金付款时间
    private Date predictSendTime;//预计发货的时间
    private Date sendTime;//实际发货时间
    private Date backTime;//归还时间
    private Date finishTime;//完成时间
    private Date cancelTime;//取消时间
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getRenterPhone() {
        return renterPhone;
    }

    public void setRenterPhone(String renterPhone) {
        this.renterPhone = renterPhone;
    }

    public BigDecimal getLendPrice() {
        return lendPrice;
    }

    public void setLendPrice(BigDecimal lendPrice) {
        this.lendPrice = lendPrice;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUseAddress() {
        return useAddress;
    }

    public void setUseAddress(String useAddress) {
        this.useAddress = useAddress;
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    public Date getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayDepositTime() {
        return payDepositTime;
    }

    public void setPayDepositTime(Date payDepositTime) {
        this.payDepositTime = payDepositTime;
    }

    public Date getPayBalanceTime() {
        return payBalanceTime;
    }

    public void setPayBalanceTime(Date payBalanceTime) {
        this.payBalanceTime = payBalanceTime;
    }

    public Date getPredictSendTime() {
        return predictSendTime;
    }

    public void setPredictSendTime(Date predictSendTime) {
        this.predictSendTime = predictSendTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
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
