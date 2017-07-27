package com.xescm.ofc.web.restcontroller.operationWorkbench.mobileOrder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.enums.BusinessTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.dto.ofc.CheckTransCodeDTO;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
import com.xescm.ofc.service.OfcDistributionBasicInfoService;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.*;

/**
 * @description: 拍照开单查询
 * @author: dragon-yuan
 * @date: 2017/7/13 11:29
 */

@Controller
@RequestMapping(value = "/page/ofc/mobile/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcMobileOrderController", tags = "OfcMobileOrderController", description = "拍照开单查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcMobileOrderController extends BaseController {

    @Resource
    private OfcMobileOrderService ofcMobileOrderService;

    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;

    /**
     * 前后端分离，分页，拍照开单查询接口
     */
    @ResponseBody
    @RequestMapping(value = "/queryMobileOrderData", method = {RequestMethod.POST})
    @ApiOperation(value = "分页查询拍照开单", httpMethod = "POST", notes = "返回钉钉拍照开单分页列表")
    public Wrapper<?> queryMobileOrderData(@ApiParam(name = "page", value = "任务日志分页") @RequestBody Page<MobileOrderOperForm> page) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OfcMobileOrder> dataList = ofcMobileOrderService.queryOrderList(page.getParam());
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

    /**
     * 前后端分离，查询拍照开单详情信息
     */
    @ResponseBody
    @RequestMapping(value = "/mobileOrderDetails/{orderCode}")
    @ApiOperation(value = "查询拍照开单详情", httpMethod = "POST", notes = "返回拍照开单详情")
    public Wrapper<?> orderDetailByOrderCode(@PathVariable("orderCode") String code) {
        logger.info("==>手机订单详情code code={}", code);
        OfcMobileOrder condition = new OfcMobileOrder();
        condition.setMobileOrderCode(code);
        OfcMobileOrderVo mobileOrder;
        try {
            mobileOrder = ofcMobileOrderService.selectOneOfcMobileOrder(condition);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, mobileOrder);
        } catch (BusinessException ex) {
            logger.error("查询手机订单详情出错:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("查询手机订单详情出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     * 拍照开单-自动受理订单
     *
     * @return
     */
    @RequestMapping(value = "/autoAcceptMobileOrderDetail")
    @ResponseBody
    public Wrapper<OfcMobileOrderVo> autoAcceptMobileOrderDetail(@PathVariable("orderCode") String orderCode) {
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
     * 校验运输单号
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/checkTransCode", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> checkTransCode(@RequestBody CheckTransCodeDTO checkTransCodeDTO) {
        logger.info("校验运输单号==> transCode={}", checkTransCodeDTO.getTransCode());
        logger.info("校验运输单号==> selfTransCode={}", checkTransCodeDTO.getSelfTransCode());
        OfcDistributionBasicInfo ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
        ofcDistributionBasicInfo.setTransCode(checkTransCodeDTO.getTransCode());
        if (PubUtils.isSEmptyOrNull(checkTransCodeDTO.getSelfTransCode())) {
            ofcDistributionBasicInfo.setSelfTransCode(checkTransCodeDTO.getSelfTransCode());
        }
        boolean flag = false;
        try {
            int count = ofcDistributionBasicInfoService.checkTransCode(ofcDistributionBasicInfo);
            if (count < 1) {
                flag = true;
            }
        } catch (Exception e) {
            logger.error("校验运输单号出错:{}　", e.getMessage(), e);
        }
        if (flag) {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
        } else {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "运输单号重复");
        }
    }

    /**
     * @param mobileOrderCode 手机订单号
     * @return
     */
    @RequestMapping(value = "/deleteMobileOrder/{mobileOrderCode}")
    @ResponseBody
    public Wrapper<?> deleteMobileOrder(@PathVariable String mobileOrderCode) {
        logger.info("==>拍照开单-删除的手机订单号为: mobileOrderCode={}", mobileOrderCode);
        try {
            if (PubUtils.isSEmptyOrNull(mobileOrderCode)) {
                throw new BusinessException("删除手机订单时订单号不能为空！");
            }
            ofcMobileOrderService.deleteMobileOrder(mobileOrderCode);
        } catch (Exception e) {
            logger.error("拍照开单-删除手机订单号发生错误: {}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());

        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }

    /**
     * 拍照开单-自动受理
     */
    @RequestMapping(value = "/autoAcceptMobileOrder", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> autoAcceptMobileOrder() {
        try {
            AuthResDto userInfo = getAuthResDtoByToken();
            String curUser = userInfo.getUserName();
            OfcMobileOrderVo mobileOrderVo = ofcMobileOrderService.autoAcceptPendingOrder(curUser);
            if (mobileOrderVo != null && !CollectionUtils.isEmpty(mobileOrderVo.getUrls())) {
                logger.info("==>放入页面自动获取待受理订单号{}", mobileOrderVo.getOrderCode());
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, mobileOrderVo);
            } else {
                return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("拍照开单自动受理订单发生错误！", e);
        }
        return null;
    }

    /**
     * 订单中心下单
     *
     * @param ofcOrderDTOStr  订单基本信息、收发货方信息
     * @param mobileOrderCode 订单号
     * @return Wrapper
     */
    @RequestMapping("/mobileOrderPlaceCon/{mobileOrderCode}")
    @ResponseBody
    public Wrapper<?> orderPlace(@RequestBody OfcOrderDTO ofcOrderDTOStr, @PathVariable String mobileOrderCode) {
        logger.info("==>订单中心下单或编辑实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        logger.info("==>订单中心拍照下单手机订单号为 mobileOrderCode={}", mobileOrderCode);
        String orderCode;
        try {
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if (ofcOrderDTOStr == null) {
                throw new BusinessException("订单中心下单dto不能为空！");
            }
            if (null == ofcOrderDTOStr.getOrderTime()) {
                throw new BusinessException("请选择订单日期");
            }
            if (CollectionUtils.isEmpty(ofcOrderDTOStr.getGoodsList())) {
                throw new BusinessException("请至少添加一条货品！");
            }
            if (CollectionUtils.isEmpty(ofcOrderDTOStr.getGoodsList())) {
                throw new BusinessException("请至少添加一条货品！");
            }
            if (ofcOrderDTOStr.getConsignor() == null) {
                throw new BusinessException("发货人信息不允许为空！");
            }
            if (ofcOrderDTOStr.getConsignee() == null) {
                throw new BusinessException("发货人信息不允许为空！");
            }
            //校验业务类型，如果是卡班，必须要有运输单号
            if (StringUtils.equals(ofcOrderDTOStr.getBusinessType(), BusinessTypeEnum.CABANNES.getCode())) {
                if (StringUtils.isBlank(ofcOrderDTOStr.getTransCode())) {
                    throw new BusinessException("业务类型是卡班，运输单号是必填项");
                }
            }
            if (null == ofcOrderDTOStr.getProvideTransport()) {
                ofcOrderDTOStr.setProvideTransport(OrderConstConstant.WAREHOUSE_NO_TRANS);
            }
            if (null == ofcOrderDTOStr.getUrgent()) {
                ofcOrderDTOStr.setUrgent(OrderConstConstant.DISTRIBUTION_ORDER_NOT_URGENT);
            }

            //手机订单受理状态校验
            OfcMobileOrder mobileOrder = ofcMobileOrderService.selectByKey(mobileOrderCode);
            if (mobileOrder == null) {
                throw new BusinessException("手机订单号不存在！");
            }
            String accepter = mobileOrder.getAccepter();
            String MobileOrderStatus = mobileOrder.getMobileOrderStatus();
            if (TREATED.equals(MobileOrderStatus)) {
                throw new BusinessException("手机订单已经受理！");
            }

            if (UN_TREATED.equals(MobileOrderStatus)) {
                throw new BusinessException("手机订单已经超过5分钟未受理，请刷新页面重新加载新的订单！");
            }

            if (TREATING.equals(MobileOrderStatus)) {
                if (!PubUtils.isSEmptyOrNull(accepter)) {
                    if (!accepter.equals(authResDtoByToken.getUserName())) {
                        throw new BusinessException("手机订单有其它人在受理,请受理其它手机订单！");
                    }
                }
            }
            orderCode = ofcMobileOrderService.placeOrder(ofcOrderDTOStr, ofcOrderDTOStr.getGoodsList(), authResDtoByToken, authResDtoByToken.getGroupRefCode()
                    , ofcOrderDTOStr.getConsignor(), ofcOrderDTOStr.getConsignee(), ofcOrderDTOStr.getSupplier());
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
}
