package com.xescm.ofc.web.restcontroller.operationWorkbench.mobileOrder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
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

}
