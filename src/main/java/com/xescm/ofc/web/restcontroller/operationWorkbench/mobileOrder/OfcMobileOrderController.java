package com.xescm.ofc.web.restcontroller.operationWorkbench.mobileOrder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.dto.ofc.CheckTransCodeDTO;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
import com.xescm.ofc.service.OfcDistributionBasicInfoService;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    private RestConfig restConfig;

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
        OfcMobileOrderVo mobileOrder = null;
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
     * @param
     * @return
     */
    @RequestMapping(value = "/checkTransCode",method = RequestMethod.POST)
    @ResponseBody
    public boolean checkTransCode(@RequestBody CheckTransCodeDTO checkTransCodeDTO){
        logger.info("校验运输单号==> transCode={}",checkTransCodeDTO.getTransCode() );
        logger.info("校验运输单号==> selfTransCode={}",checkTransCodeDTO.getSelfTransCode());
        OfcDistributionBasicInfo ofcDistributionBasicInfo=new OfcDistributionBasicInfo();
       ofcDistributionBasicInfo.setTransCode(checkTransCodeDTO.getTransCode());
       if(PubUtils.isSEmptyOrNull(checkTransCodeDTO.getSelfTransCode())){
           ofcDistributionBasicInfo.setSelfTransCode(checkTransCodeDTO.getSelfTransCode());
       }
        boolean flag = false;
        try {
            int count = ofcDistributionBasicInfoService.checkTransCode(ofcDistributionBasicInfo);
            if (count < 1){
                flag = true;
            }

        } catch (Exception e) {
            logger.error("校验运输单号出错:{}　", e.getMessage(),e);
        }
        return flag;
    }

    /**
     *
     * @param mobileOrderCode 手机订单号
     * @return
     */
    @RequestMapping(value = "/deleteMobileOrder/{mobileOrderCode}")
    @ResponseBody
    public Wrapper<?> deleteMobileOrder(@PathVariable  String mobileOrderCode) {
        logger.info("==>拍照开单-删除的手机订单号为: mobileOrderCode={}", mobileOrderCode);
        try {
            if(PubUtils.isSEmptyOrNull(mobileOrderCode)){
                throw new BusinessException("删除手机订单时订单号不能为空！");
            }
            ofcMobileOrderService.deleteMobileOrder(mobileOrderCode);
        } catch (Exception e) {
            logger.error("拍照开单-删除手机订单号发生错误: {}", e);
            return  WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());

        }
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }





}
