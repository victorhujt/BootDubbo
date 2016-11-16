package com.xescm.ofc.domain.dto.coo;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.utils.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单中心创建订单（鲜易网） 转换为数据库DO
 * Created by hiyond on 2016/11/14.
 */
public class XebestCreateOrderTrans {

    private CreateOrderEntity createOrderEntity;

    private String order_code;

    private Date nowDate = new Date();

    public CreateOrderTrans(CreateOrderEntity createOrderEntity, String order_code) {
        this.createOrderEntity = createOrderEntity;
        this.order_code = order_code;
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
    public List<OfcGoodsDetailsInfo> getOfcGoodsDetailsInfoList() throws Exception {
        if (createOrderEntity != null) {
            List<CreateOrderGoodsInfo> list = createOrderEntity.getCreateOrderGoodsInfos();
            if (null != list && !list.isEmpty()) {
                this.ofcGoodsDetailsInfoList = new ArrayList<>();
                OfcGoodsDetailsInfo ofcGoodsDetailsInfo = null;
                for (CreateOrderGoodsInfo goodsInfo : list) {
                    ofcGoodsDetailsInfo.setOrderCode(this.order_code);

                    ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
                    String goods_code = goodsInfo.getGoods_code();
                    String goods_name = goodsInfo.getGoods_name();
                    String goods_spec = goodsInfo.getGoods_spec();
                    String unit = goodsInfo.getUnit();
                    String quantity = goodsInfo.getQuantity();
                    String unit_price = goodsInfo.getUnit_price();
                    String production_batch = goodsInfo.getProduction_batch();
                    String production_time = goodsInfo.getProduction_time();
                    String invalid_time = goodsInfo.getInvalid_time();

                    ofcGoodsDetailsInfo.setGoodsCode(goods_code);
                    ofcGoodsDetailsInfo.setGoodsName(goods_name);
                    ofcGoodsDetailsInfo.setGoodsSpec(goods_spec);
                    ofcGoodsDetailsInfo.setUnit(unit);
                    ofcGoodsDetailsInfo.setQuantity(new BigDecimal(quantity));
                    ofcGoodsDetailsInfo.setUnitPrice(new BigDecimal(unit_price));
                    ofcGoodsDetailsInfo.setProductionBatch(production_batch);
                    ofcGoodsDetailsInfo.setProductionTime(DateUtils.String2Date(production_time, DateUtils.DateFormatType.TYPE1));
                    ofcGoodsDetailsInfo.setInvalidTime(DateUtils.String2Date(invalid_time, DateUtils.DateFormatType.TYPE1));

                    ofcGoodsDetailsInfo.setCreationTime(nowDate);
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
    public OfcFundamentalInformation getOfcFundamentalInformation() throws Exception {
        if (createOrderEntity != null) {
            ofcFundamentalInformation = new OfcFundamentalInformation();
            ofcFundamentalInformation.setOrderCode(this.order_code);
            ofcFundamentalInformation.setOrderTime(DateUtils.String2Date(createOrderEntity.getOrder_time(), DateUtils.DateFormatType.TYPE1));
            ofcFundamentalInformation.setCustCode(createOrderEntity.getCust_code());
            ofcFundamentalInformation.setCustName(createOrderEntity.getCust_name());
            ofcFundamentalInformation.setOrderType(createOrderEntity.getOrder_type());
            ofcFundamentalInformation.setBusinessType(createOrderEntity.getBusiness_type());
            ofcFundamentalInformation.setCustOrderCode(createOrderEntity.getCust_order_code());
            ofcFundamentalInformation.setNotes(createOrderEntity.getNotes());
            ofcFundamentalInformation.setStoreCode(createOrderEntity.getStore_code());
            ofcFundamentalInformation.setOrderSource(createOrderEntity.getOrder_source());
            ofcFundamentalInformation.setCreationTime(nowDate);
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
    public OfcDistributionBasicInfo getOfcDistributionBasicInfo() throws Exception {
        if (createOrderEntity != null) {
            ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
            ofcDistributionBasicInfo.setOrderCode(this.order_code);

            ofcDistributionBasicInfo.setQuantity(new BigDecimal(createOrderEntity.getQuantity()));
            ofcDistributionBasicInfo.setWeight(new BigDecimal(createOrderEntity.getQuantity()));
            ofcDistributionBasicInfo.setCubage(createOrderEntity.getCubage());
            ofcDistributionBasicInfo.setTotalStandardBox(Integer.valueOf(createOrderEntity.getTotal_standard_box()));
            ofcDistributionBasicInfo.setTransRequire(createOrderEntity.getTrans_require());
            ofcDistributionBasicInfo.setPickupTime(DateUtils.String2Date(createOrderEntity.getPickup_time(), DateUtils.DateFormatType.TYPE1));
            ofcDistributionBasicInfo.setExpectedArrivedTime(DateUtils.String2Date(createOrderEntity.getExpected_arrived_time(), DateUtils.DateFormatType.TYPE1));
            ofcDistributionBasicInfo.setConsignorName(createOrderEntity.getConsignor_name());
            ofcDistributionBasicInfo.setConsignorContactName(createOrderEntity.getConsignor_name());

            // FIXME: 2016/11/15 注释字段没有
            //发货方联系电话
            //发货方传真
            //发货方Email
            //发货方邮编
            ofcDistributionBasicInfo.setDeparturePlaceCode(createOrderEntity.getConsignor_zip());
            ofcDistributionBasicInfo.setDepartureProvince(createOrderEntity.getConsignor_province());
            ofcDistributionBasicInfo.setDepartureCity(createOrderEntity.getConsignor_city());
            ofcDistributionBasicInfo.setDepartureDistrict(createOrderEntity.getConsignor_county());
            ofcDistributionBasicInfo.setDepartureTowns(createOrderEntity.getConsignor_town());
            ofcDistributionBasicInfo.setDeparturePlace(createOrderEntity.getConsignor_address());

            // FIXME: 2016/11/15 注释字段没有
            //收货方
            ofcDistributionBasicInfo.setConsigneeName(createOrderEntity.getConsignee_name());
            ofcDistributionBasicInfo.setConsigneeContactName(createOrderEntity.getConsignee_contact());
            ofcDistributionBasicInfo.setDestinationProvince(createOrderEntity.getConsignee_province());

            // FIXME: 2016/11/15 注释字段没有
            //收货方联系电话
            //收货方传真
            //收货方Email
            //收货方邮编
            ofcDistributionBasicInfo.setDestinationProvince(createOrderEntity.getConsignee_province());
            ofcDistributionBasicInfo.setDestinationCity(createOrderEntity.getConsignee_city());
            ofcDistributionBasicInfo.setDestinationDistrict(createOrderEntity.getConsignee_county());
            ofcDistributionBasicInfo.setDestinationTowns(createOrderEntity.getConsignee_town());
            ofcDistributionBasicInfo.setDestination(createOrderEntity.getConsignee_address());

            //承运商
            ofcDistributionBasicInfo.setCarrierName(createOrderEntity.getSupport_name());

            //车
            ofcDistributionBasicInfo.setPlateNumber(createOrderEntity.getPlate_number());
            ofcDistributionBasicInfo.setDriverName(createOrderEntity.getDriver_name());
            ofcDistributionBasicInfo.setContactNumber(createOrderEntity.getContact_number());

            //baseId
            ofcDistributionBasicInfo.setBaseId(createOrderEntity.getBase_id());

            /**
             * FIXME 生成运输单号
             */
            String transCode = null;
            ofcDistributionBasicInfo.setTransCode(transCode);

            ofcDistributionBasicInfo.setCreationTime(nowDate);
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
    public OfcFinanceInformation getOfcFinanceInformation() throws Exception {
        if (createOrderEntity != null) {
            this.ofcFinanceInformation = new OfcFinanceInformation();
            ofcFinanceInformation.setOrderCode(this.order_code);
            ofcFinanceInformation.setServiceCharge(ofcFinanceInformation.getServiceCharge());
            ofcFinanceInformation.setOrderAmount(new BigDecimal(createOrderEntity.getOrder_amount()));
            ofcFinanceInformation.setPaymentAmount(new BigDecimal(createOrderEntity.getPayment_amount()));
            ofcFinanceInformation.setCollectLoanAmount(new BigDecimal(createOrderEntity.getCollect_loan_amount()));
            ofcFinanceInformation.setCollectServiceCharge(new BigDecimal(createOrderEntity.getCollect_service_charge()));
            ofcFinanceInformation.setCollectFlag(createOrderEntity.getCollect_flag());
            ofcFinanceInformation.setPrintInvoice(createOrderEntity.getPrint_invoice());
            ofcFinanceInformation.setBuyerPaymentMethod(createOrderEntity.getBuyer_payment_me());
            ofcFinanceInformation.setInsure(createOrderEntity.getInsure());
            ofcFinanceInformation.setInsureValue(createOrderEntity.getInsure_value());

            ofcFinanceInformation.setCreationTime(nowDate);
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
    public OfcWarehouseInformation getOfcWarehouseInformation() throws Exception {
        if (createOrderEntity != null) {
            this.ofcWarehouseInformation = new OfcWarehouseInformation();
            ofcWarehouseInformation.setOrderCode(this.order_code);
            ofcWarehouseInformation.setWarehouseCode(createOrderEntity.getWarehouse_code());
            ofcWarehouseInformation.setWarehouseName(createOrderEntity.getWarehouse_name());
            ofcWarehouseInformation.setSupportName(createOrderEntity.getSupport_name());
            ofcWarehouseInformation.setProvideTransport(Integer.valueOf(createOrderEntity.getProvide_transport()));
            ofcWarehouseInformation.setArriveTime(DateUtils.String2Date(createOrderEntity.getArrive_time(), DateUtils.DateFormatType.TYPE1));
            ofcWarehouseInformation.setPlateNumber(createOrderEntity.getPlate_number());
            ofcWarehouseInformation.setDriverName(createOrderEntity.getDriver_name());

            ofcWarehouseInformation.setCreationTime(nowDate);
            return ofcWarehouseInformation;
        }
        return null;
    }

}
