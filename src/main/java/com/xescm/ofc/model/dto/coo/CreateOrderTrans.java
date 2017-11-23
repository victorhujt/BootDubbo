package com.xescm.ofc.model.dto.coo;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.edas.model.dto.ofc.OfcCreateOrderDTO;
import com.xescm.ofc.edas.model.dto.ofc.OfcCreateOrderGoodsInfoDTO;
import com.xescm.ofc.enums.OrderStatusEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.utils.DateUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.CREATE_ORDER_BYAPI;

/**
 * 订单中心创建订单（鲜易网） 转换为数据库DO
 * Created by hiyond on 2016/11/14.
 */
public class CreateOrderTrans {

    private OfcCreateOrderDTO createOrderEntity;

    private String orderCode;

    private Date nowDate;

    ModelMapper modelMapper;

    public CreateOrderTrans(OfcCreateOrderDTO createOrderEntity, String orderCode) {
        this.createOrderEntity = createOrderEntity;
        this.orderCode = orderCode;
        this.nowDate = new Date();
        this.modelMapper = new ModelMapper();
    }

    /**
     * 货品明细信息
     */
    private List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList;

    /**
     * 订单中心基本信息
     */
    private OfcFundamentalInformation ofcFundamentalInformation;

    /**
     * 订单中心配送基本信息
     */
    private OfcDistributionBasicInfo ofcDistributionBasicInfo;

    /**
     * 财务信息
     */
    private OfcFinanceInformation ofcFinanceInformation;

    /**
     * 仓储信息
     */
    private OfcWarehouseInformation ofcWarehouseInformation;

    /**
     * 订单状态
     */
    private OfcOrderStatus ofcOrderStatus;

    public OfcOrderStatus getOfcOrderStatus() throws BusinessException {
        if (createOrderEntity != null) {
            ofcOrderStatus = new OfcOrderStatus();
            ofcOrderStatus.setOrderStatus(OrderStatusEnum.PEND_AUDIT.getCode());
            ofcOrderStatus.setOrderCode(orderCode);
            StringBuilder notes = new StringBuilder();
            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已创建");
            notes.append(" 操作人: ").append(CREATE_ORDER_BYAPI);
            notes.append(" 操作单位: ").append(CREATE_ORDER_BYAPI);
            ofcOrderStatus.setTraceStatus(OrderStatusEnum.PEND_AUDIT.getCode());
            ofcOrderStatus.setTrace("接收订单");
            ofcOrderStatus.setNotes(notes.toString());
            ofcOrderStatus.setOperator(CREATE_ORDER_BYAPI);
            ofcOrderStatus.setLastedOperTime(nowDate);
        }
        return this.ofcOrderStatus;
    }

    /**
     * 获取货品明细信息
     *
     * @return
     */
    public List<OfcGoodsDetailsInfo> getOfcGoodsDetailsInfoList() throws BusinessException {
        if (createOrderEntity != null) {
            List<OfcCreateOrderGoodsInfoDTO> list = createOrderEntity.getCreateOrderGoodsInfos();
            if (null != list && !list.isEmpty()) {
                this.ofcGoodsDetailsInfoList = new ArrayList<>();
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                modelMapper.addMappings(new PropertyMap<OfcCreateOrderGoodsInfoDTO, OfcGoodsDetailsInfo>() {
                    @Override
                    protected void configure() {
                        skip().setInvalidTime(null);
                        skip().setProductionTime(null);
                    }
                });
                for (OfcCreateOrderGoodsInfoDTO goodsInfo : list) {
                    OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
                    String productionTime = goodsInfo.getProductionTime();
                    String invalidTime = goodsInfo.getInvalidTime();
                    modelMapper.map(goodsInfo, ofcGoodsDetailsInfo);
                    ofcGoodsDetailsInfo.setOrderCode(this.orderCode);
                    ofcGoodsDetailsInfo.setCreationTime(nowDate);
                    ofcGoodsDetailsInfo.setOperator(CREATE_ORDER_BYAPI);
                    if (!PubUtils.isSEmptyOrNull(productionTime)) {
                        ofcGoodsDetailsInfo.setProductionTime(DateUtils.String2Date(productionTime, DateUtils.DateFormatType.TYPE2));
                    }
                    if (!PubUtils.isSEmptyOrNull(invalidTime)) {
                        ofcGoodsDetailsInfo.setInvalidTime(DateUtils.String2Date(invalidTime, DateUtils.DateFormatType.TYPE2));
                    }
                    ofcGoodsDetailsInfo.setOperTime(nowDate);
                    ofcGoodsDetailsInfoList.add(ofcGoodsDetailsInfo);
                }
                return ofcGoodsDetailsInfoList;
            }
        }
        return null;
    }

