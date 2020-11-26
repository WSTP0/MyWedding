package com.tp.wedding.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDto {

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

}
