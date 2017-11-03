package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.ofc.annotation.Permission;
import com.xescm.ofc.annotation.ValidParam;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcOrderScreenMapper;
import com.xescm.ofc.model.dto.form.OfcManagementForm;
import com.xescm.ofc.model.dto.ofc.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.BeanConvertor;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.tfc.edas.model.domain.epc.req.FollowInfoReqDto;
import com.xescm.tfc.edas.model.domain.epc.resp.TransportRespDto;
import com.xescm.tfc.edas.service.TfcTransportEpcEdasService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.HASBEEN_CANCELED;
import static com.xescm.ofc.constant.OrderConstant.TRANSPORT_ORDER;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.ORDER_TAG_CUST_STOCK;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.ORDER_TAG_OPER_TRANS;
import static com.xescm.ofc.enums.OrderStatusOfCustEnum.CONFIRMED;
import static com.xescm.ofc.enums.OrderStatusOfCustEnum.UNCONFIRMED;

/**
 * 客户工作台订单管理
 * Created by lyh on 2017/9/8.
 */
@Service
public class OfcCustOrderManageServiceImpl implements OfcCustOrderManageService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcCustOrderNewstatusService ofcCustOrderNewstatusService;
    @Resource
    private OfcCustFundamentalInformationService ofcCustFundamentalInformationService;
    @Resource
    private OfcCustDistributionBasicInfoService ofcCustDistributionBasicInfoService;
    @Resource
    private OfcCustWarehouseInformationService ofcCustWarehouseInformationService;
    @Resource
    private OfcCustFinanceInformationService ofcCustFinanceInformationService;
    @Resource
    private OfcCustGoodsDetailsInfoService ofcCustGoodsDetailsInfoService;
    @Resource
    private OfcCustOrderStatusService ofcCustOrderStatusService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private OfcOrderNewstatusService ofcOrderNewstatusService;
    @Resource
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Resource
    private TfcTransportEpcEdasService tfcTransportEpcEdasService;
    @Resource
    private OfcOrderScreenMapper ofcOrderScreenMapper;

    /**
     * 订单筛选
     */
    @Permission
    @Override
    public List<OrderSearchOperResult> queryOrderListByCondition(@ValidParam(validType = ValidParam.ValidType.ADD_CUSTOMER_MSG) OfcUserMsgDTO userMsgDTO
            , OfcManagementForm form) {
        logger.info("订单管理筛选后端权限校验入参: userMsgDTO==>{},form==>{}", userMsgDTO, form);
        if (null == form) {
            throw new BusinessException("订单管理筛选后端权限校验入参有误");
        }
        List<OrderSearchOperResult> orderSearchOperResults;
        String areaSerialNo = form.getAreaSerialNo();
        String baseSerialNo = form.getBaseSerialNo();
        if (PubUtils.isSEmptyOrNull(areaSerialNo) && !PubUtils.isSEmptyOrNull(baseSerialNo)) {
            throw new BusinessException("基地所属大区未选择!");
        }
        // 2017.8.17 订单状态支持多选
        String orderStateStr = form.getOrderState();
        if (StringUtils.isNotEmpty(orderStateStr)) {
            List<String> strList = Arrays.asList(orderStateStr.split(","));
            form.setOrderStateList(strList);
        }
        String userId = userMsgDTO.getUserId();
        form.setCustName(userMsgDTO.getCustName());
        form.setCustCode(userMsgDTO.getCustCode());
        orderSearchOperResults = ofcOrderScreenMapper.queryOrderListForCust(form, userId, false);
        return orderSearchOperResults;
    }




    /**
     * 订单取消
     */
    @Permission
    @Override
    public Wrapper orderCancel(String orderCode, AuthResDto authResDtoByToken) {
        logger.info("客户工作台订单取消===> userMsgDTO==>{}, managementForm==>{}", orderCode);
        if (StringUtils.isEmpty(orderCode)) {
            logger.error("客户工作台订单取消失败, 入参有误!");
            throw new BusinessException("取消失败！");
        }
        OfcCustOrderNewstatus custOrderNewstatus = ofcCustOrderNewstatusService.selectByKey(orderCode);
        if (null == custOrderNewstatus) {
            OfcOrderNewstatus ofcOrderNewstatus = ofcOrderNewstatusService.selectByKey(orderCode);
            if (null == ofcOrderNewstatus) {
                logger.error("该订单号再无最新状态");
                throw new BusinessException("该订单号再无最新状态");
            }
            custOrderNewstatus = new OfcCustOrderNewstatus();
            BeanUtils.copyProperties(ofcOrderNewstatus, custOrderNewstatus);
        }
        String orderLatestStatus = custOrderNewstatus.getOrderLatestStatus();
        // 未确认订单(即滞留客户工作台的订单)直接取消掉
        if (StringUtils.equals(orderLatestStatus, UNCONFIRMED.getCode())) {
            this.modifyRelativeWhenCancel(orderCode, authResDtoByToken);
            // 已确认订单(即已推送运营工作台订单)调用原取消方法
        } else {
            ofcOrderManageService.orderCancel(orderCode, authResDtoByToken);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }

    private void modifyRelativeWhenCancel(String orderCode, AuthResDto authResDtoByToken) {
        StringBuilder notes = new StringBuilder();
        OfcCustOrderStatus ofcOrderStatus = new OfcCustOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(HASBEEN_CANCELED);
        ofcOrderStatus.setStatusDesc("已取消");
        notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
        notes.append(" 订单已取消");
        notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
        notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
        ofcOrderStatus.setNotes(notes.toString());
        ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
        ofcOrderStatus.setLastedOperTime(new Date());
        this.checkCRUD(ofcCustOrderStatusService.saveOrderStatus(ofcOrderStatus));
        OfcCustFundamentalInformation ofcFundamentalInformation = ofcCustFundamentalInformationService.selectByKey(orderCode);
        ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOperTime(new Date());
        ofcFundamentalInformation.setAbolisher(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setAbolisherName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setAbolishMark(1);//表明已作废
        ofcFundamentalInformation.setAbolishTime(ofcFundamentalInformation.getOperTime());
        this.checkCRUD(ofcCustFundamentalInformationService.update(ofcFundamentalInformation));
    }


    /**
     * 通过订单号查询订单详情
     */
    @Permission
    @Override
    public OfcCustOrderInfoDTO queryOrderDetailByOrderCode(String orderCode, @ValidParam OfcUserMsgDTO userMsgDTO) {
        if (StringUtils.isEmpty(orderCode)) {
            throw new BusinessException("查询订单详情异常");
        }
        OfcCustOrderInfoDTO ofcCustOrderInfoDTO = new OfcCustOrderInfoDTO();
        OfcCustFundamentalInformation custFundamentalInformation = ofcCustFundamentalInformationService.queryByOrderCode(orderCode);
        if (null == custFundamentalInformation) {
            logger.error("查无该订单");
            throw new BusinessException("无法查到该订单");
        }
        ofcCustOrderInfoDTO.setOfcFundamentalInformation(custFundamentalInformation);
        String orderType = custFundamentalInformation.getOrderType();
        if (StringUtils.equals(orderType, TRANSPORT_ORDER)) {
            OfcCustDistributionBasicInfo custDistributionBasicInfo = ofcCustDistributionBasicInfoService.queryByOrderCode(orderCode);
            if (null == custDistributionBasicInfo) {
                logger.error("该订单无运输信息");
                throw new BusinessException("该订单无运输信息");
            }
            ofcCustOrderInfoDTO.setOfcDistributionBasicInfo(custDistributionBasicInfo);
        } else if (StringUtils.equals(orderType, WAREHOUSE_DIST_ORDER)) {
            OfcCustWarehouseInformation custWarehouseInformation = ofcCustWarehouseInformationService.queryByOrderCode(orderCode);
            if (null == custWarehouseInformation) {
                logger.error("该订单无仓储信息");
                throw new BusinessException("该订单无仓储信息");
            }
            ofcCustOrderInfoDTO.setOfcWarehouseInformation(custWarehouseInformation);
            if (custWarehouseInformation.getProvideTransport().compareTo(1) == 0) {
                OfcCustDistributionBasicInfo custDistributionBasicInfo = ofcCustDistributionBasicInfoService.queryByOrderCode(orderCode);
                if (null == custDistributionBasicInfo) {
                    logger.error("该订单无运输信息");
                    throw new BusinessException("该订单无运输信息");
                }
                ofcCustOrderInfoDTO.setOfcDistributionBasicInfo(custDistributionBasicInfo);
            }
        }
        OfcCustFinanceInformation ofcCustFinanceInformation = ofcCustFinanceInformationService.queryByOrderCode(orderCode);
        ofcCustOrderInfoDTO.setOfcFinanceInformation(ofcCustFinanceInformation);
        List<OfcCustGoodsDetailsInfo> ofcCustGoodsDetailsInfos = ofcCustGoodsDetailsInfoService.queryByOrderCode(orderCode);
        ofcCustOrderInfoDTO.setGoodsDetailsInfoList(ofcCustGoodsDetailsInfos);
        List<OfcCustOrderStatus> ofcCustOrderStatus = ofcCustOrderStatusService.queryByOrderCode(orderCode);
        ofcCustOrderInfoDTO.setOrderStatusList(ofcCustOrderStatus);
        FollowInfoReqDto followInfoReqDto = new FollowInfoReqDto();
        followInfoReqDto.setCustomerOrderCode(custFundamentalInformation.getCustOrderCode());

        Wrapper<List<TransportRespDto>> transportState = tfcTransportEpcEdasService.findTransportState(followInfoReqDto);
        if (transportState.getCode() == Wrapper.ERROR_CODE) {
            logger.error("调用TFC接口查询运单跟踪信息失败");
            throw new BusinessException("查询运单跟踪信息失败");
        }
        ofcCustOrderInfoDTO.setTransportRespDtoList(transportState.getResult());
        return ofcCustOrderInfoDTO;
    }

    /**
     * 客户工作台确认订单
     */
    @Permission
    @Override
    public void confirmOrder(String orderCode, AuthResDto authResDtoByToken) {
        if (StringUtils.isEmpty(orderCode)) {
            logger.error("客户工作台确认订单DTO不能为空");
            throw new BusinessException("客户工作台确认订单DTO不能为空！");
        }
        if (StringUtils.isBlank(orderCode)) {
            throw new BusinessException("订单编号不能为空！");
        }
        // 将订单状态从待确认置为已确认
        StringBuilder notes = new StringBuilder();
        OfcCustOrderStatus ofcOrderStatus = new OfcCustOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(CONFIRMED.getCode());
        ofcOrderStatus.setStatusDesc(CONFIRMED.getDesc());
        notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
        notes.append(" 订单已确认");
        notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
        notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
        ofcOrderStatus.setNotes(notes.toString());
        ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
        ofcOrderStatus.setLastedOperTime(new Date());
        this.checkCRUD(ofcCustOrderStatusService.saveOrderStatus(ofcOrderStatus));
        // 确认订单, 即将订单推送运营工作台
        OfcCustFundamentalInformation custFundamentalInformation = ofcCustFundamentalInformationService.queryByOrderCode(orderCode);
        String orderType = custFundamentalInformation.getOrderType();
        if (StringUtils.equals(orderType, TRANSPORT_ORDER)) {// 运输订单确认下单
            OfcOrderDTO ofcOrderDTO = this.getOrderDetailForConfirmTransOrder(orderCode);
            ofcOrderPlaceService.placeOrder(ofcOrderDTO, ofcOrderDTO.getGoodsList(), ORDER_TAG_OPER_TRANS, authResDtoByToken, ofcOrderDTO.getCustCode()
                    , ofcOrderDTO.getConsignor(), ofcOrderDTO.getConsignee(), ofcOrderDTO.getSupplier());
        } else if (StringUtils.equals(orderType, WAREHOUSE_DIST_ORDER)) {// 仓储订单确认下单
            this.confirmStorageOrder(orderCode, authResDtoByToken);
        }
    }

    /**
     * 客户工作台仓储订单订单确认
     **/
    @SuppressWarnings("unchecked")
    private void confirmStorageOrder(String orderCode, AuthResDto authResDtoByToken) {
        OfcCustOrderInfoDTO orderDetailUnion = this.getOrderDetailUnion(orderCode);
        OfcSaveStorageDTO ofcSaveStorageDTO = new OfcSaveStorageDTO();
        OfcFundamentalInformationDTO fundamentalInformationDTO = new OfcFundamentalInformationDTO();
        BeanUtils.copyProperties(orderDetailUnion.getOfcFundamentalInformation(), fundamentalInformationDTO);
        ofcSaveStorageDTO.setFundamentalInformation(fundamentalInformationDTO);
        OfcWarehouseInformationDTO ofcWarehouseInformation = new OfcWarehouseInformationDTO();
        BeanUtils.copyProperties(orderDetailUnion.getOfcWarehouseInformation(), ofcWarehouseInformation);
        ofcSaveStorageDTO.setWarehouseInformation(ofcWarehouseInformation);
        if ((ofcWarehouseInformation.getProvideTransport().compareTo(1) == 0)) {
            OfcDistributionBasicInfoDTO ofcDistributionBasicInfoDTO = new OfcDistributionBasicInfoDTO();
            if (null == orderDetailUnion.getOfcDistributionBasicInfo()) {
                logger.error("该仓储订单提供运输但无运输信息");
                throw new BusinessException("该仓储订单提供运输但无运输信息");
            }
            BeanUtils.copyProperties(orderDetailUnion.getOfcDistributionBasicInfo(), ofcDistributionBasicInfoDTO);
            ofcSaveStorageDTO.setDistributionBasicInfo(ofcDistributionBasicInfoDTO);
        }
        List<OfcCustGoodsDetailsInfo> goodsDetailsInfoList = orderDetailUnion.getGoodsDetailsInfoList();
        List<OfcGoodsDetailsInfoDTO> ofcGoodsDetailsInfos = new ArrayList<>();
        ofcGoodsDetailsInfos = BeanConvertor.listConvertor(goodsDetailsInfoList, ofcGoodsDetailsInfos, OfcGoodsDetailsInfoDTO.class);
        ofcOrderManageService.saveStorageOrder(ofcSaveStorageDTO, ofcGoodsDetailsInfos, ORDER_TAG_CUST_STOCK
                , null, null, new CscSupplierInfoDto(), authResDtoByToken);
    }

    private OfcCustOrderInfoDTO getOrderDetailUnion(String orderCode) {
        OfcCustOrderInfoDTO ofcOrderInfoDTO = new OfcCustOrderInfoDTO();
        ofcOrderInfoDTO.setOfcFundamentalInformation(ofcCustFundamentalInformationService.queryByOrderCode(orderCode));
        ofcOrderInfoDTO.setOfcWarehouseInformation(ofcCustWarehouseInformationService.queryByOrderCode(orderCode));
        ofcOrderInfoDTO.setOfcDistributionBasicInfo(ofcCustDistributionBasicInfoService.queryByOrderCode(orderCode));
        ofcOrderInfoDTO.setOfcFinanceInformation(ofcCustFinanceInformationService.queryByOrderCode(orderCode));
        ofcOrderInfoDTO.setGoodsDetailsInfoList(ofcCustGoodsDetailsInfoService.queryByOrderCode(orderCode));
        return ofcOrderInfoDTO;
    }

    @SuppressWarnings("unchecked")
    private OfcOrderDTO getOrderDetailForConfirmTransOrder(String orderCode) {
        if (StringUtils.isEmpty(orderCode)) {
            logger.error("getOrderDetailForConfirm 入参为空");
            throw new BusinessException("操作失败");
        }
        OfcCustOrderInfoDTO orderDetailUnion = this.getOrderDetailUnion(orderCode);
        OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
        OfcCustFundamentalInformation custFundamentalInformation = orderDetailUnion.getOfcFundamentalInformation();
        if (null == custFundamentalInformation) {
            throw new BusinessException("查无此单");
        }
        OfcCustDistributionBasicInfo custDistributionBasicInfo = orderDetailUnion.getOfcDistributionBasicInfo();
        OfcCustFinanceInformation ofcCustFinanceInformation = orderDetailUnion.getOfcFinanceInformation();
        OfcCustWarehouseInformation ofcWarehouseInformation = orderDetailUnion.getOfcWarehouseInformation();
        BeanUtils.copyProperties(custFundamentalInformation, ofcOrderDTO);
        if (null != custDistributionBasicInfo) BeanUtils.copyProperties(custDistributionBasicInfo, ofcOrderDTO);
        if (null != ofcCustFinanceInformation) BeanUtils.copyProperties(ofcCustFinanceInformation, ofcOrderDTO);
        if (null != ofcWarehouseInformation) BeanUtils.copyProperties(ofcWarehouseInformation, ofcOrderDTO);
        List<OfcCustGoodsDetailsInfo> ofcCustGoodsDetailsInfos = orderDetailUnion.getGoodsDetailsInfoList();
        List<OfcGoodsDetailsInfo> goodsList = new ArrayList<>();
        goodsList = BeanConvertor.listConvertor(ofcCustGoodsDetailsInfos, goodsList, OfcGoodsDetailsInfo.class);
        ofcOrderDTO.setGoodsList(goodsList);
        return ofcOrderDTO;
    }

    private void checkCRUD(Integer num) {
        if (num.compareTo(0) == 0) {
            logger.error("CRUD error");
            throw new BusinessException("操作失败");
        }
    }


}
