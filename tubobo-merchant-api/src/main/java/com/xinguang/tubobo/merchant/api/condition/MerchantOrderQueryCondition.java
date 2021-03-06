package com.xinguang.tubobo.merchant.api.condition;

import java.io.Serializable;
import java.util.Date;

public class MerchantOrderQueryCondition implements Serializable{
    private String orderType;           //订单类型
    private Long providerId;
    private Date orderTimeEnd;	        //结束创建时间
    private Date orderTimeStart;	    //开始创建时间
    private String orderNo;	            //订单编号
    private String orderStatus;	        //状态
    private String unsettledStatus;     //驿站订单 未妥投状态  默认""  0:未妥投处理中 1：未妥投已处理
    private String receiverPhone;	    //收货人电话
    private String riderId;	            //骑手ID
    private String riderName;	        //骑手姓名
    private String shopId;	            //商家ID
    private String shopName;	        //商家名称
    private String expectFinishTimeSort;//按预计送达时间排序	asc按预计送达时间顺序；desc按预计送达时间倒序；空值按创建时间倒序排列
    private String orderTimeSort;
    private Integer pageNo;
    private Integer pageSize;

    public String getUnsettledStatus() {
        return unsettledStatus;
    }

    public void setUnsettledStatus(String unsettledStatus) {
        this.unsettledStatus = unsettledStatus;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getExpectFinishTimeSort() {
        return expectFinishTimeSort;
    }

    public void setExpectFinishTimeSort(String expectFinishTimeSort) {
        this.expectFinishTimeSort = expectFinishTimeSort;
    }

    public Date getOrderTimeEnd() {
        return orderTimeEnd;
    }

    public void setOrderTimeEnd(Date orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }

    public Date getOrderTimeStart() {
        return orderTimeStart;
    }

    public void setOrderTimeStart(Date orderTimeStart) {
        this.orderTimeStart = orderTimeStart;
    }

    public String getOrderTimeSort() {
        return orderTimeSort;
    }

    public void setOrderTimeSort(String orderTimeSort) {
        this.orderTimeSort = orderTimeSort;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
