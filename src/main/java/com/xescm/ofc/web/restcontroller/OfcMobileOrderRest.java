package com.xescm.ofc.web.restcontroller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.enums.BusinessTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.TREATED;
import static com.xescm.ofc.constant.OrderConstConstant.TREATING;
import static com.xescm.ofc.constant.OrderConstConstant.UN_TREATED;

/**
 * 手机订单
 * Created by hujintao on 2016/12/15.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcMobileOrderRest extends BaseController {

    @Resource
    private OfcMobileOrderService ofcMobileOrderService;

    @Resource
    private RestConfig restConfig;


    @RequestMapping(value = "/queryMobileOrderData", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> queryMobileOrderData(Page<MobileOrderOperForm> page, MobileOrderOperForm form) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OfcMobileOrder> dataList = ofcMobileOrderService.queryOrderList(form);
            PageInfo<OfcMobileOrder> pageInfo = new PageInfo<>(dataList);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException ex) {
            logger.error("运营平台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("运营平台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @RequestMapping(value = "/mobileOrderDetails/{orderCode}")
    public ModelAndView orderDetailByOrderCode(@PathVariable("orderCode") String code) {
        logger.info("==>手机订单详情code code={}", code);
        ModelAndView modelAndView = new ModelAndView("mobile_order_detail_opera");
        modelAndView.addObject(OrderConstConstant.OFC_WEB_URL, restConfig.getOfcWebUrl());
        OfcMobileOrder condition = new OfcMobileOrder();
        condition.setMobileOrderCode(code);
        OfcMobileOrderVo mobileOrder = null;
        try {
            mobileOrder = ofcMobileOrderService.selectOneOfcMobileOrder(condition);
        } catch (UnsupportedEncodingException ex) {
            logger.error("手机订单详情:{}", ex.getMessage(), ex);
        }
        modelAndView.addObject("mobileOrder", mobileOrder);
        return modelAndView;
    }

    @RequestMapping(value = "/acceptMobileOrder/{orderCode}")
    public ModelAndView acceptMobileOrder(@PathVariable("orderCode") String code) {
        logger.info("==>手机订单详情code code={}", code);
        ModelAndView modelAndView = new ModelAndView("mobile_order_accept_opera");
        modelAndView.addObject(OrderConstConstant.OFC_WEB_URL, restConfig.getOfcWebUrl());
        OfcMobileOrder condition = new OfcMobileOrder();
        condition.setMobileOrderCode(code);
        OfcMobileOrderVo mobileOrder = null;
        try {
            mobileOrder = ofcMobileOrderService.selectOneOfcMobileOrder(condition);
        } catch (UnsupportedEncodingException e) {
            logger.error("手机订单详情:{}", e.getMessage(), e);
        }
        modelAndView.addObject("mobileOrder", mobileOrder);
        return modelAndView;
    }

    /**
     * @param ofcOrderDTOStr                      订单基本信息、收发货方信息
     * @param orderGoodsListStr                   货品信息
     * @param cscContantAndCompanyDtoConsignorStr 发货人信息
     * @param cscContantAndCompanyDtoConsigneeStr 收货人信息
     * @param cscSupplierInfoDtoStr               供应商信息
     * @return
     */
    @RequestMapping("/mobileorderPlaceCon")
    @ResponseBody
    public Wrapper<?> mobileOrderPlace(String ofcOrderDTOStr, String orderGoodsListStr, String cscContantAndCompanyDtoConsignorStr
            , String cscContantAndCompanyDtoConsigneeStr, String cscSupplierInfoDtoStr, String mobileOrderCode) {
        logger.info("==>拍照下单订单中心下单 ofcOrderDTOStr={}", ofcOrderDTOStr);
        String orderCode;
        try {
            orderGoodsListStr = orderGoodsListStr.replace("~`", "");
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if (PubUtils.isSEmptyOrNull(ofcOrderDTOStr)) {
                ofcOrderDTOStr = JacksonUtil.toJsonWithFormat(new OfcOrderDTO());
            }
            if (PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr)) {
                cscContantAndCompanyDtoConsignorStr = JacksonUtil.toJsonWithFormat(new CscContantAndCompanyDto());
            }
            if (PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)) {
                cscContantAndCompanyDtoConsigneeStr = JacksonUtil.toJsonWithFormat(new CscContantAndCompanyDto());
            }
            if (PubUtils.isSEmptyOrNull(cscSupplierInfoDtoStr)) {
                cscSupplierInfoDtoStr = JacksonUtil.toJsonWithFormat(new CscSupplierInfoDto());
            }
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if (!PubUtils.isSEmptyOrNull(orderGoodsListStr)) { // 如果货品不空才去添加
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JacksonUtil.parseJsonWithFormat(ofcOrderDTOStr, OfcOrderDTO.class);
            logger.info(cscContantAndCompanyDtoConsignorStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            logger.info(cscContantAndCompanyDtoConsigneeStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JacksonUtil.parseJsonWithFormat(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            if (cscContantAndCompanyDtoConsignor == null) {
                throw new BusinessException("发货人信息不允许为空！");
            }
            if (cscContantAndCompanyDtoConsignee == null) {
                throw new BusinessException("收货人信息不允许为空！");
            }
            CscSupplierInfoDto cscSupplierInfoDto = JacksonUtil.parseJsonWithFormat(cscSupplierInfoDtoStr, CscSupplierInfoDto.class);
            //校验业务类型，如果是卡班，必须要有运输单号
            if (StringUtils.equals(ofcOrderDTO.getBusinessType(), BusinessTypeEnum.CABANNES.getCode())) {
                if (StringUtils.isBlank(ofcOrderDTO.getTransCode())) {
                    throw new Exception("业务类型是卡班，运输单号是必填项");
                }
            }
            if (null != ofcOrderDTO) {
                if (null == ofcOrderDTO.getProvideTransport()) {
                    ofcOrderDTO.setProvideTransport(OrderConstConstant.WAREHOUSE_NO_TRANS);
                }
                if (null == ofcOrderDTO.getUrgent()) {
                    ofcOrderDTO.setUrgent(OrderConstConstant.DISTRIBUTION_ORDER_NOT_URGENT);
                }
                if (null == ofcOrderDTO.getOrderTime()) {
                    throw new BusinessException("请选择订单日期！");
                }
            } else {
                throw new BusinessException("订单相关信息有误！");
            }
            //手机订单受理状态校验
            OfcMobileOrder mobileOrder = ofcMobileOrderService.selectByKey(mobileOrderCode);
            if(mobileOrder ==null){
                throw new BusinessException("手机订单号不存在！");
            }
            String accepter = mobileOrder.getAccepter();
            String MobileOrderStatus = mobileOrder.getMobileOrderStatus();
            if(TREATED.equals(MobileOrderStatus)){
                throw new BusinessException("手机订单已经受理！");
            }
            if(UN_TREATED.equals(MobileOrderStatus)|| TREATING.equals(MobileOrderStatus)){
                if(!PubUtils.isSEmptyOrNull(accepter)){
                    if(!accepter.equals(authResDtoByToken.getUserName())){
                        throw new BusinessException("手机订单有其它人在受理,请受理其它手机订单！");
                    }
                }
            }

            orderCode = ofcMobileOrderService.placeOrder(ofcOrderDTO, ofcGoodsDetailsInfos, authResDtoByToken, authResDtoByToken.getGroupRefCode()
                    , cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee, cscSupplierInfoDto);

            //更新拍照订单的状态，订单号
            if (!PubUtils.isSEmptyOrNull(orderCode)) {
                OfcMobileOrder order = new OfcMobileOrder();
                order.setMobileOrderCode(mobileOrderCode);
                order.setAccepter(authResDtoByToken.getUserName());
                order.setAppcetDate(new Date());
                order.setMobileOrderStatus(TREATED);//已处理
                order.setOrderCode(orderCode);
                ofcMobileOrderService.updateByMobileCode(order);
            }
        } catch (BusinessException ex) {
            logger.error("订单中心下单或编辑出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                logger.error("订单中心下单或编辑出现异常:{}", "获取订单号发生重复!", ex);
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "获取订单号发生重复!");
            } else {
                logger.error("订单中心下单或编辑出现未知异常:{}", ex.getMessage(), ex);
                return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
            }
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }

    /**
     * 拍照开单-自动受理订单
     *
     * @return
     */
    @RequestMapping(value = "/autoAcceptMobileOrderDetail")
    @ResponseBody
    public Wrapper<OfcMobileOrderVo> autoAcceptMobileOrderDetail(String orderCode) {
        logger.info("==>拍照开单-自动受理订单 orderCode={}", orderCode);
        Wrapper<OfcMobileOrderVo> result;
        try {
            OfcMobileOrder params = new OfcMobileOrder();
            params.setMobileOrderCode(orderCode);
            OfcMobileOrderVo mobileOrderVo = ofcMobileOrderService.selectOneOfcMobileOrder(params);
            if (PubUtils.isNull(mobileOrderVo)) {
                result = WrapMapper.wrap(Wrapper.ERROR_CODE, "暂无待受理订单！");
            } else {
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, mobileOrderVo);
            }
        } catch (BusinessException e) {
            logger.error("拍照开单-自动受理订单发生错误: {}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("拍照开单-自动受理订单发生错误: {}", e);
            result = WrapMapper.error();
        }
        return result;
    }

    /**
     *
     * @param mobileOrderCode 手机订单号
     * @return
     */
    @RequestMapping(value = "/deleteMobileOrder")
    @ResponseBody
    public Wrapper<?> deleteMobileOrder(String mobileOrderCode) {
        logger.info("==>拍照开单-删除的手机订单号为: mobileOrderCode={}", mobileOrderCode);
        try {
            if(PubUtils.isSEmptyOrNull(mobileOrderCode)){
                throw new BusinessException("删除手机订单时订单号不能为空！");
            }
            ofcMobileOrderService.deleteMobileOrder(mobileOrderCode);
        } catch (Exception e) {
            logger.error("拍照开单-删除手机订单号发生错误: {}", e);
            return  WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE,e.getMessage());

        }
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }
}