package com.xescm.ofc.model.dto.rmc;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tbl_rmc_warehouse")
public class RmcWarehouse {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    private String id;

    /**
     * 仓库全称
     */
    @Column(name = "warehouse_name")
    private String warehouseName;

    /**
     * 仓库简称
     */
    @Column(name = "warehouse_abbreviation")
    private String warehouseAbbreviation;

    /**
     * 仓库编码
     */
    @Column(name = "warehouse_code")
    private String warehouseCode;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区县街
     */
    private String area;

    /**
     * 详细地址
     */
    @Column(name = "detailed_address")
    private String detailedAddress;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 维度
     */
    private String dimension;

    /**
     * 类型：1.平推库，2.货架库，3.自动化库
     */
    private String type;

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
    private String reserves;

    /**
     * 属性：1.保税仓 2.商超仓 3.监管仓 4.农产品仓 5.集货分拣仓
     */
    private String attribute;

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
    private String forklift;

    /**
     * 月台数
     */
    private String platform;

    /**
     * 月台编码
     */
    @Column(name = "platform_code")
    private String platformCode;

    /**
     * 1.发货月台 2.收货月台
     */
    @Column(name = "platform_type")
    private String platformType;

    /**
     * 1.常温（0以上）2.冷藏（0~5） 3.冷冻（-15~-18）4.急冻（-23）5.深冷（-27~-35或更低）
     */
    @Column(name = "temperature_type")
    private String temperatureType;

    /**
     * 仓库位置图
     */
    private String imgurl;

    /**
     * 仓库库位表
     */
    private String storage;

    /**
     * 常温储存量:单位吨
     */
    @Column(name = "normal_temperature")
    private String normalTemperature;

    /**
     * 冷藏储存量:单位吨
     */
    @Column(name = "cold_storage")
    private String coldStorage;

    /**
     * 冷冻储存量:单位吨
     */
    private String freezing;

    /**
     * 急冻存储量:单位吨
     */
    private String frozen;

    /**
     * 深冷储存量:单位吨
     */
    private String cryogenic;

    /**
     * 创建人
     */
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
     * 取得删除状态
     * @return
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * 设置删除状态
     * @param yn
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取仓库全称
     *
     * @return warehouse_name - 仓库全称
     */
    public String getWarehouseName() {
        return warehouseName;
    }

    /**
     * 设置仓库全称
     *
     * @param warehouseName 仓库全称
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    /**
     * 获取仓库简称
     *
     * @return warehouse_abbreviation - 仓库简称
     */
    public String getWarehouseAbbreviation() {
        return warehouseAbbreviation;
    }

    /**
     * 设置仓库简称
     *
     * @param warehouseAbbreviation 仓库简称
     */
    public void setWarehouseAbbreviation(String warehouseAbbreviation) {
        this.warehouseAbbreviation = warehouseAbbreviation;
    }

    /**
     * 获取仓库编码
     *
     * @return warehouse_code - 仓库编码
     */
    public String getWarehouseCode() {
        return warehouseCode;
    }

    /**
     * 设置仓库编码
     *
     * @param warehouseCode 仓库编码
     */
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    /**
     * 获取省
     *
     * @return province - 省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省
     *
     * @param province 省
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取区县街
     *
     * @return area - 区县街
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置区县街
     *
     * @param area 区县街
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取详细地址
     *
     * @return detailed_address - 详细地址
     */
    public String getDetailedAddress() {
        return detailedAddress;
    }

    /**
     * 设置详细地址
     *
     * @param detailedAddress 详细地址
     */
    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    /**
     * 获取经度
     *
     * @return longitude - 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取维度
     *
     * @return dimension - 维度
     */
    public String getDimension() {
        return dimension;
    }

    /**
     * 设置维度
     *
     * @param dimension 维度
     */
    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    /**
     * 获取类型：1.平推库，2.货架库，3.自动化库
     *
     * @return type - 类型：1.平推库，2.货架库，3.自动化库
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型：1.平推库，2.货架库，3.自动化库
     *
     * @param type 类型：1.平推库，2.货架库，3.自动化库
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取建筑面积：单位平米
     *
     * @return architecture area - 建筑面积：单位平米
     */
    public String getArchitectureArea() {
        return architectureArea;
    }

    /**
     * 设置建筑面积：单位平米
     *
     * @param architectureArea 建筑面积：单位平米
     */
    public void setArchitectureArea(String architectureArea) {
        this.architectureArea = architectureArea;
    }

    /**
     * 获取使用面积：单位平米
     *
     * @return utilization_area - 使用面积：单位平米
     */
    public String getUtilizationArea() {
        return utilizationArea;
    }

    /**
     * 设置使用面积：单位平米
     *
     * @param utilizationArea 使用面积：单位平米
     */
    public void setUtilizationArea(String utilizationArea) {
        this.utilizationArea = utilizationArea;
    }

    /**
     * 获取总储存量：单位万吨
     *
     * @return reserves - 总储存量：单位万吨
     */
    public String getReserves() {
        return reserves;
    }

