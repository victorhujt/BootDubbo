package com.xescm.ofc.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.coo.*;
import com.xescm.ofc.domain.dto.csc.*;
import com.xescm.ofc.domain.dto.csc.domain.CscContact;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsVo;
import com.xescm.ofc.domain.dto.csc.vo.CscStorevo;
import com.xescm.ofc.feign.client.*;
import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.ofc.utils.JsonUtil;
import com.xescm.uam.domain.UamUser;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.xescm.ofc.enums.OrderConstEnum.*;

/**
 * 订单api
 * Created by hiyond on 2016/11/15.
 */
@Service
public class CreateOrderServiceImpl implements CreateOrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OfcCreateOrderMapper createOrdersMapper;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Autowired
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Autowired
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private CodeGenUtils codeGenUtils;
    @Autowired
    private OfcOrderManageService ofcOrderManageService;
    @Autowired
    private FeignCscWarehouseAPIClient feignCscWarehouseAPIClient;
    @Autowired
    private FeignCscStoreAPIClient feignCscStoreAPIClient;
    @Autowired
    private FeignCscSupplierAPIClient feignCscSupplierAPIClient;
    @Autowired
    private FeignCscGoodsAPIClient feignCscGoodsAPIClient;
    @Autowired
    private OfcCreateOrderErrorLogService ofcCreateOrderErrorLogService;


    @Override
    public boolean CreateOrders(List<CreateOrderEntity> list) {
        return false;
    }

    /**
     * 创单api  custOrderCode与typeId是相同的
     *
     * @param data 传入的json格式的字符串
     * @throws Exception
     */
    public String createOrder(String data) throws Exception {
        logger.info("订单中心创建订单接口参数{}", data);
        data = queryJson();
        //组装接口的返回信息
        List<CreateOrderResult> createOrderResultList = null;
        try {
            List<CreateOrderEntity> createOrderEntityList = (List<CreateOrderEntity>) JsonUtil.json2List(data, new TypeReference<List<CreateOrderEntity>>() {
            });
            if (!CollectionUtils.isEmpty(createOrderEntityList)) {

                createOrderResultList = new ArrayList<>();
                ResultModel resultModel;
                //返回结果
                boolean result = false;
                //原因
                String reason = null;
                //客户订单编号
                String custOrderCode;
                //订单编号
                String orderCode = null;
                for (int index = 0; index < createOrderEntityList.size(); index++) {
                    CreateOrderEntity createOrderEntity = createOrderEntityList.get(index);
                    try {

                        custOrderCode = createOrderEntity.getCustOrderCode();

                        //校验数据：货主编码
                        QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
                        String custCode = createOrderEntity.getCustCode();
                        queryCustomerIdDto.setCustomerCode(custCode);
                        Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
                        String custId = (String) wrapper.getResult();
                        resultModel = CheckUtils.checkCustCode(custId);
                        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            logger.info("校验数据{}失败：{}", "货主编码", resultModel.getCode());
                            addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                            throw new Exception("校验数据货主编码失败"+resultModel.getCode());
                        }
//
//                    //校验数据：订单类型
                        String orderType = createOrderEntity.getOrderType();
                        resultModel = CheckUtils.checkOrderType(orderType);
                        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            logger.info("校验数据{}失败：{}", "订单类型", resultModel.getCode());
                            addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                            throw new Exception("校验数据订单类型失败"+resultModel.getCode());
                        }
//
//                    //校验：业务类型
                        String businessType = createOrderEntity.getBusinessType();
                        resultModel = CheckUtils.checkBusinessType(orderType, businessType);
                        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            logger.info("校验数据{}失败：{}", "业务类型", resultModel.getCode());
                            addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                            throw new Exception("校验数据业务类型失败"+resultModel.getCode());
                        }
//
//                    //check 数量、重量、体积 三选一不能为空
                        resultModel = CheckUtils.checkQuantityAndWeightAndCubage(createOrderEntity.getQuantity(), createOrderEntity.getWeight(), createOrderEntity.getCubage());
                        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            logger.info("校验数据{}失败：{}", "数量、重量、体积 三选一不能为空", resultModel.getCode());
                            throw new Exception("校验数据数量、重量、体积失败"+resultModel.getCode());
                        }
                        //校验：店铺编码
                        String storeCode = createOrderEntity.getStoreCode();
                        QueryStoreDto storeDto = new QueryStoreDto();
                        storeDto.setCustomerId(custId);
                        Wrapper<List<CscStorevo>> cscWarehouseList = feignCscStoreAPIClient.getStoreByCustomerId(storeDto);
                        resultModel = CheckUtils.checkStoreCode(cscWarehouseList, storeCode);
                        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            logger.info("校验数据{}失败：{}", "店铺编码", resultModel.getCode());
                            addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                            throw new Exception("校验数据店铺编码失败"+resultModel.getCode());
                        }
