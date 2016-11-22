package com.xescm.ofc.service.impl;

import com.xescm.ofc.constant.CreateOrderApiConstant;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.coo.CreateOrderEntity;
import com.xescm.ofc.domain.dto.coo.CreateOrderGoodsInfo;
import com.xescm.ofc.domain.dto.coo.CreateOrderTrans;
import com.xescm.ofc.domain.dto.csc.*;
import com.xescm.ofc.domain.dto.csc.domain.CscContact;
import com.xescm.ofc.domain.dto.csc.domain.CscContactCompany;
import com.xescm.ofc.domain.dto.csc.vo.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.*;
import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.uam.domain.UamUser;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.xescm.ofc.enums.OrderConstEnum.ALREADYEXAMINE;
import static com.xescm.ofc.enums.OrderConstEnum.CREATE_ORDER_BYAPI;
import static com.xescm.ofc.enums.OrderConstEnum.PENDINGAUDIT;

/**
 * Created by hiyond on 2016/11/18.
 */

@Transactional
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

    @Autowired
    private OfcCreateOrderMapper createOrdersMapper;

    @Override
    public int queryCountByOrderStatus(String orderCode, String orderStatus) {
        return createOrdersMapper.queryCountByOrderStatus(orderCode, orderStatus);
    }

    public ResultModel ofcCreateOrder(CreateOrderEntity createOrderEntity, String orderCode) throws BusinessException {
        ResultModel resultModel = null;
        //校验数据：货主编码 对应客户中心的custId
        String custCode = createOrderEntity.getCustCode();
        String custName = createOrderEntity.getCustName();
        String custId = custCode;
        String groupId = null;
        //校验货主编码
        resultModel = CheckUtils.checkCustCode(custCode);
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.info("校验数据{}失败：{}", "货主编码", resultModel.getCode());
            return resultModel;
        }
        //校验货主名称
        if (StringUtils.isBlank(custName)) {
            return new ResultModel(ResultModel.ResultEnum.CODE_0008);
        }

        QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
        queryCustomerCodeDto.setId(custId);
        Wrapper<CscCustomerVo> customerVoWrapper = feignCscCustomerAPIClient.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
        if (customerVoWrapper.getResult() == null) {
            return new ResultModel(ResultModel.ResultEnum.CODE_0009);
        }
        CscCustomerVo cscCustomerVo = customerVoWrapper.getResult();
        groupId = cscCustomerVo.getGroupId();


        //校验数据：订单类型
        String orderType = createOrderEntity.getOrderType();
        resultModel = CheckUtils.checkOrderType(orderType);
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.info("校验数据{}失败：{}", "订单类型", resultModel.getCode());
            return resultModel;
        }
        //校验：业务类型
        String businessType = createOrderEntity.getBusinessType();
        resultModel = CheckUtils.checkBusinessType(orderType, businessType);
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.info("校验数据{}失败：{}", "业务类型", resultModel.getCode());
            return resultModel;
        }
        //check 数量、重量、体积 三选一不能为空
        resultModel = CheckUtils.checkQuantityAndWeightAndCubage(createOrderEntity.getQuantity(), createOrderEntity.getWeight(), createOrderEntity.getCubage());
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.info("校验数据{}失败：{}", "数量、重量、体积 三选一不能为空", resultModel.getCode());
            return resultModel;
        }
        //校验：店铺编码
        String storeCode = createOrderEntity.getStoreCode();
        storeCode = StringUtils.isBlank(storeCode) ? "20161122000001" : storeCode;
        QueryStoreDto storeDto = new QueryStoreDto();
        storeDto.setCustomerId(custId);
        Wrapper<List<CscStorevo>> cscWarehouseList = feignCscStoreAPIClient.getStoreByCustomerId(storeDto);
        resultModel = CheckUtils.checkStoreCode(cscWarehouseList, storeCode);
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.info("校验数据{}失败：{}", "店铺编码", resultModel.getCode());
            return resultModel;
        }
        //校验：【发货方】与【收货方】
        resultModel = CheckUtils.checkWaresDist(orderType, createOrderEntity.getConsignorName(), createOrderEntity.getConsignorContact(),
                createOrderEntity.getConsignorPhone(), createOrderEntity.getConsignorAddress(), createOrderEntity.getConsigneeName(),
                createOrderEntity.getConsigneeContact(), createOrderEntity.getConsigneePhone(), createOrderEntity.getConsigneeAddress(),
                createOrderEntity.getProvideTransport());
        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            logger.info("校验数据{}失败：{}", "发货方与收货方", resultModel.getCode());
            return resultModel;
        }

        //收发货方的保存规则
        CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
        cscContantAndCompanyDto.setCustomerId(custId);
        cscContantAndCompanyDto.setUserId(CreateOrderApiConstant.USERID);
        cscContantAndCompanyDto.setUserName(CreateOrderApiConstant.USERNAME);
        cscContantAndCompanyDto.setCscContact(new CscContact());
        cscContantAndCompanyDto.setCscContactCompany(new CscContactCompany());
        cscContantAndCompanyDto.getCscContact().setPurpose("1");
        cscContantAndCompanyDto.getCscContact().setContactName(createOrderEntity.getConsignorName());
        cscContantAndCompanyDto.setCustomerId(custId);
        cscContantAndCompanyDto.setGroupId(groupId);
        Wrapper<List<CscContantAndCompanyVo>> cscReceivingInfoList = feignCscCustomerAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
        if (cscReceivingInfoList.getResult() == null || cscReceivingInfoList.getResult().isEmpty()) {
            addCscContantAndCompanyDto("1", custId, createOrderEntity, groupId);
        }

        //收发货方的保存规则
        cscContantAndCompanyDto = new CscContantAndCompanyDto();
        cscContantAndCompanyDto.setCustomerId(custId);
        cscContantAndCompanyDto.setUserId(CreateOrderApiConstant.USERID);
        cscContantAndCompanyDto.setUserName(CreateOrderApiConstant.USERNAME);
        cscContantAndCompanyDto.setCscContact(new CscContact());
        cscContantAndCompanyDto.setCscContactCompany(new CscContactCompany());
        cscContantAndCompanyDto.getCscContact().setPurpose("2");
        cscContantAndCompanyDto.getCscContact().setContactName(createOrderEntity.getConsigneeName());
        cscContantAndCompanyDto.setCustomerId(custId);
        cscContantAndCompanyDto.setGroupId(groupId);
        cscReceivingInfoList = feignCscCustomerAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
        if (cscReceivingInfoList.getResult() == null || cscReceivingInfoList.getResult().isEmpty()) {
            addCscContantAndCompanyDto("2", custId, createOrderEntity, groupId);
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
            return resultModel;
        }

        //供应商
        String supportName = createOrderEntity.getSupportName();
        CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
        cscSupplierInfoDto.setCustomerId(custId);
        cscSupplierInfoDto.setSupplierCode(supportName);
        Wrapper<List<CscSupplierInfoDto>> listWrapper = feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
        String supportCode = CheckUtils.checkSupport(listWrapper, supportName);
        if (StringUtils.isBlank(supportCode)) {
            cscSupplierInfoDto.setCustomerId(custId);
            cscSupplierInfoDto.setUserId(CreateOrderApiConstant.USERID);
            cscSupplierInfoDto.setUserName(CreateOrderApiConstant.USERNAME);
            cscSupplierInfoDto.setProvinceName(createOrderEntity.getSupportProvince());
            cscSupplierInfoDto.setCityName(createOrderEntity.getSupportCity());
            cscSupplierInfoDto.setAreaName(createOrderEntity.getSupportCounty());
            cscSupplierInfoDto.setStreetName(createOrderEntity.getSupportTown());
            cscSupplierInfoDto.setAddress(createOrderEntity.getSupportAddress());
            cscSupplierInfoDto.setEmail(createOrderEntity.getSupportEmail());
            cscSupplierInfoDto.setContactName(createOrderEntity.getSupportContact());
            cscSupplierInfoDto.setContactPhone(createOrderEntity.getSupportPhone());
            feignCscSupplierAPIClient.addSupplierBySupplierCode(cscSupplierInfoDto);
        }

        //校验：货品档案信息
        List<CreateOrderGoodsInfo> createOrderGoodsInfos = createOrderEntity.getCreateOrderGoodsInfos();
        for (CreateOrderGoodsInfo createOrderGoodsInfo : createOrderGoodsInfos) {
            CscGoodsApiDto cscGoods = new CscGoodsApiDto();
            cscGoods.setCustomerId(custId);
            Wrapper<List<CscGoodsApiVo>> cscGoodsVoWrapper = feignCscGoodsAPIClient.queryCscGoodsList(cscGoods);
            resultModel = CheckUtils.checkGoodsInfo(cscGoodsVoWrapper, createOrderGoodsInfo);
            if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                logger.info("校验数据：{}货品编码：{}失败：{}", "货品档案信息", createOrderGoodsInfo.getGoodsCode(), resultModel.getCode());
                return resultModel;
            }
        }

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
            logger.info("校验数据成功，执行创单操作成功；orderCode:{}", orderCode);
        }
        return resultModel;
    }


    /**
     * 保存收货信息
     *
     * @param purpose
     * @param custId
     * @param createOrderEntity
     */
    public void addCscContantAndCompanyDto(String purpose, String custId, CreateOrderEntity createOrderEntity, String groupId) {
        CscContantAndCompanyDto cscContantAndCompanyVo = new CscContantAndCompanyDto();
        cscContantAndCompanyVo.setGroupId(groupId);
        cscContantAndCompanyVo.setCustomerId(custId);
        cscContantAndCompanyVo.setUserId(CreateOrderApiConstant.USERID);
        cscContantAndCompanyVo.setUserName(CreateOrderApiConstant.USERNAME);
        if (StringUtils.equals("1", purpose)) {
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
        } else if (StringUtils.equals("2", purpose)) {
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
        }
        feignCscCustomerAPIClient.addCscContantAndCompany(cscContantAndCompanyVo);
    }

    public ResultModel createOrders(OfcFundamentalInformation ofcFundamentalInformation,
                                    OfcDistributionBasicInfo ofcDistributionBasicInfo,
                                    OfcFinanceInformation ofcFinanceInformation,
                                    OfcWarehouseInformation ofcWarehouseInformation,
                                    List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList) throws BusinessException {
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
//        ofcOrderManageService.orderAudit(orderCode, PENDINGAUDIT, "review", authResDto);
        return new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }

}
