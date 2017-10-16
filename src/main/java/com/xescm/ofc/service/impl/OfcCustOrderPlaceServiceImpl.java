package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.annotation.Permission;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcCustOrderNewstatus;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.enums.BusinessTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcGoodsDetailsInfoDTO;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcSaveStorageDTO;
import com.xescm.ofc.service.OfcCustOrderNewstatusService;
import com.xescm.ofc.service.OfcCustOrderPlaceService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.service.OfcOrderPlaceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.*;
import static com.xescm.ofc.enums.OrderStatusOfCustEnum.*;


/**
 *
 * Created by lyh on 2017/9/13.
 */
@Service
public class OfcCustOrderPlaceServiceImpl implements OfcCustOrderPlaceService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private OfcCustOrderNewstatusService ofcCustOrderNewstatusService;


    @Permission
    @Override
    public void saveStorageOrder(OfcSaveStorageDTO ofcSaveStorageDTO, String tag, AuthResDto authResDtoByToken) throws Exception {
        logger.info("客户工作台仓储订单下单或编辑 ofcSaveStorageDTO==>{}, tag==>{}", ofcSaveStorageDTO, tag);
        if (ofcSaveStorageDTO == null) {
            throw new BusinessException("订单的基本信息不能为空");
        }
        if (PubUtils.isSEmptyOrNull(tag)) {
            throw new BusinessException("下单标志不能为空");
        }
        if (!(ORDER_TAG_STOCK_SAVE.equals(tag) || ORDER_TAG_STOCK_EDIT.equals(tag) || ORDER_TAG_STOCK_IMPORT.equals(tag))) {
            throw new BusinessException("下单标志类型错误");
        }
        //货品信息
        List<OfcGoodsDetailsInfoDTO> ofcGoodsDetailsInfos = ofcSaveStorageDTO.getGoodsDetailsInfo();
        if (CollectionUtils.isEmpty(ofcGoodsDetailsInfos)) {
            throw new BusinessException("仓储下单时货品信息不能为空");
        }
        //订单基本信息
        OfcFundamentalInformation ofcFundamentalInformation = ofcSaveStorageDTO.getFundamentalInformation();
        //仓储信息
        OfcWarehouseInformation ofcWarehouseInformation = ofcSaveStorageDTO.getWarehouseInformation();
        //发货人信息
        CscContantAndCompanyDto consignor = ofcSaveStorageDTO.getConsignor();
        //收货人信息
        CscContantAndCompanyDto consignee = ofcSaveStorageDTO.getConsignee();
        //供应商信息
        CscSupplierInfoDto supplier=ofcSaveStorageDTO.getSupplier();
        logger.info("==>仓储开单或编辑实体 OfcSaveStorageDTO={}", JacksonUtil.toJson(ofcSaveStorageDTO));
        // logger.info("==>仓储开单或编辑标志位 tag={}", tag);
        if (ofcWarehouseInformation.getProvideTransport() == 1) {
            if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("61")) {
                if (consignee == null) {
                    throw new BusinessException("需要提供运输时,配送基本信息收货方不能为空");
                }
            } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("62")) {
                if (consignor == null) {
                    throw new BusinessException("需要提供运输时,配送基本信息发货方不能为空");
                }
            }
        }
        //发货方信息
        if (consignor == null) {
            consignor = new CscContantAndCompanyDto();
        }
        if (consignee == null) {
            consignee = new CscContantAndCompanyDto();
        }
        Wrapper<?> result;
        if (StringUtils.equals(tag, ORDER_TAG_STOCK_CUST_EDIT)) {
            OfcCustOrderNewstatus newstatus = ofcCustOrderNewstatusService.queryByOrderCode(ofcFundamentalInformation.getOrderCode());
            String orderLatestStatus = newstatus.getOrderLatestStatus();
            // 未确认
            if (StringUtils.equals(orderLatestStatus, UNCONFIRMED.getCode())) {
                result = ofcOrderManageService.saveStorageOrder(ofcSaveStorageDTO
                        , ofcGoodsDetailsInfos, tag, consignor, consignee, supplier,authResDtoByToken);
                // 待审核
            } else if (StringUtils.equals(orderLatestStatus, PEND_AUDIT.getCode())) {
                result = ofcOrderManageService.saveStorageOrder(ofcSaveStorageDTO
                        , ofcGoodsDetailsInfos, ORDER_TAG_STOCK_EDIT, consignor, consignee, supplier,authResDtoByToken);
            } else {
                throw new BusinessException("订单状态有误");
            }
        } else if (StringUtils.equals(tag, ORDER_TAG_STOCK_CUST_SAVE)) {
            result = ofcOrderManageService.saveStorageOrder(ofcSaveStorageDTO
                    , ofcGoodsDetailsInfos, tag, consignor, consignee, supplier,authResDtoByToken);
        } else {
            throw new BusinessException("订单标志位错误");
        }

        if (result.getCode() != Wrapper.SUCCESS_CODE) {
            if (!org.apache.commons.lang.StringUtils.isEmpty(result.getMessage())) {
                throw new BusinessException(result.getMessage());
            } else {
                throw new BusinessException(Wrapper.ERROR_MESSAGE);
            }
        }
    }

    @Permission
    @Override
    public String placeTransOrder(AuthResDto authResDtoByToken, OfcOrderDTO ofcOrderDTOStr, String tag) {
        logger.info("客户工作台运输订单下单或编辑 ofcOrderDTOStr==>{}, tag==>{}", ofcOrderDTOStr, tag);
        if (ofcOrderDTOStr == null) {
            throw new BusinessException("订单中心下单dto不能为空！");
        }
        if(null == ofcOrderDTOStr.getOrderTime()){
            throw new BusinessException("请选择订单日期");
        }
        if(CollectionUtils.isEmpty(ofcOrderDTOStr.getGoodsList())){
            throw new BusinessException("请至少添加一条货品！");
        }
        if(CollectionUtils.isEmpty(ofcOrderDTOStr.getGoodsList())){
            throw new BusinessException("请至少添加一条货品！");
        }
        if(ofcOrderDTOStr.getConsignor() == null){
            throw new BusinessException("发货人信息不允许为空！");
        }
        if(ofcOrderDTOStr.getConsignee() == null){
            throw new BusinessException("发货人信息不允许为空！");
        }
        //校验业务类型，如果是卡班，必须要有运输单号
        if(StringUtils.equals(ofcOrderDTOStr.getBusinessType(), BusinessTypeEnum.CABANNES.getCode())){
            if(StringUtils.isBlank(ofcOrderDTOStr.getTransCode())){
                throw new BusinessException("业务类型是卡班，运输单号是必填项");
            }
        }
        if (null == ofcOrderDTOStr.getProvideTransport()){
            ofcOrderDTOStr.setProvideTransport(OrderConstConstant.WAREHOUSE_NO_TRANS);
        }
        if (null == ofcOrderDTOStr.getUrgent()){
            ofcOrderDTOStr.setUrgent(OrderConstConstant.DISTRIBUTION_ORDER_NOT_URGENT);
        }
        //客户工作台运输订单编辑
        if (StringUtils.equals(ORDER_TAG_CUST_TRANS_EDIT, tag)) {
            // 判断订单状态
            OfcCustOrderNewstatus newstatus = ofcCustOrderNewstatusService.queryByOrderCode(ofcOrderDTOStr.getOrderCode());
            String orderLatestStatus = newstatus.getOrderLatestStatus();
            // 未确认
            if (StringUtils.equals(orderLatestStatus, UNCONFIRMED.getCode())) {
                return ofcOrderPlaceService.placeOrder(ofcOrderDTOStr,ofcOrderDTOStr.getGoodsList(), tag, authResDtoByToken, ofcOrderDTOStr.getCustCode()
                        ,ofcOrderDTOStr.getConsignor(),ofcOrderDTOStr.getConsignee(),ofcOrderDTOStr.getSupplier());
                // 待审核
            } else if (StringUtils.equals(orderLatestStatus, PEND_AUDIT.getCode())) {
                return ofcOrderPlaceService.placeOrder(ofcOrderDTOStr,ofcOrderDTOStr.getGoodsList(), ORDER_TAG_OPER_TRANEDIT, authResDtoByToken, ofcOrderDTOStr.getCustCode()
                        ,ofcOrderDTOStr.getConsignor(),ofcOrderDTOStr.getConsignee(),ofcOrderDTOStr.getSupplier());
            } else {
                throw new BusinessException("订单状态有误");
            }
            //客户工作台运输订单下单
        } else if (StringUtils.equals(ORDER_TAG_NORMAL_PLACE, tag)) {
            return ofcOrderPlaceService.placeOrder(ofcOrderDTOStr,ofcOrderDTOStr.getGoodsList(), tag, authResDtoByToken, ofcOrderDTOStr.getCustCode()
                    ,ofcOrderDTOStr.getConsignor(),ofcOrderDTOStr.getConsignee(),ofcOrderDTOStr.getSupplier());
        } else {
            throw new BusinessException("标志位错误");
        }

    }
}
