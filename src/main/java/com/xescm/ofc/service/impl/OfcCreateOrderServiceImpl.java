package com.xescm.ofc.service.impl;

import com.xescm.ofc.constant.CreateOrderApiConstant;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.*;
import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.model.dto.coo.CreateOrderEntity;
import com.xescm.ofc.model.dto.coo.CreateOrderGoodsInfo;
import com.xescm.ofc.model.dto.coo.CreateOrderTrans;
import com.xescm.ofc.model.dto.csc.*;
import com.xescm.ofc.model.dto.wms.AddressDto;
import com.xescm.ofc.model.vo.csc.CscCustomerVo;
import com.xescm.ofc.model.vo.csc.CscGoodsApiVo;
import com.xescm.ofc.model.vo.csc.CscStorevo;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xescm.ofc.constant.OrderConstConstant.CREATE_ORDER_BYAPI;
import static com.xescm.ofc.constant.OrderConstConstant.PENDINGAUDIT;

@Service
public class OfcCreateOrderServiceImpl implements OfcCreateOrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

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
    private FeignAddressCodeClient feignAddressCodeClient;
    @Autowired
    private OfcCreateOrderMapper createOrdersMapper;

    @Override
    public int queryCountByOrderStatus(String orderCode, String orderStatus) {
        return createOrdersMapper.queryCountByOrderStatus(orderCode, orderStatus);
    }

    @Transactional
    public ResultModel ofcCreateOrder(CreateOrderEntity createOrderEntity, String orderCode) throws BusinessException {
        ResultModel resultModel = null;
        //校验数据：货主编码 对应客户中心的custId
        String custCode = createOrderEntity.getCustCode();
        String custName = createOrderEntity.getCustName();
        //校验货主编码
        resultModel = CheckUtils.checkCustCode(custCode);
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.error("校验数据{}失败：{}", "货主编码", resultModel.getCode());
            return resultModel;
        }
        //校验货主名称
        if (StringUtils.isBlank(custName)) {
            return new ResultModel(ResultModel.ResultEnum.CODE_0008);
        }

        QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
        queryCustomerCodeDto.setCustomerCode(custCode);
        Wrapper<CscCustomerVo> customerVoWrapper = feignCscCustomerAPIClient.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
        if (customerVoWrapper.getResult() == null) {
            logger.error("获取货主信息失败：custId:{}，{}", custCode, customerVoWrapper.getMessage());
            return new ResultModel(ResultModel.ResultEnum.CODE_0009);
        }

        //校验数据：订单类型
        String orderType = createOrderEntity.getOrderType();
        resultModel = CheckUtils.checkOrderType(orderType);
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.error("校验数据{}失败：{},订单类型：{}", "订单类型", resultModel.getCode(), orderType);
            return resultModel;
        }
        //校验：业务类型
        String businessType = createOrderEntity.getBusinessType();
        resultModel = CheckUtils.checkBusinessType(orderType, businessType);
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.error("校验数据{}失败：{}，订单类型,{},业务类型:{}", "业务类型", resultModel.getCode(), orderType, businessType);
            return resultModel;
        }
        //check 数量、重量、体积 三选一不能为空
        resultModel = CheckUtils.checkQuantityAndWeightAndCubage(createOrderEntity.getQuantity(), createOrderEntity.getWeight(), createOrderEntity.getCubage());
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.error("校验数据{}失败：{}", "数量、重量、体积 三选一不能为空", resultModel.getCode());
            return resultModel;
        }
        //校验：店铺编码，获取该客户下的店铺编码
        String storeCode = createOrderEntity.getStoreCode();
        //店铺名称
        String storeName = null;
        QueryStoreDto storeDto = new QueryStoreDto();
        storeDto.setCustomerCode(custCode);
        Wrapper<List<CscStorevo>> cscStoreVoList = feignCscStoreAPIClient.getStoreByCustomerId(storeDto);
        if (!CollectionUtils.isEmpty(cscStoreVoList.getResult())) {
            CscStorevo cscStorevo = cscStoreVoList.getResult().get(0);
            storeCode = cscStorevo.getStoreCode();
            storeName = cscStorevo.getStoreName();
        } else {
            resultModel = new ResultModel(ResultModel.ResultEnum.CODE_0003);
            return resultModel;
        }
        createOrderEntity.setStoreCode(storeCode);

