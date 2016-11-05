package com.xescm.ofc.domain.dto.rmc;

/**
 * Created by wanghw on 2016/11/4.
 */
public class RmcCompanyLineVo {
    private String companyName;
    private String lineName;
    private String beginProvinceName;
    private  String beginCityName;
    private  String beginAreaName;
    private  String arriveProvinceName;
    private  String arriveCityName;
    private  String arriveArea;

    private  String frequency;
    private  String contactName;
    private  String companyPhone;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    @Override
    public String toString() {
        return "RmcCompanyLineVo{" +
                "companyName='" + companyName + '\'' +
                ", lineName='" + lineName + '\'' +
                ", beginProvinceName='" + beginProvinceName + '\'' +
                ", beginCityName='" + beginCityName + '\'' +
                ", beginAreaName='" + beginAreaName + '\'' +
                ", arriveProvinceName='" + arriveProvinceName + '\'' +
                ", arriveCityName='" + arriveCityName + '\'' +
                ", arriveArea='" + arriveArea + '\'' +
                ", frequency='" + frequency + '\'' +
                ", contactName='" + contactName + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                '}';
    }
}