    /**
     * 设置总储存量：单位万吨
     *
     * @param reserves 总储存量：单位万吨
     */
    public void setReserves(String reserves) {
        this.reserves = reserves;
    }

    /**
     * 获取属性：1.保税仓 2.商超仓 3.监管仓 4.农产品仓 5.集货分拣仓
     *
     * @return attribute - 属性：1.保税仓 2.商超仓 3.监管仓 4.农产品仓 5.集货分拣仓
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * 设置属性：1.保税仓 2.商超仓 3.监管仓 4.农产品仓 5.集货分拣仓
     *
     * @param attribute 属性：1.保税仓 2.商超仓 3.监管仓 4.农产品仓 5.集货分拣仓
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * 获取容量：平米
     *
     * @return capacity_square_metre - 容量：平米
     */
    public String getCapacitySquareMetre() {
        return capacitySquareMetre;
    }

    /**
     * 设置容量：平米
     *
     * @param capacitySquareMetre 容量：平米
     */
    public void setCapacitySquareMetre(String capacitySquareMetre) {
        this.capacitySquareMetre = capacitySquareMetre;
    }

    /**
     * 获取容量：立方
     *
     * @return capacity_cube - 容量：立方
     */
    public String getCapacityCube() {
        return capacityCube;
    }

    /**
     * 设置容量：立方
     *
     * @param capacityCube 容量：立方
     */
    public void setCapacityCube(String capacityCube) {
        this.capacityCube = capacityCube;
    }

    /**
     * 获取容量：托盘
     *
     * @return capacity_tray - 容量：托盘
     */
    public String getCapacityTray() {
        return capacityTray;
    }

    /**
     * 设置容量：托盘
     *
     * @param capacityTray 容量：托盘
     */
    public void setCapacityTray(String capacityTray) {
        this.capacityTray = capacityTray;
    }

    /**
     * 获取出库时效:小时
     *
     * @return delivery_time - 出库时效:小时
     */
    public String getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * 设置出库时效:小时
     *
     * @param deliveryTime 出库时效:小时
     */
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * 获取分拣面积：平米
     *
     * @return sorting_area - 分拣面积：平米
     */
    public String  getSortingArea() {
        return sortingArea;
    }

    /**
     * 设置分拣面积：平米
     *
     * @param sortingArea 分拣面积：平米
     */
    public void setSortingArea(String sortingArea) {
        this.sortingArea = sortingArea;
    }

    /**
     * 获取分拣时间:小时
     *
     * @return sorting_time - 分拣时间:小时
     */
    public String getSortingTime() {
        return sortingTime;
    }

    /**
     * 设置分拣时间:小时
     *
     * @param sortingTime 分拣时间:小时
     */
    public void setSortingTime(String sortingTime) {
        this.sortingTime = sortingTime;
    }


    /**
     * 获取停车场：平米
     *
     * @return square_metre - 停车场：平米
     */
    public String getSquareMetre() {
        return squareMetre;
    }

    /**
     * 设置停车场：平米
     *
     * @param squareMetre 停车场：平米
     */
    public void setSquareMetre(String squareMetre) {
        this.squareMetre = squareMetre;
    }

    /**
     * 获取车位
     *
     * @return parking_lot - 车位
     */
    public String getParkingLot() {
        return parkingLot;
    }

    /**
     * 设置车位
     *
     * @param parkingLot 车位
     */
    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    /**
     * 获取叉车：单位台
     *
     * @return forklift - 叉车：单位台
     */
    public String getForklift() {
        return forklift;
    }

    /**
     * 设置叉车：单位台
     *
     * @param forklift 叉车：单位台
     */
    public void setForklift(String forklift) {
        this.forklift = forklift;
    }

    /**
     * 获取月台数
     *
     * @return platform - 月台数
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 设置月台数
     *
     * @param platform 月台数
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * 获取月台编码
     *
     * @return platform_code - 月台编码
     */
    public String getPlatformCode() {
        return platformCode;
    }

    /**
     * 设置月台编码
     *
     * @param platformCode 月台编码
     */
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    /**
     * 获取1.发货月台 2.收货月台
     *
     * @return platform_type - 1.发货月台 2.收货月台
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * 设置1.发货月台 2.收货月台
     *
     * @param platformType 1.发货月台 2.收货月台
     */
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    /**
     * 获取1.常温（0以上）2.冷藏（0~5） 3.冷冻（-15~-18）4.急冻（-23）5.深冷（-27~-35或更低）
     *
     * @return temperature_type - 1.常温（0以上）2.冷藏（0~5） 3.冷冻（-15~-18）4.急冻（-23）5.深冷（-27~-35或更低）
     */
    public String getTemperatureType() {
        return temperatureType;
    }

    /**
     * 设置1.常温（0以上）2.冷藏（0~5） 3.冷冻（-15~-18）4.急冻（-23）5.深冷（-27~-35或更低）
     *
     * @param temperatureType 1.常温（0以上）2.冷藏（0~5） 3.冷冻（-15~-18）4.急冻（-23）5.深冷（-27~-35或更低）
     */
    public void setTemperatureType(String temperatureType) {
        this.temperatureType = temperatureType;
    }