//          不校验店铺编码 date:2016-11
//        resultModel = CheckUtils.checkStoreCode(cscStoreVoList, storeCode);
//        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
//            logger.info("校验数据{}失败：{},店铺编码:{}", "店铺编码", resultModel.getCode(), storeCode);
//            return resultModel;
//        }

        //校验：【发货方】与【收货方】
        resultModel = CheckUtils.checkWaresDist(orderType, createOrderEntity.getConsignorName(), createOrderEntity.getConsignorContact(),
                createOrderEntity.getConsignorPhone(), createOrderEntity.getConsignorAddress(), createOrderEntity.getConsigneeName(),
                createOrderEntity.getConsigneeContact(), createOrderEntity.getConsigneePhone(), createOrderEntity.getConsigneeAddress(),
                createOrderEntity.getProvideTransport());
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.error("校验数据{}失败：{}", "发货方与收货方", resultModel.getCode());
            return resultModel;
        }

        //仓库编码
        String warehouseCode = createOrderEntity.getWarehouseCode();
        CscWarehouse cscWarehouse = new CscWarehouse();
        cscWarehouse.setCustomerCode(custCode);
        cscWarehouse.setWarehouseCode(warehouseCode);
        Wrapper<List<CscWarehouse>> cscWarehouseByCustomerId = feignCscWarehouseAPIClient.getCscWarehouseByCustomerId(cscWarehouse);
        resultModel = CheckUtils.checkWarehouseCode(cscWarehouseByCustomerId, warehouseCode, orderType);
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.error("校验数据{}失败：{}", "仓库编码", resultModel.getCode());
            return resultModel;
        }

        //供应商
        String supportName = createOrderEntity.getSupportName();
        CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
        cscSupplierInfoDto.setCustomerCode(custCode);
        cscSupplierInfoDto.setSupplierCode(supportName);
        Wrapper<List<CscSupplierInfoDto>> listWrapper = feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
        String supportCode = CheckUtils.checkSupport(listWrapper, supportName);
        if (StringUtils.isBlank(supportCode)) {
            addSupplier(createOrderEntity, cscSupplierInfoDto, custCode);
        }

        //校验：货品档案信息  如果是不是运输类型（60），校验货品明细
        if (!StringUtils.equals("60", orderType)) {
            List<CreateOrderGoodsInfo> createOrderGoodsInfos = createOrderEntity.getCreateOrderGoodsInfos();
            for (CreateOrderGoodsInfo createOrderGoodsInfo : createOrderGoodsInfos) {
                CscGoodsApiDto cscGoods = new CscGoodsApiDto();
                cscGoods.setCustomerCode(custCode);
                Wrapper<List<CscGoodsApiVo>> cscGoodsVoWrapper = feignCscGoodsAPIClient.queryCscGoodsList(cscGoods);
                resultModel = CheckUtils.checkGoodsInfo(cscGoodsVoWrapper, createOrderGoodsInfo);
                if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                    logger.error("校验数据：{}货品编码：{}失败：{}", "货品档案信息", createOrderGoodsInfo.getGoodsCode(), resultModel.getCode());
                    return resultModel;
                }
            }
        }

        //转换 dto → do

        CreateOrderTrans createOrderTrans = new CreateOrderTrans(createOrderEntity, orderCode);
        OfcFundamentalInformation ofcFundamentalInformation = createOrderTrans.getOfcFundamentalInformation();
        ofcFundamentalInformation.setStoreName(storeName);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = createOrderTrans.getOfcDistributionBasicInfo();
        OfcFinanceInformation ofcFinanceInformation = createOrderTrans.getOfcFinanceInformation();
        OfcWarehouseInformation ofcWarehouseInformation = createOrderTrans.getOfcWarehouseInformation();
        OfcOrderStatus ofcOrderStatus = createOrderTrans.getOfcOrderStatus();
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = createOrderTrans.getOfcGoodsDetailsInfoList();
        resultModel = createOrders(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
        if (StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            //操作成功
            logger.info("校验数据成功，执行创单操作成功；orderCode:{}", orderCode);
        }
        return resultModel;
    }

    /**
     * 保存供应商信息
     */
    public void addSupplier(CreateOrderEntity createOrderEntity, CscSupplierInfoDto cscSupplierInfoDto, String custId) {
        cscSupplierInfoDto.setCustomerCode(custId);
        cscSupplierInfoDto.setUserId(CreateOrderApiConstant.USER_ID);
        cscSupplierInfoDto.setUserName(CreateOrderApiConstant.USER_NAME);
        cscSupplierInfoDto.setProvinceName(createOrderEntity.getSupportProvince());
        cscSupplierInfoDto.setCityName(createOrderEntity.getSupportCity());
        cscSupplierInfoDto.setAreaName(createOrderEntity.getSupportCounty());
        cscSupplierInfoDto.setStreetName(createOrderEntity.getSupportTown());
        cscSupplierInfoDto.setAddress(createOrderEntity.getSupportAddress());
        cscSupplierInfoDto.setEmail(createOrderEntity.getSupportEmail());
        cscSupplierInfoDto.setContactName(createOrderEntity.getSupportContact());
        cscSupplierInfoDto.setContactPhone(createOrderEntity.getSupportPhone());
        Wrapper<?> wrapper = feignCscSupplierAPIClient.addSupplierBySupplierCode(cscSupplierInfoDto);
        logger.info("创建供应商：{}", wrapper.getCode());
    }

    /**
     * 保存收货信息
     * （收发货信息不维护到客户中心 date:2016-11-20）
     *
     * @param purpose
     * @param custId
     * @param createOrderEntity
     */
    /*public void addCscContantAndCompanyDto(String purpose, String custId, CreateOrderEntity createOrderEntity, String groupId) {
        CscContantAndCompanyDto cscContantAndCompanyVo = new CscContantAndCompanyDto();
        cscContantAndCompanyVo.setGroupId(groupId);
        cscContantAndCompanyVo.setCustomerId(custId);
        cscContantAndCompanyVo.setUserId(CreateOrderApiConstant.USER_ID);
        cscContantAndCompanyVo.setUserName(CreateOrderApiConstant.USER_NAME);
        if (StringUtils.equals("1", purpose)) {
            AddressDto addressDto = new AddressDto();
            addressDto.setProvinceName(createOrderEntity.getConsignorProvince());
            addressDto.setCityName(createOrderEntity.getConsignorCity());
            addressDto.setDistrictName(createOrderEntity.getConsignorCounty());
            Map<String, String> addressMap = getAddressCode(addressDto);
            String provinceCode = addressMap.get("provinceCode");
            String cityCode = addressMap.get("cityCode");
            String districtCode = addressMap.get("districtCode");
            CscContact cscContact = new CscContact();
            cscContact.setPurpose("1");
            cscContact.setContactCode(createOrderEntity.getConsignorName());
            cscContact.setContactName(createOrderEntity.getConsignorContact());
            cscContact.setPhone(createOrderEntity.getConsignorPhone());
            cscContact.setProvinceName(createOrderEntity.getConsignorProvince());
            cscContact.setProvince(provinceCode);
            cscContact.setCityName(createOrderEntity.getConsignorCity());
            cscContact.setCity(cityCode);
            cscContact.setAreaName(createOrderEntity.getConsignorCounty());
            cscContact.setArea(districtCode);
            cscContact.setStreetName(createOrderEntity.getConsignorTown());
            cscContact.setDetailAddress(createOrderEntity.getConsignorAddress());
            cscContantAndCompanyVo.setCscContact(cscContact);
            CscContactCompany cscContactCompany = new CscContactCompany();
            cscContactCompany.setContactCompanyName(createOrderEntity.getConsignorName());
            cscContantAndCompanyVo.setCscContactCompany(cscContactCompany);
        } else if (StringUtils.equals("2", purpose)) {
            AddressDto addressDto = new AddressDto();
            addressDto.setProvinceName(createOrderEntity.getConsignorProvince());
            addressDto.setCityName(createOrderEntity.getConsignorCity());
            addressDto.setDistrictName(createOrderEntity.getConsignorCounty());
            Map<String, String> addressMap = getAddressCode(addressDto);
            String provinceCode = addressMap.get("provinceCode");
            String cityCode = addressMap.get("cityCode");
            String districtCode = addressMap.get("districtCode");
            CscContact cscContact = new CscContact();
            cscContact.setPurpose("2");
            cscContact.setContactCode(createOrderEntity.getConsigneeName());
            cscContact.setContactName(createOrderEntity.getConsigneeContact());
            cscContact.setPhone(createOrderEntity.getConsigneePhone());
            cscContact.setProvinceName(createOrderEntity.getConsigneeProvince());
            cscContact.setProvince(provinceCode);
            cscContact.setCityName(createOrderEntity.getConsigneeCity());
            cscContact.setCity(cityCode);
            cscContact.setAreaName(createOrderEntity.getConsigneeCounty());
            cscContact.setArea(districtCode);
            cscContact.setStreetName(createOrderEntity.getConsigneeTown());
            cscContact.setDetailAddress(createOrderEntity.getConsigneeAddress());
            cscContantAndCompanyVo.setCscContact(cscContact);
            CscContactCompany cscContactCompany = new CscContactCompany();
            cscContactCompany.setContactCompanyName(createOrderEntity.getConsigneeName());
            cscContantAndCompanyVo.setCscContactCompany(cscContactCompany);
        }
        Wrapper<?> wrapper = feignCscCustomerAPIClient.addCscContantAndCompany(cscContantAndCompanyVo);
        logger.info("创建收发货方信息：{}", wrapper.getMessage());
    }*/

    /**
     * 获取地区编码
     *
     * @param addressDto
     * @return
     */
    public Map<String, String> getAddressCode(AddressDto addressDto) {
        if (StringUtils.isBlank(addressDto.getProvinceName()) || StringUtils.isBlank(addressDto.getCityName())
                || StringUtils.isBlank(addressDto.getDistrictName())) {
            throw new BusinessException("收货方省市区地址不全");
        }
        Map<String, String> resuteMap = new HashMap<>();
        String result = feignAddressCodeClient.findCodeByName(addressDto);
        if (StringUtils.isBlank(result)) {
            throw new BusinessException("无法获取收货方省市区地址编码");
        }
        JSONObject jsonObject = JSONObject.fromObject(JSONObject.fromObject(result).getString("result"));
        String provinceCode = jsonObject.getString("provinceCode");
        String cityCode = jsonObject.getString("cityCode");
        String districtCode = jsonObject.getString("districtCode");
        resuteMap.put("provinceCode", provinceCode);
        resuteMap.put("cityCode", cityCode);
        resuteMap.put("districtCode", districtCode);
        return resuteMap;
    }

    /**
     * 保存到数据库
     * 先根据客户订单编号与货主便编码查询是否已存在订单，
     * 如果存在，判断是否是待审核，如果是待审核进行更改
     * 否则不进行操作
     * 保存或更新订单后调用自动审核的方法
     *
     * @param ofcFundamentalInformation
     * @param ofcDistributionBasicInfo
     * @param ofcFinanceInformation
     * @param ofcWarehouseInformation
     * @param ofcGoodsDetailsInfoList
     * @param ofcOrderStatus
     * @return
     * @throws BusinessException
     */
    @Transactional
    public ResultModel createOrders(OfcFundamentalInformation ofcFundamentalInformation,
                                    OfcDistributionBasicInfo ofcDistributionBasicInfo,
                                    OfcFinanceInformation ofcFinanceInformation,
                                    OfcWarehouseInformation ofcWarehouseInformation,
                                    List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList, OfcOrderStatus ofcOrderStatus) throws BusinessException {
        String orderCode = ofcFundamentalInformation.getOrderCode();
        //订单记录表只添加不修改
        //插入或更新订单中心基本信息
        String custOrderCode = ofcFundamentalInformation.getCustOrderCode();
        String custCode = ofcFundamentalInformation.getCustCode();
        //根据客户订单编号与货主代码查询是否已经存在订单
        int existResult = createOrdersMapper.queryCountByOrderStatus(custOrderCode, custCode);
        if (existResult > 0) {
            //订单已存在,获取订单的最新状态,只有待审核的才能更新
            OfcOrderStatus queryOrderStatus = ofcOrderStatusService.queryLastUpdateOrderByOrderCode(orderCode);
            if (queryOrderStatus != null && StringUtils.equals(queryOrderStatus.getOrderCode(), PENDINGAUDIT)) {
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
                ofcOrderStatusService.save(ofcOrderStatus);
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
            ofcOrderStatusService.save(ofcOrderStatus);
        }
        //自动审核通过 review:审核；rereview:反审核
        try {
            orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList);
        } catch (RuntimeException ex) {
            logger.error("自动审核异常，{}", ex);
            return new ResultModel(ResultModel.ResultEnum.CODE_1000);
        }
        return new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }

    public void orderApply(OfcFundamentalInformation ofcFundamentalInformation,
                           OfcDistributionBasicInfo ofcDistributionBasicInfo,
                           OfcFinanceInformation ofcFinanceInformation,
                           OfcWarehouseInformation ofcWarehouseInformation,
                           List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList) {
        //自动审核通过 review:审核；rereview:反审核
        AuthResDto authResDto = new AuthResDto();
        authResDto.setGroupRefName(CREATE_ORDER_BYAPI);
        Wrapper<?> wrapper = ofcOrderManageService.orderAutoAuditFromOperation(ofcFundamentalInformation, ofcGoodsDetailsInfoList, ofcDistributionBasicInfo, ofcWarehouseInformation, ofcFinanceInformation, PENDINGAUDIT, "review", authResDto);
        logger.info("自动审核操作：" + wrapper.getCode());
    }

}
