package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.dto.QueryStoreDto;
import com.xescm.csc.model.dto.QueryWarehouseDto;
import com.xescm.csc.model.dto.warehouse.CscWarehouseDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.model.vo.CscStorevo;
import com.xescm.csc.provider.*;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.model.dto.coo.CreateOrderEntity;
import com.xescm.ofc.model.dto.coo.CreateOrderGoodsInfo;
import com.xescm.ofc.model.dto.coo.CreateOrderTrans;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.rmc.edas.domain.vo.RmcAddressNameVo;
import com.xescm.rmc.edas.service.RmcAddressEdasService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
    private CscCustomerEdasService cscCustomerEdasService;
    @Autowired
    private OfcOrderManageService ofcOrderManageService;
    @Autowired
    private CscWarehouseEdasService cscWarehouseEdasService;
    @Autowired
    private CscStoreEdasService cscStoreEdasService;
    @Autowired
    private CscSupplierEdasService cscSupplierEdasService;
    @Autowired
    private CscGoodsEdasService cscGoodsEdasService;
    @Autowired
    private RmcAddressEdasService rmcAddressEdasService;
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
            logger.error("校验数据{}失败：{}", "货主名称", custName);
            return new ResultModel(ResultModel.ResultEnum.CODE_0008);
        }

        QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
        queryCustomerCodeDto.setCustomerCode(custCode);
        Wrapper<CscCustomerVo> customerVoWrapper = (Wrapper<CscCustomerVo>)cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
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
        Wrapper<List<CscStorevo>> cscStoreVoList = (Wrapper<List<CscStorevo>>)cscStoreEdasService.getStoreByCustomerId(storeDto);
        if (!CollectionUtils.isEmpty(cscStoreVoList.getResult())) {
            logger.info("获取该客户下的店铺编码接口返回成功，custCode:{},接口返回值:{}", custCode, ToStringBuilder.reflectionToString(cscStoreVoList));
            CscStorevo cscStorevo = cscStoreVoList.getResult().get(0);
            storeCode = cscStorevo.getStoreCode();
            storeName = cscStorevo.getStoreName();
        } else {
            logger.error("获取该客户下的店铺编码接口返回失败，custCode:{},接口返回值:{}", custCode, ToStringBuilder.reflectionToString(cscStoreVoList));
            resultModel = new ResultModel(ResultModel.ResultEnum.CODE_0003);
            return resultModel;
        }
        createOrderEntity.setStoreCode(storeCode);

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
        QueryWarehouseDto cscWarehouse = new QueryWarehouseDto();
        cscWarehouse.setCustomerCode(custCode);
        Wrapper<List<CscWarehouseDto>> cscWarehouseByCustomerId = (Wrapper<List<CscWarehouseDto>>)cscWarehouseEdasService.getCscWarehouseByCustomerId(cscWarehouse);
        resultModel = CheckUtils.checkWarehouseCode(cscWarehouseByCustomerId, warehouseCode, orderType);
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.error("校验数据{}失败：{}", "仓库编码", resultModel.getCode());
            return resultModel;
        }

        //供应商
        //checkSupport(createOrderEntity, custCode);

        //校验：货品档案信息  如果是不是运输类型（60），校验货品明细
        if (!StringUtils.equals("60", orderType)) {
            List<CreateOrderGoodsInfo> createOrderGoodsInfos = createOrderEntity.getCreateOrderGoodsInfos();
            for (CreateOrderGoodsInfo createOrderGoodsInfo : createOrderGoodsInfos) {
                CscGoodsApiDto cscGoods = new CscGoodsApiDto();
                cscGoods.setCustomerCode(custCode);
                Wrapper<List<CscGoodsApiVo>> cscGoodsVoWrapper = (Wrapper<List<CscGoodsApiVo>>)cscGoodsEdasService.queryCscGoodsList(cscGoods);
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
            CscContact cscContactDto = new CscContact();
            cscContactDto.setPurpose("1");
            cscContactDto.setContactCode(createOrderEntity.getConsignorName());
            cscContactDto.setContactName(createOrderEntity.getConsignorContact());
            cscContactDto.setPhone(createOrderEntity.getConsignorPhone());
            cscContactDto.setProvinceName(createOrderEntity.getConsignorProvince());
            cscContactDto.setProvince(provinceCode);
            cscContactDto.setCityName(createOrderEntity.getConsignorCity());
            cscContactDto.setCity(cityCode);
            cscContactDto.setAreaName(createOrderEntity.getConsignorCounty());
            cscContactDto.setArea(districtCode);
            cscContactDto.setStreetName(createOrderEntity.getConsignorTown());
            cscContactDto.setDetailAddress(createOrderEntity.getConsignorAddress());
            cscContantAndCompanyVo.setCscContact(cscContactDto);
            CscContactCompany cscContactCompanyDto = new CscContactCompany();
            cscContactCompanyDto.setContactCompanyName(createOrderEntity.getConsignorName());
            cscContantAndCompanyVo.setCscContactCompany(cscContactCompanyDto);
        } else if (StringUtils.equals("2", purpose)) {
            AddressDto addressDto = new AddressDto();
            addressDto.setProvinceName(createOrderEntity.getConsignorProvince());
            addressDto.setCityName(createOrderEntity.getConsignorCity());
            addressDto.setDistrictName(createOrderEntity.getConsignorCounty());
            Map<String, String> addressMap = getAddressCode(addressDto);
            String provinceCode = addressMap.get("provinceCode");
            String cityCode = addressMap.get("cityCode");
            String districtCode = addressMap.get("districtCode");
            CscContact cscContactDto = new CscContact();
            cscContactDto.setPurpose("2");
            cscContactDto.setContactCode(createOrderEntity.getConsigneeName());
            cscContactDto.setContactName(createOrderEntity.getConsigneeContact());
            cscContactDto.setPhone(createOrderEntity.getConsigneePhone());
            cscContactDto.setProvinceName(createOrderEntity.getConsigneeProvince());
            cscContactDto.setProvince(provinceCode);
            cscContactDto.setCityName(createOrderEntity.getConsigneeCity());
            cscContactDto.setCity(cityCode);
            cscContactDto.setAreaName(createOrderEntity.getConsigneeCounty());
            cscContactDto.setArea(districtCode);
            cscContactDto.setStreetName(createOrderEntity.getConsigneeTown());
            cscContactDto.setDetailAddress(createOrderEntity.getConsigneeAddress());
            cscContantAndCompanyVo.setCscContact(cscContactDto);
            CscContactCompany cscContactCompanyDto = new CscContactCompany();
            cscContactCompanyDto.setContactCompanyName(createOrderEntity.getConsigneeName());
            cscContantAndCompanyVo.setCscContactCompany(cscContactCompanyDto);
        }
        Wrapper<?> wrapper = feignCscCustomerAPIClient.addCscContantAndCompany(cscContantAndCompanyVo);
        logger.info("创建收发货方信息：{}", wrapper.getMessage());
    }*/



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
        //订单记录表只添加不修改
        //插入或更新订单中心基本信息
        String custOrderCode = ofcFundamentalInformation.getCustOrderCode();
        String custCode = ofcFundamentalInformation.getCustCode();
        //根据客户订单编号与货主代码查询是否已经存在订单
        OfcFundamentalInformation information = ofcFundamentalInformationService.queryOfcFundInfoByCustOrderCodeAndCustCode(custOrderCode, custCode);
        if (information != null) {
            String orderCode = information.getOrderCode();
            OfcOrderStatus queryOrderStatus = ofcOrderStatusService.queryLastUpdateOrderByOrderCode(orderCode);
            //订单已存在,获取订单的最新状态,只有待审核的才能更新
            if (queryOrderStatus != null && !StringUtils.equals(queryOrderStatus.getOrderCode(), PENDINGAUDIT)) {
                logger.error("订单已经审核custOrderCode:{},custCode:{}", custOrderCode, custCode);
                return new ResultModel(ResultModel.ResultEnum.CODE_1001);
            }
            ofcFundamentalInformation.setOrderCode(orderCode);
            ofcDistributionBasicInfo.setOrderCode(orderCode);
            ofcWarehouseInformation.setOrderCode(orderCode);
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
            ofcDistributionBasicInfoService.update(ofcDistributionBasicInfo);
            ofcWarehouseInformationService.update(ofcWarehouseInformation);
            ofcFinanceInformationService.update(ofcFinanceInformation);
            ofcGoodsDetailsInfoService.deleteAllByOrderCode(orderCode);
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfoList) {
                ofcGoodsDetailsInfo.setOrderCode(orderCode);
                ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            }
            ofcOrderStatusService.save(ofcOrderStatus);
            try {
                //自动审核通过 review:审核；rereview:反审核
                orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList);
            } catch (BusinessException ex) {
                logger.error("自动审核异常，{}", ex);
                throw new BusinessException("自动审核异常", ex);
            }
            return new ResultModel(ResultModel.ResultEnum.CODE_0000);
        } else {
            ofcFundamentalInformationService.save(ofcFundamentalInformation);
            ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
            ofcWarehouseInformationService.save(ofcWarehouseInformation);
            ofcFinanceInformationService.save(ofcFinanceInformation);
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfoList) {
                ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            }
            ofcOrderStatusService.save(ofcOrderStatus);
            try {
                //自动审核通过 review:审核；rereview:反审核
                orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList);
            } catch (BusinessException ex) {
                logger.error("自动审核异常，{}", ex);
                throw new BusinessException("自动审核异常", ex);
            }
            return new ResultModel(ResultModel.ResultEnum.CODE_0000);
        }
    }

    /**
     * 自动审核
     * @param ofcFundamentalInformation
     * @param ofcDistributionBasicInfo
     * @param ofcFinanceInformation
     * @param ofcWarehouseInformation
     * @param ofcGoodsDetailsInfoList
     */
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

    /**
     * 根据省市区名称获取编码
     *
     * @param addressDto
     * @return
     */
    public Map<String, String> getAddressCode(RmcAddressNameVo addressDto) {
        if (StringUtils.isBlank(addressDto.getProvinceName())
                || StringUtils.isBlank(addressDto.getCityName())
                || StringUtils.isBlank(addressDto.getDistrictName())) {
            throw new BusinessException("省市区地址信息不全");
        }
        Map<String, String> resuteMap = new HashMap<>();
        Wrapper<?> wrapperResult = rmcAddressEdasService.findCodeByName(addressDto);
        String result = (String) wrapperResult.getResult();
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

}