    /**
     * 获取仓库位置图
     *
     * @return imgurl - 仓库位置图
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * 设置仓库位置图
     *
     * @param imgurl 仓库位置图
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    /**
     * 获取仓库库位表
     *
     * @return storage - 仓库库位表
     */
    public String getStorage() {
        return storage;
    }

    /**
     * 设置仓库库位表
     *
     * @param storage 仓库库位表
     */
    public void setStorage(String storage) {
        this.storage = storage;
    }

    /**
     * 获取常温储存量:单位吨
     *
     * @return normal_temperature - 常温储存量:单位吨
     */
    public String getNormalTemperature() {
        return normalTemperature;
    }

    /**
     * 设置常温储存量:单位吨
     *
     * @param normalTemperature 常温储存量:单位吨
     */
    public void setNormalTemperature(String normalTemperature) {
        this.normalTemperature = normalTemperature;
    }

    /**
     * 获取冷藏储存量:单位吨
     *
     * @return cold_storage - 冷藏储存量:单位吨
     */
    public String getColdStorage() {
        return coldStorage;
    }

    /**
     * 设置冷藏储存量:单位吨
     *
     * @param coldStorage 冷藏储存量:单位吨
     */
    public void setColdStorage(String coldStorage) {
        this.coldStorage = coldStorage;
    }

    /**
     * 获取冷冻储存量:单位吨
     *
     * @return freezing - 冷冻储存量:单位吨
     */
    public String getFreezing() {
        return freezing;
    }

    /**
     * 设置冷冻储存量:单位吨
     *
     * @param freezing 冷冻储存量:单位吨
     */
    public void setFreezing(String freezing) {
        this.freezing = freezing;
    }

    /**
     * 获取急冻存储量:单位吨
     *
     * @return frozen - 急冻存储量:单位吨
     */
    public String getFrozen() {
        return frozen;
    }

    /**
     * 设置急冻存储量:单位吨
     *
     * @param frozen 急冻存储量:单位吨
     */
    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    /**
     * 获取深冷储存量:单位吨
     *
     * @return cryogenic - 深冷储存量:单位吨
     */
    public String getCryogenic() {
        return cryogenic;
    }

    /**
     * 设置深冷储存量:单位吨
     *
     * @param cryogenic 深冷储存量:单位吨
     */
    public void setCryogenic(String cryogenic) {
        this.cryogenic = cryogenic;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建人ID
     *
     * @return creator_id - 创建人ID
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID
     *
     * @param creatorId 创建人ID
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取创建时间
     *
     * @return created_time - 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     *
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取最近操作人
     *
     * @return last_operator - 最近操作人
     */
    public String getLastOperator() {
        return lastOperator;
    }

    /**
     * 设置最近操作人
     *
     * @param lastOperator 最近操作人
     */
    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    /**
     * 获取最后操作人ID
     *
     * @return last_operator_id - 最后操作人ID
     */
    public String getLastOperatorId() {
        return lastOperatorId;
    }

    /**
     * 设置最后操作人ID
     *
     * @param lastOperatorId 最后操作人ID
     */
    public void setLastOperatorId(String lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
     * 企业id
     */
    @Column(name = "group_id")
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "RmcWarehouse{" +
                "id=" + id +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseAbbreviation='" + warehouseAbbreviation + '\'' +
                ", warehouseCode=" + warehouseCode +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", detailedAddress='" + detailedAddress + '\'' +
                ", longitude='" + longitude + '\'' +
                ", dimension='" + dimension + '\'' +
                ", type='" + type + '\'' +
                ", architectureArea=" + architectureArea +
                ", utilizationArea=" + utilizationArea +
                ", reserves='" + reserves + '\'' +
                ", attribute='" + attribute + '\'' +
                ", capacitySquareMetre='" + capacitySquareMetre + '\'' +
                ", capacityCube='" + capacityCube + '\'' +
                ", capacityTray='" + capacityTray + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", sortingArea=" + sortingArea +
                ", sortingTime=" + sortingTime +
                  '\'' +
                ", squareMetre='" + squareMetre + '\'' +
                ", parkingLot='" + parkingLot + '\'' +
                ", forklift='" + forklift + '\'' +
                ", platform='" + platform + '\'' +
                ", platformCode='" + platformCode + '\'' +
                ", platformType='" + platformType + '\'' +
                ", temperatureType='" + temperatureType + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", storage='" + storage + '\'' +
                ", normalTemperature='" + normalTemperature + '\'' +
                ", coldStorage='" + coldStorage + '\'' +
                ", freezing='" + freezing + '\'' +
                ", frozen='" + frozen + '\'' +
                ", cryogenic='" + cryogenic + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", createdTime=" + createdTime +
                ", lastOperator='" + lastOperator + '\'' +
                ", lastOperatorId='" + lastOperatorId + '\'' +
                ", updateTime=" + updateTime +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}