    /**
     * 获取订单中心基本信息
     *
     * @return
     */
    public OfcFundamentalInformation getOfcFundamentalInformation() throws BusinessException {
        if (createOrderEntity != null) {
            ofcFundamentalInformation = new OfcFundamentalInformation();
            modelMapper.addMappings(new PropertyMap<OfcCreateOrderDTO, OfcFundamentalInformation>() {
                @Override
                protected void configure() {
                    skip().setOrderTime(null);
                }
            });
            modelMapper.map(createOrderEntity, ofcFundamentalInformation);
            ofcFundamentalInformation.setOrderCode(this.orderCode);
            ofcFundamentalInformation.setOrderTime(DateUtils.String2Date(createOrderEntity.getOrderTime(), DateUtils.DateFormatType.TYPE2));
            ofcFundamentalInformation.setCreationTime(nowDate);
            ofcFundamentalInformation.setOperator(CREATE_ORDER_BYAPI);
            ofcFundamentalInformation.setCreator(CREATE_ORDER_BYAPI);
            ofcFundamentalInformation.setOperTime(nowDate);
//            String custCode = createOrderEntity.getCustCode();
//            String orderSource = OrderSourceEnum.getCodeByCustCode(custCode);
//            ofcFundamentalInformation.setOrderSource(orderSource);
            //如果是鲜易网（100002或者XEBEST），固定平台类型为4
//            if (StringUtils.equals(CreateOrderApiConstant.XEBEST_CUST_CODE, custCode) || StringUtils.equals(CreateOrderApiConstant.XEBEST_CUST_CODE_TEST, custCode)) {
//                ofcFundamentalInformation.setPlatformType(CreateOrderApiConstant.XEBEST_PLATFORM_TYPE);
//            }
            return ofcFundamentalInformation;
        }
        return null;
    }

    /**
     * 返回订单中心配送基本信息
     *
     * @return
     * @throws Exception
     */
    public OfcDistributionBasicInfo   getOfcDistributionBasicInfo() throws BusinessException {
        if (createOrderEntity != null) {
            ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
            modelMapper.addMappings(new PropertyMap<OfcCreateOrderDTO, OfcDistributionBasicInfo>() {
                @Override
                protected void configure() {
                    skip().setPickupTime(null);
                    skip().setExpectedArrivedTime(null);
                    skip().setConsignorCode(null);
                    skip().setConsigneeCode(null);
                }
            });
            modelMapper.map(createOrderEntity, ofcDistributionBasicInfo);
            ofcDistributionBasicInfo.setOrderCode(this.orderCode);
            ofcDistributionBasicInfo.setPickupTime(DateUtils.String2Date(createOrderEntity.getPickupTime(), DateUtils.DateFormatType.TYPE1));
            ofcDistributionBasicInfo.setExpectedArrivedTime(DateUtils.String2Date(createOrderEntity.getExpectedArrivedTime(), DateUtils.DateFormatType.TYPE1));
            ofcDistributionBasicInfo.setConsignorContactName(createOrderEntity.getConsignorContact());
            ofcDistributionBasicInfo.setConsignorContactPhone(createOrderEntity.getConsignorPhone());
            ofcDistributionBasicInfo.setConsignorName(createOrderEntity.getConsignorName());
            ofcDistributionBasicInfo.setConsignorCode(createOrderEntity.getConsignorCode());
            ofcDistributionBasicInfo.setConsignorType("2");
            //发货方传真、发货方Email、发货方邮编 数据库暂无字段
            ofcDistributionBasicInfo.setDepartureProvince(createOrderEntity.getConsignorProvince());
            ofcDistributionBasicInfo.setDepartureCity(createOrderEntity.getConsignorCity());
            ofcDistributionBasicInfo.setDepartureDistrict(createOrderEntity.getConsignorCounty());
            ofcDistributionBasicInfo.setDepartureTowns(createOrderEntity.getConsignorTown());
            ofcDistributionBasicInfo.setDeparturePlace(createOrderEntity.getConsignorAddress());

            String consignorProvinceCode = createOrderEntity.getConsignorProvinceCode();
            if(!PubUtils.isSEmptyOrNull(consignorProvinceCode)){
                StringBuilder departureCode = new StringBuilder(createOrderEntity.getConsignorProvinceCode());
                if(!PubUtils.isSEmptyOrNull(createOrderEntity.getConsignorCityCode())){
                    departureCode.append(",").append(createOrderEntity.getConsignorCityCode());
                }
                if(!PubUtils.isSEmptyOrNull(createOrderEntity.getConsignorCountyCode())){
                    departureCode.append(",").append(createOrderEntity.getConsignorCountyCode());
                }
                if(!PubUtils.isSEmptyOrNull(createOrderEntity.getConsignorTownCode())){
                    departureCode.append(",").append(createOrderEntity.getConsignorTownCode());
                }
                ofcDistributionBasicInfo.setDeparturePlaceCode(departureCode.toString());
            }
            //收货方
            ofcDistributionBasicInfo.setConsigneeContactName(createOrderEntity.getConsigneeContact());
            ofcDistributionBasicInfo.setDestinationProvince(createOrderEntity.getConsigneeProvince());
            ofcDistributionBasicInfo.setConsigneeContactPhone(createOrderEntity.getConsigneePhone());
            ofcDistributionBasicInfo.setConsigneeName(createOrderEntity.getConsigneeName());
            ofcDistributionBasicInfo.setConsigneeCode(createOrderEntity.getConsigneeCode());
            ofcDistributionBasicInfo.setConsigneeContactCode(createOrderEntity.getConsigneeContactCode());
            ofcDistributionBasicInfo.setConsigneeType("2");
            //收货方传真、收货方Email、收货方邮编 暂无字段
            ofcDistributionBasicInfo.setDestinationProvince(createOrderEntity.getConsigneeProvince());
            ofcDistributionBasicInfo.setDestinationCity(createOrderEntity.getConsigneeCity());
            ofcDistributionBasicInfo.setDestinationDistrict(createOrderEntity.getConsigneeCounty());
            ofcDistributionBasicInfo.setDestinationTowns(createOrderEntity.getConsigneeTown());
            ofcDistributionBasicInfo.setDestination(createOrderEntity.getConsigneeAddress());
            StringBuilder destinationCode = new StringBuilder();
            if (!PubUtils.isSEmptyOrNull(createOrderEntity.getConsigneeProvinceCode())) {
                destinationCode.append(createOrderEntity.getConsigneeProvinceCode());
            }
            if(!PubUtils.isSEmptyOrNull(createOrderEntity.getConsigneeCityCode())){
                destinationCode.append(",").append(createOrderEntity.getConsigneeCityCode());
            }
            if(!PubUtils.isSEmptyOrNull(createOrderEntity.getConsigneeCountyCode())){
                destinationCode.append(",").append(createOrderEntity.getConsigneeCountyCode());
            }
            if(!PubUtils.isSEmptyOrNull(createOrderEntity.getConsigneeTownCode())){
                destinationCode.append(",").append(createOrderEntity.getConsigneeTownCode());
            }
            ofcDistributionBasicInfo.setDestinationCode(destinationCode.toString());
            //承运商
            ofcDistributionBasicInfo.setCarrierName(createOrderEntity.getSupportName());

            //生成运输单号(如果运输单号为空设置为订单号)
            String transCode = ofcDistributionBasicInfo.getTransCode();
            if (PubUtils.isNull(transCode)) {
                ofcDistributionBasicInfo.setTransCode(this.orderCode);
            }
            ofcDistributionBasicInfo.setCreationTime(nowDate);
            ofcDistributionBasicInfo.setOperator(CREATE_ORDER_BYAPI);
            ofcDistributionBasicInfo.setOperTime(nowDate);
            return ofcDistributionBasicInfo;
        }
        return null;
    }

