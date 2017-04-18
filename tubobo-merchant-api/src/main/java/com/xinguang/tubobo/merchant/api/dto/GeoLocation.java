package com.xinguang.tubobo.merchant.api.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/15.
 */
public class GeoLocation implements Serializable{
    private String userId;
    private Double latitude;
    private Double longitude;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GeoLocation: ")
                .append("userId : ").append(userId)
                .append(", latitude: ").append(latitude)
                .append(", longitude: ").append(longitude);
        return super.toString();
    }
}