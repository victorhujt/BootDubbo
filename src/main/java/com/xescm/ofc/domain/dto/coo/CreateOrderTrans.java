package com.xescm.ofc.domain.dto.coo;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.utils.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xescm.ofc.enums.OrderConstEnum.CREATE_ORDER_BYAPI;

/**
 * 订单中心创建订单（鲜易网） 转换为数据库DO
 * Created by hiyond on 2016/11/14.
 */
public class CreateOrderTrans {

    private CreateOrderEntity createOrderEntity;

    private String orderCode;

    private Date nowDate = new Date();

    public CreateOrderTrans(CreateOrderEntity createOrderEntity, String orderCode) {
        this.createOrderEntity = createOrderEntity;
        this.orderCode = orderCode;
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
     * 获取货品明细信息
     *
     * @return
     */
    public List<OfcGoodsDetailsInfo> getOfcGoodsDetailsInfoList() throws BusinessException {
        if (createOrderEntity != null) {
            List<CreateOrderGoodsInfo> list = createOrderEntity.getCreateOrderGoodsInfos();
            if (null != list && !list.isEmpty()) {
                this.ofcGoodsDetailsInfoList = new ArrayList<>();
                OfcGoodsDetailsInfo ofcGoodsDetailsInfo = null;
                for (CreateOrderGoodsInfo goodsInfo : list) {
                    ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
                    ofcGoodsDetailsInfo.setOrderCode(this.orderCode);

                    ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
                    String goodsCode = goodsInfo.getGoodsCode();
                    String goodsName = goodsInfo.getGoodsName();
                    String goodsSpec = goodsInfo.getGoodsSpec();
                    String unit = goodsInfo.getUnit();
                    String quantity = goodsInfo.getQuantity();
                    String unitPrice = goodsInfo.getUnitPrice();
                    String productionBatch = goodsInfo.getProductionBatch();
                    String productionTime = goodsInfo.getProductionTime();
                    String invalidTime = goodsInfo.getInvalidTime();

                    ofcGoodsDetailsInfo.setGoodsCode(goodsCode);
                    ofcGoodsDetailsInfo.setGoodsName(goodsName);
                    ofcGoodsDetailsInfo.setGoodsSpec(goodsSpec);
                    ofcGoodsDetailsInfo.setUnit(unit);
                    ofcGoodsDetailsInfo.setQuantity(NumberUtils.isNumber(quantity) ? new BigDecimal(quantity) : null);
                    ofcGoodsDetailsInfo.setUnitPrice(NumberUtils.isNumber(unitPrice) ? new BigDecimal(unitPrice) : null);
                    ofcGoodsDetailsInfo.setProductionBatch(productionBatch);
                    ofcGoodsDetailsInfo.setProductionTime(DateUtils.String2Date(productionTime, DateUtils.DateFormatType.TYPE1));
                    ofcGoodsDetailsInfo.setInvalidTime(DateUtils.String2Date(invalidTime, DateUtils.DateFormatType.TYPE1));

                    ofcGoodsDetailsInfo.setCreationTime(nowDate);
                    ofcGoodsDetailsInfo.setOperator(CREATE_ORDER_BYAPI);
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
            ofcFundamentalInformation.setOrderCode(this.orderCode);
            ofcFundamentalInformation.setOrderTime(DateUtils.String2Date(createOrderEntity.getOrderTime(), DateUtils.DateFormatType.TYPE2));
            ofcFundamentalInformation.setCustCode(createOrderEntity.getCustCode());
            ofcFundamentalInformation.setCustName(createOrderEntity.getCustName());
            ofcFundamentalInformation.setSecCustCode(createOrderEntity.getSecCustCode());
            ofcFundamentalInformation.setSecCustName(createOrderEntity.getSecCustName());
            ofcFundamentalInformation.setOrderType(createOrderEntity.getOrderType());
            ofcFundamentalInformation.setBusinessType(createOrderEntity.getBusinessType());
            ofcFundamentalInformation.setCustOrderCode(createOrderEntity.getCustOrderCode());
            ofcFundamentalInformation.setNotes(createOrderEntity.getNotes());
            ofcFundamentalInformation.setStoreCode(createOrderEntity.getStoreCode());
            ofcFundamentalInformation.setOrderSource(createOrderEntity.getOrderSource());
            ofcFundamentalInformation.setCreationTime(nowDate);
            ofcFundamentalInformation.setOperator(CREATE_ORDER_BYAPI);
            ofcFundamentalInformation.setOperTime(nowDate);
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
    public OfcDistributionBasicInfo getOfcDistributionBasicInfo() throws BusinessException {
        if (createOrderEntity != null) {
            ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
            ofcDistributionBasicInfo.setOrderCode(this.orderCode);

            ofcDistributionBasicInfo.setQuantity(NumberUtils.isNumber(createOrderEntity.getQuantity()) ? new BigDecimal(createOrderEntity.getQuantity()) : null);
            ofcDistributionBasicInfo.setWeight(NumberUtils.isNumber(createOrderEntity.getQuantity()) ? new BigDecimal(createOrderEntity.getQuantity()) : null);
            ofcDistributionBasicInfo.setCubage(createOrderEntity.getCubage());
            ofcDistributionBasicInfo.setTotalStandardBox(NumberUtils.createInteger(createOrderEntity.getTotalStandardBox()));
            ofcDistributionBasicInfo.setTransRequire(createOrderEntity.getTransRequire());
            ofcDistributionBasicInfo.setPickupTime(DateUtils.String2Date(createOrderEntity.getPickupTime(), DateUtils.DateFormatType.TYPE1));
            ofcDistributionBasicInfo.setExpectedArrivedTime(DateUtils.String2Date(createOrderEntity.getExpectedArrivedTime(), DateUtils.DateFormatType.TYPE1));
            ofcDistributionBasicInfo.setConsignorName(createOrderEntity.getConsignorName());
            ofcDistributionBasicInfo.setConsignorContactName(createOrderEntity.getConsignorName());

            // FIXME: 2016/11/15 注释字段没有
            //发货方联系电话
            //发货方传真
            //发货方Email
            //发货方邮编
            ofcDistributionBasicInfo.setDeparturePlaceCode(createOrderEntity.getConsignorZip());
            ofcDistributionBasicInfo.setDepartureProvince(createOrderEntity.getConsignorProvince());
            ofcDistributionBasicInfo.setDepartureCity(createOrderEntity.getConsignorCity());
            ofcDistributionBasicInfo.setDepartureDistrict(createOrderEntity.getConsignorCounty());
            ofcDistributionBasicInfo.setDepartureTowns(createOrderEntity.getConsignorTown());
            ofcDistributionBasicInfo.setDeparturePlace(createOrderEntity.getConsignorAddress());

            // FIXME: 2016/11/15 注释字段没有
            //收货方
            ofcDistributionBasicInfo.setConsigneeName(createOrderEntity.getConsigneeName());
            ofcDistributionBasicInfo.setConsigneeContactName(createOrderEntity.getConsigneeContact());
            ofcDistributionBasicInfo.setDestinationProvince(createOrderEntity.getConsigneeProvince());

            // FIXME: 2016/11/15 注释字段没有
            //收货方联系电话
            //收货方传真
            //收货方Email
            //收货方邮编
            ofcDistributionBasicInfo.setDestinationProvince(createOrderEntity.getConsigneeProvince());
            ofcDistributionBasicInfo.setDestinationCity(createOrderEntity.getConsigneeCity());
            ofcDistributionBasicInfo.setDestinationDistrict(createOrderEntity.getConsigneeCounty());
            ofcDistributionBasicInfo.setDestinationTowns(createOrderEntity.getConsigneeTown());
            ofcDistributionBasicInfo.setDestination(createOrderEntity.getConsigneeAddress());

            //承运商
            ofcDistributionBasicInfo.setCarrierName(createOrderEntity.getSupportName());

            //车
            ofcDistributionBasicInfo.setPlateNumber(createOrderEntity.getPlateNumber());
            ofcDistributionBasicInfo.setDriverName(createOrderEntity.getDriverName());
            ofcDistributionBasicInfo.setContactNumber(createOrderEntity.getContactNumber());

            //baseId
            ofcDistributionBasicInfo.setBaseId(createOrderEntity.getBaseId());

            /**
             * FIXME 生成运输单号
             */
            String transCode = null;
            ofcDistributionBasicInfo.setTransCode(transCode);

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
            ofcFinanceInformation.setOrderCode(this.orderCode);
            ofcFinanceInformation.setServiceCharge(ofcFinanceInformation.getServiceCharge());
            ofcFinanceInformation.setOrderAmount(NumberUtils.isNumber(createOrderEntity.getOrderAmount()) ? new BigDecimal(createOrderEntity.getOrderAmount()) : null);
            ofcFinanceInformation.setPaymentAmount(NumberUtils.isNumber(createOrderEntity.getPaymentAmount()) ? new BigDecimal(createOrderEntity.getPaymentAmount()) : null);
            ofcFinanceInformation.setCollectLoanAmount(NumberUtils.isNumber(createOrderEntity.getCollectLoanAmount()) ? new BigDecimal(createOrderEntity.getCollectLoanAmount()) : null);
            ofcFinanceInformation.setCollectServiceCharge(NumberUtils.isNumber(createOrderEntity.getCollectServiceCharge()) ? new BigDecimal(createOrderEntity.getCollectServiceCharge()) : null);
            ofcFinanceInformation.setCollectFlag(createOrderEntity.getCollectFlag());
            ofcFinanceInformation.setPrintInvoice(createOrderEntity.getPrintInvoice());
            ofcFinanceInformation.setBuyerPaymentMethod(createOrderEntity.getBuyerPaymentMe());
            ofcFinanceInformation.setInsure(createOrderEntity.getInsure());
            ofcFinanceInformation.setInsureValue(createOrderEntity.getInsureValue());

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
            ofcWarehouseInformation.setOrderCode(this.orderCode);
            ofcWarehouseInformation.setWarehouseCode(createOrderEntity.getWarehouseCode());
            ofcWarehouseInformation.setWarehouseName(createOrderEntity.getWarehouseName());
            ofcWarehouseInformation.setSupportName(createOrderEntity.getSupportName());
            ofcWarehouseInformation.setProvideTransport(NumberUtils.createInteger(createOrderEntity.getProvideTransport()));
            ofcWarehouseInformation.setArriveTime(DateUtils.String2Date(createOrderEntity.getArriveTime(), DateUtils.DateFormatType.TYPE1));
            ofcWarehouseInformation.setPlateNumber(createOrderEntity.getPlateNumber());
            ofcWarehouseInformation.setDriverName(createOrderEntity.getDriverName());

            ofcWarehouseInformation.setCreationTime(nowDate);
            ofcWarehouseInformation.setOperator(CREATE_ORDER_BYAPI);
            ofcWarehouseInformation.setOperTime(nowDate);
            return ofcWarehouseInformation;
        }
        return null;
    }

}