    /**
     * 返回财务信息
     *
     * @return OfcFinanceInformation
     * @throws Exception
     */
    public OfcFinanceInformation getOfcFinanceInformation() throws BusinessException {
        if (createOrderEntity != null) {
            this.ofcFinanceInformation = new OfcFinanceInformation();
            modelMapper.map(createOrderEntity, ofcFinanceInformation);
            ofcFinanceInformation.setOrderCode(this.orderCode);
            ofcFinanceInformation.setCreationTime(nowDate);
            ofcFinanceInformation.setOperator(CREATE_ORDER_BYAPI);
            ofcFinanceInformation.setOperTime(nowDate);
            return ofcFinanceInformation;
        }
        return null;
    }

    /**
     * 返回仓储信息
     *
     * @return
     * @throws Exception
     */
    public OfcWarehouseInformation getOfcWarehouseInformation() throws BusinessException {
        if (createOrderEntity != null) {
            this.ofcWarehouseInformation = new OfcWarehouseInformation();
            modelMapper.addMappings(new PropertyMap<OfcCreateOrderDTO, OfcWarehouseInformation>() {
                @Override
                protected void configure() {
                    map().setArriveTime(null);
                }
            });
            modelMapper.map(createOrderEntity, ofcWarehouseInformation);
            Integer provideTransport = ofcWarehouseInformation.getProvideTransport();
            provideTransport = provideTransport == null || provideTransport == 0 ? 0 : 1;
            ofcWarehouseInformation.setProvideTransport(provideTransport);
            ofcWarehouseInformation.setOrderCode(this.orderCode);
            ofcWarehouseInformation.setArriveTime(DateUtils.String2Date(createOrderEntity.getArriveTime(), DateUtils.DateFormatType.TYPE1));
            ofcWarehouseInformation.setCreationTime(nowDate);
            ofcWarehouseInformation.setOperator(CREATE_ORDER_BYAPI);
            ofcWarehouseInformation.setOperTime(nowDate);
            return ofcWarehouseInformation;
        }
        return null;
    }

}