//
//                    //校验：【发货方】与【收货方】
                        resultModel = CheckUtils.checkWaresDist(orderType, createOrderEntity.getConsignorName(), createOrderEntity.getConsignorContact(),
                                createOrderEntity.getConsignorPhone(), createOrderEntity.getConsignorAddress(), createOrderEntity.getConsigneeName(),
                                createOrderEntity.getConsigneeContact(), createOrderEntity.getConsigneePhone(), createOrderEntity.getConsigneeAddress(),
                                createOrderEntity.getProvideTransport());
                        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            logger.info("校验数据{}失败：{}", "发货方与收货方", resultModel.getCode());
                            addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                            throw new Exception("校验数据发货方与收货方失败"+resultModel.getCode());
                        }

                        //收发货方的保存规则
                        CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                        cscContantAndCompanyDto.setCustomerId(custId);
                        Wrapper<List<CscContantAndCompanyVo>> cscReceivingInfoList = feignCscCustomerAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
                        if (cscReceivingInfoList.getResult() == null || cscReceivingInfoList.getResult().isEmpty()) {
                            addCscContantAndCompanyDto("1", custId, createOrderEntity);
                        }

                        //仓库编码
                        String warehouseCode = createOrderEntity.getWarehouseCode();
                        CscWarehouse cscWarehouse = new CscWarehouse();
                        cscWarehouse.setCustomerId(custId);
                        cscWarehouse.setWarehouseCode(warehouseCode);
                        Wrapper<List<CscWarehouse>> cscWarehouseByCustomerId = feignCscWarehouseAPIClient.getCscWarehouseByCustomerId(cscWarehouse);
                        resultModel = CheckUtils.checkWarehouseCode(cscWarehouseByCustomerId, warehouseCode, orderType);
                        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            logger.info("校验数据{}失败：{}", "仓库编码", resultModel.getCode());
                            addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                            throw new Exception("校验数据仓库编码失败"+resultModel.getCode());
                        }

                        //供应商
                        String supportName = createOrderEntity.getSupportName();
                        CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
                        cscSupplierInfoDto.setCustomerId(custId);
                        cscSupplierInfoDto.setSupplierCode(supportName);
                        Wrapper<List<CscSupplierInfoDto>> listWrapper = feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
                        String supportCode = CheckUtils.checkSupport(listWrapper, supportName);
                        createOrderEntity.setSupportCode(supportCode);

                        //校验：货品档案信息
                        List<CreateOrderGoodsInfo> createOrderGoodsInfos = createOrderEntity.getCreateOrderGoodsInfos();
                        for (CreateOrderGoodsInfo createOrderGoodsInfo : createOrderGoodsInfos) {
                            CscGoods cscGoods = new CscGoods();
                            Wrapper<List<CscGoodsVo>> cscGoodsVoWrapper = feignCscGoodsAPIClient.queryCscGoodsList(cscGoods);
                            resultModel = CheckUtils.checkGoodsInfo(cscGoodsVoWrapper, createOrderGoodsInfo);
                            if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                                logger.info("校验数据{}失败：{}", "货品档案信息", resultModel.getCode());
                                addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                                throw new Exception("校验数据货品档案信息失败"+resultModel.getCode());
                            }
                        }

                        //生成orderCode
                        orderCode = codeGenUtils.getNewWaterCode("SO", 6);

                        //转换 dto → do
                        CreateOrderTrans createOrderTrans = new CreateOrderTrans(createOrderEntity, orderCode);
                        OfcFundamentalInformation ofcFundamentalInformation = createOrderTrans.getOfcFundamentalInformation();
                        OfcDistributionBasicInfo ofcDistributionBasicInfo = createOrderTrans.getOfcDistributionBasicInfo();
                        OfcFinanceInformation ofcFinanceInformation = createOrderTrans.getOfcFinanceInformation();
                        OfcWarehouseInformation ofcWarehouseInformation = createOrderTrans.getOfcWarehouseInformation();
                        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = createOrderTrans.getOfcGoodsDetailsInfoList();

                        resultModel = createOrders(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList);
                        if (StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            //操作成功
                            result = true;
                        }
                        addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                        logger.info("校验数据成功，执行创单操作成功；orderCode:{}", orderCode);
                        throw new Exception();
                    } catch (Exception ex) {
                        saveErroeLog(createOrderEntity.getCustOrderCode(), createOrderEntity.getCustCode(), createOrderEntity.getOrderTime(), ex);
                        throw new Exception(ex);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("订单中心创建订单接口出错:{},{}", ex.getMessage(), ex);
            throw new Exception(ex);
        } finally {
            //转换返回结果
            if (!CollectionUtils.isEmpty(createOrderResultList)) {
                String code = "200";
                StringBuffer reason = new StringBuffer();
                List<String> typeIdList = new ArrayList<>();
                for (int index = 0; index < createOrderResultList.size(); index++) {
                    CreateOrderResult orderResult = createOrderResultList.get(index);
                    if (orderResult.getResult() == false) {
                        code = "500";
                    }
                    String typeIdAndReason = "typeId:" + orderResult.getTypeId() + "||reason" + orderResult.getReason();
                    if (index != createOrderResultList.size() - 1) {
                        typeIdAndReason = typeIdAndReason + ",";
                    }
                    reason.append(typeIdAndReason);
                    String typeId = "typeId:" + orderResult.getTypeId();
                    typeIdList.add(typeId);
                }
                CreateOrderResultDto createOrderResultDto = new CreateOrderResultDto();
                createOrderResultDto.setCode(code);
                createOrderResultDto.setReason(reason.toString());
                createOrderResultDto.setMessage(typeIdList);

                //要反回的json格式的字符串
                String createOrderResultDtoJson = JsonUtil.object2Json(createOrderResultDto);
                return createOrderResultDtoJson;
            }
            logger.debug("订单中心创建订单接口结束");
        }
        return null;
    }

    /**
     * 组装返回信息封装到 List<CreateOrderResult>
     *
     * @param result                结果
     * @param reason                原因
     * @param custOrderCode         客户订单编号
     * @param orderCode             orderCode
     * @param resultModel           返回的code
     * @param createOrderResultList 返回结果的集合
     */
    private void addCreateOrderEntityList(boolean result, String reason,
                                          String custOrderCode, String orderCode,
                                          ResultModel resultModel,
                                          List<CreateOrderResult> createOrderResultList) {
        reason = resultModel.getDesc();
        CreateOrderResult createOrderResult = new CreateOrderResult(result, reason, custOrderCode, orderCode);
        createOrderResultList.add(createOrderResult);
    }

    @Transactional
    @Override
    public ResultModel createOrders(OfcFundamentalInformation ofcFundamentalInformation,
                                    OfcDistributionBasicInfo ofcDistributionBasicInfo,
                                    OfcFinanceInformation ofcFinanceInformation,
                                    OfcWarehouseInformation ofcWarehouseInformation,
                                    List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList) {
        String orderCode = ofcFundamentalInformation.getOrderCode();

        //订单状态更改为已审核
        String orderStatus = ALREADYEXAMINE;
        //插入或更新订单中心基本信息

        int existResult = createOrdersMapper.queryCountByOrderStatus(orderCode, orderStatus);
        if (existResult > 0) {
            //订单已存在
            //更新订单中心基本信息
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
            //订单中心配送基本信息
            ofcDistributionBasicInfoService.update(ofcDistributionBasicInfo);
            //仓配信息
            ofcWarehouseInformationService.update(ofcWarehouseInformation);
            //财务信息
            ofcFinanceInformationService.update(ofcFinanceInformation);
            //更新货品明细信息 →先根据orderCode删除所有数据，再新增
            ofcGoodsDetailsInfoService.deleteAllByOrderCode(orderCode);
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfoList) {
                ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            }
        } else if (existResult == 0) {
            //新增订单中心基本信息
            ofcFundamentalInformationService.save(ofcFundamentalInformation);
            //订单中心配送基本信息
            ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
            //仓配信息
            ofcWarehouseInformationService.save(ofcWarehouseInformation);
            //财务信息
            ofcFinanceInformationService.save(ofcFinanceInformation);

            //货品明细信息
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfoList) {
                ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            }
        }
        //自动审核通过 review:审核；rereview:反审核
        AuthResDto authResDto = new AuthResDto();
        UamUser uamUser = new UamUser();
        uamUser.setUserName(CREATE_ORDER_BYAPI);
        authResDto.setUamUser(uamUser);
        ofcOrderManageService.orderAudit(orderCode, ALREADYEXAMINE, "review", authResDto);
        return new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }

    @Override
    public ResultModel cancelOrderStateByOrderCode(String custOrderCode, String custCode) {
        String orderCode = ofcFundamentalInformationService.getOrderCodeByCustOrderCodeAndCustCode(custOrderCode, custCode);
        if (StringUtils.isBlank(orderCode)) {
            return new ResultModel("1000", "发货单号不存在");
        }
        OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.queryOrderStateByOrderCode(orderCode);
        String orderState = ofcOrderStatus.getOrderStatus();
        if (StringUtils.equals(orderState, HASBEENCOMPLETED)) {
            return new ResultModel("1000", "订单已完成");
        } else if (StringUtils.equals(orderState, HASBEENCANCELED)) {
            return new ResultModel("1000", "订单已取消");
        } else if (StringUtils.equals(orderState, PENDINGAUDIT) || StringUtils.equals(orderState, ALREADYEXAMINE) || StringUtils.equals(orderState, IMPLEMENTATIONIN)) {
            ofcOrderStatusService.cancelOrderStateByOrderCode(orderCode);
            return new ResultModel(ResultModel.ResultEnum.CODE_0000);
        } else {
            return new ResultModel(ResultModel.ResultEnum.CODE_9999);
        }
    }

    /**
     * 保存收货信息
     *
     * @param purpose
     * @param custId
     * @param createOrderEntity
     */
    @Transactional
    public void addCscContantAndCompanyDto(String purpose, String custId, CreateOrderEntity createOrderEntity) {
        CscContantAndCompanyDto cscContantAndCompanyVo = new CscContantAndCompanyDto();
        if (StringUtils.equals("1", purpose)) {
            cscContantAndCompanyVo.setCustomerId(custId);
            CscContact cscContact = new CscContact();
            cscContact.setPurpose("1");
            cscContact.setContactCode(createOrderEntity.getConsignorName());
            cscContact.setContactName(createOrderEntity.getConsignorContact());
            cscContact.setPhone(createOrderEntity.getConsignorPhone());
            cscContact.setProvinceName(createOrderEntity.getConsignorProvince());
            cscContact.setCityName(createOrderEntity.getConsignorCity());
            cscContact.setAreaName(createOrderEntity.getConsignorCounty());
            cscContact.setStreetName(createOrderEntity.getConsignorTown());
            cscContact.setDetailAddress(createOrderEntity.getConsignorAddress());
            cscContantAndCompanyVo.setCscContact(cscContact);

            cscContantAndCompanyVo.setCscContact(cscContact);
        } else if (StringUtils.equals("2", purpose)) {
            cscContantAndCompanyVo.setCustomerId(custId);
            CscContact cscContact = new CscContact();
            cscContact.setPurpose("2");
            cscContact.setContactCode(createOrderEntity.getConsigneeName());
            cscContact.setContactName(createOrderEntity.getConsigneeContact());
            cscContact.setPhone(createOrderEntity.getConsigneePhone());
            cscContact.setProvinceName(createOrderEntity.getConsigneeProvince());
            cscContact.setCityName(createOrderEntity.getConsigneeCity());
            cscContact.setAreaName(createOrderEntity.getConsigneeCounty());
            cscContact.setStreetName(createOrderEntity.getConsigneeTown());
            cscContact.setDetailAddress(createOrderEntity.getConsigneeAddress());
            cscContantAndCompanyVo.setCscContact(cscContact);

            cscContantAndCompanyVo.setCscContact(cscContact);
        }
        feignCscCustomerAPIClient.addCscContantAndCompany(cscContantAndCompanyVo);
    }

    /**
     * 保存错误信息
     * 不需要回滚
     *
     * @param cCustOrderCode
     * @param custCode
     * @param orderTime
     * @param ex
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public void saveErroeLog(String cCustOrderCode, String custCode, String orderTime, Exception ex) {
        OfcCreateOrderErrorLog ofcCreateOrderErrorLog = new OfcCreateOrderErrorLog();
        ofcCreateOrderErrorLog.setCustOrderCode(cCustOrderCode);
        ofcCreateOrderErrorLog.setCustCode(custCode);
        ofcCreateOrderErrorLog.setOrderTime(DateUtils.String2Date(DateUtils.dateSubStringGetYMD(orderTime), DateUtils.DateFormatType.TYPE2));
        ofcCreateOrderErrorLog.setErrorLog(ex.toString());
        ofcCreateOrderErrorLogService.save(ofcCreateOrderErrorLog);
    }


    public String queryJson() {
        return "[{\n" +
                "        \"custOrderCode\": \"D161115107629044\",\n" +
                "        \"orderTime\": \"2016-11-15\",\n" +
                "        \"custCode\": null,\n" +
                "        \"custName\": null,\n" +
                "        \"orderType\": \"60\",\n" +
                "        \"businessType\": \"600\",\n" +
                "        \"notes\": \"订单备注\",\n" +
                "        \"storeCode\": null,\n" +
                "        \"orderSource\": \"EDI\",\n" +
                "        \"expandSaleOrg\": null,\n" +
                "        \"expandProGroup\": null,\n" +
                "        \"expandSaleDep\": null,\n" +
                "        \"expandSaleGroup\": null,\n" +
                "        \"expandSaleDepDes\": null,\n" +
                "        \"expandSaleGroupDes\": null,\n" +
                "        \"quantity\": \"1111\",\n" +
                "        \"weight\": \"9212.0\",\n" +
                "        \"cubage\": null,\n" +
                "        \"totalStandardBox\": null,\n" +
                "        \"transRequire\": null,\n" +
                "        \"pickupTime\": null,\n" +
                "        \"expectedArrivedTime\": null,\n" +
                "        \"consignorName\": \"鲜易网\",\n" +
                "        \"consignorContact\": \"鲜易网\",\n" +
                "        \"consignorPhone\": \"400-662-6366\",\n" +
                "        \"consignorFax\": null,\n" +
                "        \"consignorEmail\": null,\n" +
                "        \"consignorZip\": null,\n" +
                "        \"consignorProvince\": \"河南\",\n" +
                "        \"consignorCity\": \"郑州\",\n" +
                "        \"consignorCounty\": \"郑州新区\",\n" +
                "        \"consignorTown\": null,\n" +
                "        \"consignorAddress\": \"东风南路七里河路交叉口绿地之窗云峰座\",\n" +
                "        \"consigneeName\": \"李歌\",\n" +
                "        \"consigneeContact\": \"李歌\",\n" +
                "        \"consigneePhone\": \"18637711063\",\n" +
                "        \"consigneeFax\": null,\n" +
                "        \"consigneeEmail\": null,\n" +
                "        \"consigneeZip\": null,\n" +
                "        \"consigneeProvince\": \"河南\",\n" +
                "        \"consigneeCity\": \"南阳市\",\n" +
                "        \"consigneeCounty\": \"宛城区\",\n" +
                "        \"consigneeTown\": null,\n" +
                "        \"consigneeAddress\": \"府衙小吃街\",\n" +
                "        \"warehouseCode\": null,\n" +
                "        \"warehouseName\": null,\n" +
                "        \"provideTransport\": null,\n" +
                "        \"supportName\": null,\n" +
                "        \"supportContact\": null,\n" +
                "        \"supportPhone\": null,\n" +
                "        \"supportFax\": null,\n" +
                "        \"supportEmail\": null,\n" +
                "        \"supportZip\": null,\n" +
                "        \"supportProvince\": null,\n" +
                "        \"supportCity\": null,\n" +
                "        \"supportCounty\": null,\n" +
                "        \"supportTown\": null,\n" +
                "        \"supportAddress\": null,\n" +
                "        \"arriveTime\": null,\n" +
                "        \"plateNumber\": null,\n" +
                "        \"driverName\": null,\n" +
                "        \"contactNumber\": null,\n" +
                "        \"serviceCharge\": null,\n" +
                "        \"orderAmount\": \"1932\",\n" +
                "        \"paymentAmount\": \"1932\",\n" +
                "        \"collectLoanAmount\": \"0\",\n" +
                "        \"collectServiceCharge\": null,\n" +
                "        \"collectFlag\": null,\n" +
                "        \"printInvoice\": null,\n" +
                "        \"buyerPaymentMethod\": \"6830\",\n" +
                "        \"insure\": null,\n" +
                "        \"insureValue\": null,\n" +
                "        \"createOrderGoodsInfos\": [\n" +
                "            {\n" +
                "                \"goodsCode\": null,\n" +
                "                \"goodsName\": \"鱿鱼切半40克 4.35kg/箱\",\n" +
                "                \"goodsSpec\": \"4.35kg/箱\",\n" +
                "                \"unit\": \"箱\",\n" +
                "                \"quantity\": \"21\",\n" +
                "                \"unitPrice\": \"92\",\n" +
                "                \"productionBatch\": null,\n" +
                "                \"productionTime\": null,\n" +
                "                \"invalidTime\": null\n" +
                "            }\n" +
                "        ],\n" +
                "        \"baseId\": \"40551\"\n" +
                "    }]";
    }

}
