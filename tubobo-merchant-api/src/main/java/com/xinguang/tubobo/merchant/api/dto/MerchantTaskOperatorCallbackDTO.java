package com.xinguang.tubobo.merchant.api.dto;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/30.
 */
public class MerchantTaskOperatorCallbackDTO implements Serializable {
    private String taskNo;
    private Double expiredMinute;
    private Double expiredCompensation;
    private Date operateTime;

    public MerchantTaskOperatorCallbackDTO(){}

    public MerchantTaskOperatorCallbackDTO(String taskNo, Date operateTime, Double expiredMinute, Double expiredCompensation){
        this.taskNo = taskNo;
        this.expiredMinute = expiredMinute;
        this.expiredCompensation = expiredCompensation;
        this.operateTime = operateTime;
    }

    public Double getExpiredMinute() {
        return expiredMinute;
    }

    public void setExpiredMinute(Double expiredMinute) {
        this.expiredMinute = expiredMinute;
    }

    public Double getExpiredCompensation() {
        return expiredCompensation;
    }

    public void setExpiredCompensation(Double expiredCompensation) {
        this.expiredCompensation = expiredCompensation;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    @Override
    public String toString() {
        return "MerchantTaskOperatorCallbackDTO{" +
                "taskNo='" + taskNo + '\'' +
                ", operateTime=" + operateTime +
                '}';
    }
}
