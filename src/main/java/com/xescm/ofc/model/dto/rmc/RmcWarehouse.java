package com.xescm.ofc.model.dto.rmc;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tbl_rmc_warehouse")
public class RmcWarehouse {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 公司ID
     */
    @Column(name="company_code")
    private String companyCode;

    /**
     * 性质：1.存储仓（DC）2.分拣仓（TC）
     */
    @Column(name="nature")
    private String nature;

    /**
     * 属性：1.自有库2：外租库
     */
    @Column(name="property")
    private Integer property;

    /**
     * 仓库全称
     */
    @Column(name = "warehouse_name")
    private String warehouseName;

    /**
     * 仓库编码
     */
    @Column(name = "warehouse_code")
    private String warehouseCode;

    /**
     * 省
     */
    @Column(name="province")
    private String province;

    @Column(name="province_code")
    private String provinceCode;

    /**
     * 市
     */
    @Column(name = "city")
    private String city;

    @Column(name="city_code")
    private String cityCode;

    /**
     * 区县街
     */
    @Column(name="area")
    private String area;

    @Column(name="area_code")
    private String areaCode;

    @Column(name = "street")
    private String street;

    @Column(name="street_code")
    private String streetCode;

    /**
     * 详细地址
     */
    @Column(name = "detail_address")
    private String detailAddress;

    /**
     * 类型：1.平推库，2.货架库，3.自动化库
     */
    @Column(name = "type")
    private String type;

    /**
     * 特点：1.保税仓 2.商超仓 3.监管仓 4.农产品仓 5.集货分拣仓
     */
    @Column(name="feature")
    private String feature;

    /**
     * 建筑面积：单位平米
     */
    @Column(name = "architecture_area")
    private String architectureArea;

    /**
     * 使用面积：单位平米
     */
    @Column(name = "utilization_area")
    private String utilizationArea;

    /**
     * 总储存量：单位万吨
     */
    @Column(name="reserves")
    private String reserves;

    /**
     * 库温
     */
    @Column(name="store_temperature")
    private String storeTemperature;
    /**
     * 容量：平米
     */
    @Column(name = "capacity_square_metre")
    private String capacitySquareMetre;

    /**
     * 容量：立方
     */
    @Column(name = "capacity_cube")
    private String capacityCube;

    /**
     * 容量：托盘
     */
    @Column(name = "capacity_tray")
    private String capacityTray;

    /**
     * 出库时效:小时
     */
    @Column(name = "delivery_time")
    private String deliveryTime;

    /**
     * 分拣面积：平米
     */
    @Column(name = "sorting_area")
    private String sortingArea;

    /**
     * 分拣时间:小时
     */
    @Column(name = "sorting_time")
    private String sortingTime;


    /**
     * 停车场：平米
     */
    @Column(name = "square_metre")
    private String squareMetre;

    /**
     * 车位
     */
    @Column(name = "parking_lot")
    private String parkingLot;

    /**
     * 叉车：单位台
     */
    @Column(name = "forklift")
    private String forklift;

    /**
     * 月台数
     */
    @Column(name = "platform")
    private String platform;

    /**
     * 创建人
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 最近操作人
     */
    @Column(name = "last_operator")
    private String lastOperator;

    /**
     * 最后操作人ID
     */
    @Column(name = "last_operator_id")
    private String lastOperatorId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     *
     * 删除状态
     */
    @Column(name="yn")
    private Integer yn;

    /**
     * 联系人
     */
    @Column(name = "contact_name")
    private String  contactName;

    /**
     * 电话
     */
    @Column(name="phone")
    private String phone;

    /**
     * 状态
     */
    @Column(name="state")
    private Integer state;

    /**
     * 基地编码
     */
    @Column(name = "base_code")
    private String baseCode;
    /**
     * 基地名称
     */
    @Column(name="base_name")
    private String baseName;

    public String getId() {
        return id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getNature() {
        return nature;
    }

    public Integer getProperty() {
        return property;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public String getProvince() {
        return province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getCity() {
        return city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getArea() {
        return area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getType() {
        return type;
    }

    public String getFeature() {
        return feature;
    }

    public String getArchitectureArea() {
        return architectureArea;
    }

    public String getUtilizationArea() {
        return utilizationArea;
    }

    public String getReserves() {
        return reserves;
    }

    public String getStoreTemperature() {
        return storeTemperature;
    }

    public String getCapacitySquareMetre() {
        return capacitySquareMetre;
    }

    public String getCapacityCube() {
        return capacityCube;
    }

    public String getCapacityTray() {
        return capacityTray;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getSortingArea() {
        return sortingArea;
    }

    public String getSortingTime() {
        return sortingTime;
    }

    public String getSquareMetre() {
        return squareMetre;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public String getForklift() {
        return forklift;
    }

    public String getPlatform() {
        return platform;
    }

    public String getCreator() {
        return creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getLastOperator() {
        return lastOperator;
    }

    public String getLastOperatorId() {
        return lastOperatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getState() {
        return state;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public void setProperty(Integer property) {
        this.property = property;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setArchitectureArea(String architectureArea) {
        this.architectureArea = architectureArea;
    }

    public void setUtilizationArea(String utilizationArea) {
        this.utilizationArea = utilizationArea;
    }

    public void setReserves(String reserves) {
        this.reserves = reserves;
    }

    public void setStoreTemperature(String storeTemperature) {
        this.storeTemperature = storeTemperature;
    }

    public void setCapacitySquareMetre(String capacitySquareMetre) {
        this.capacitySquareMetre = capacitySquareMetre;
    }

    public void setCapacityCube(String capacityCube) {
        this.capacityCube = capacityCube;
    }

    public void setCapacityTray(String capacityTray) {
        this.capacityTray = capacityTray;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setSortingArea(String sortingArea) {
        this.sortingArea = sortingArea;
    }

    public void setSortingTime(String sortingTime) {
        this.sortingTime = sortingTime;
    }

    public void setSquareMetre(String squareMetre) {
        this.squareMetre = squareMetre;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void setForklift(String forklift) {
        this.forklift = forklift;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    public void setLastOperatorId(String lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }
}