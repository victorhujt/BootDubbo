package com.xescm.ofc.model.dto.rmc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wanghw on 2016/11/4.
 */
public class RmcCompanyLineQO {

    private  String beginProvinceName;
    private  String beginCityName;
    private  String beginAreaName;

    private  String arriveProvinceName;
    private  String arriveCityName;
    private  String arriveArea;

    private String companyName;

    /**
     * 发车时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date departureTime;

    /**
     * 城市配送、干线
     */
    private  String lineType;

    public String getBeginProvinceName() {
        return beginProvinceName;
    }

    public void setBeginProvinceName(String beginProvinceName) {
        this.beginProvinceName = beginProvinceName;
    }

    public String getBeginCityName() {
        return beginCityName;
    }

    public void setBeginCityName(String beginCityName) {
        this.beginCityName = beginCityName;
    }

    public String getBeginAreaName() {
        return beginAreaName;
    }

    public void setBeginAreaName(String beginAreaName) {
        this.beginAreaName = beginAreaName;
    }

    public String getArriveProvinceName() {
        return arriveProvinceName;
    }

    public void setArriveProvinceName(String arriveProvinceName) {
        this.arriveProvinceName = arriveProvinceName;
    }

    public String getArriveCityName() {
        return arriveCityName;
    }

    public void setArriveCityName(String arriveCityName) {
        this.arriveCityName = arriveCityName;
    }

    public String getArriveArea() {
        return arriveArea;
    }

    public void setArriveArea(String arriveArea) {
        this.arriveArea = arriveArea;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "RmcCompanyLineQO{" +
                "beginProvinceName='" + beginProvinceName + '\'' +
                ", beginCityName='" + beginCityName + '\'' +
                ", beginAreaName='" + beginAreaName + '\'' +
                ", arriveProvinceName='" + arriveProvinceName + '\'' +
                ", arriveCityName='" + arriveCityName + '\'' +
                ", arriveArea='" + arriveArea + '\'' +
                ", companyName='" + companyName + '\'' +
                ", departureTime=" + departureTime +
                ", lineType='" + lineType + '\'' +
                '}';
    }
}
