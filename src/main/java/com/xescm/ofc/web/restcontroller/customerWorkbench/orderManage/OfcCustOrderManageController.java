package com.xescm.ofc.web.restcontroller.customerWorkbench.orderManage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.OfcManagementForm;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.model.dto.ofc.AuditOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcOrderInfoDTO;
import com.xescm.ofc.model.dto.ofc.OfcUserMsgDTO;
import com.xescm.ofc.service.OfcCustOrderManageService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by lyh on 2017/9/8.
 */
@Controller
@RequestMapping(value = "/page/ofc/cust/manage/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcCustOrderManageController", tags = "OfcCustOrderManageController", description = "客户工作台订单管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcCustOrderManageController extends BaseController{

    @Resource
    private OfcCustOrderManageService custOrderManageService;


    @ResponseBody
    @RequestMapping(value = "/queryOrdersPage", method = {RequestMethod.POST})
    @ApiOperation(value = "分页查询订单列表", httpMethod = "POST", notes = "返回订单列表")
//    public Wrapper<PageInfo<OrderSearchOperResult>> queryOrdersPage(@ApiParam(name = "page", value = "订单列表分页") @RequestBody Page<OfcManagementForm> page) {
    public Wrapper<PageInfo<OrderSearchOperResult>> queryOrdersPage() {
//        logger.info("==>queryOrdersPage   page:{}", page);
        try {
            PageHelper.startPage(1, 1 );
            List<OrderSearchOperResult> dataList = custOrderManageService.queryOrderListByCondition(new OfcUserMsgDTO(), new OfcManagementForm());
            PageInfo<OrderSearchOperResult> pageInfo = new PageInfo<>(dataList);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException ex) {
            logger.error("客户工作台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("客户工作台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     * 订单取消
     * @param managementForm     订单编号
     * @return  Wrapper
     */
    @ApiOperation( notes = "取消订单", httpMethod = "POST", value = "取消订单")
    @RequestMapping(value = "/cancelOrder/{orderCode}", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderCancelOper(@ApiParam(name = "orderCode",value = "订单号" ) @PathVariable OfcManagementForm managementForm) {
        try {
            return custOrderManageService.orderCancel(new OfcUserMsgDTO(), managementForm, getAuthResDtoByToken());
        } catch (BusinessException ex) {
            logger.error("订单中心订单管理订单取消出现异常,{}", "", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "订单取消出现异常");
        }
    }

    /**
     * 客户工作台确认订单
     */
    @RequestMapping(value = "/auditOrderOrNotAudit/{orderCode}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(notes = "客户工作台确认订单",  httpMethod = "POST", value = "客户工作台确认订单" )
    public Wrapper<?> auditOrderOrNotAuditOper(@ApiParam(name = "AuditOrderDTO",value = "确认订单的DTO") @PathVariable String orderCode) {
        logger.info("客户工作台确认订单orderCode==>{}", orderCode);
        try {
            if (StringUtils.isEmpty(orderCode)) {
                logger.error("客户工作台确认订单DTO不能为空");
                throw new BusinessException("客户工作台确认订单DTO不能为空！");
            }
            custOrderManageService.confirmOrder(orderCode, new OfcUserMsgDTO(), getAuthResDtoByToken());
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
        } catch (BusinessException ex) {
            logger.error("客户工作台确认订单出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("客户工作台确认订单出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }


    /**
     * 客户工作台订单详情
     */
    @ResponseBody
    @RequestMapping(value = "/queryOrderDetailByOrderCode/{orderCode}", method = {RequestMethod.POST})
    @ApiOperation(value = "客户工作台订单详情", httpMethod = "POST", notes = "")
    public Wrapper<OfcOrderInfoDTO> queryOrderDetailByOrderCode(@ApiParam(name = "orderCode", value = "订单号") @PathVariable String orderCode) {
        logger.info("==>queryOrderDetailByOrderCode   orderCode:{}", orderCode);
        Wrapper<OfcOrderInfoDTO> result;
        try {
            if (StringUtils.isEmpty(orderCode)) {
                return WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, Wrapper.ILLEGAL_ARGUMENT_MESSAGE);
            }
            OfcOrderInfoDTO orderInfoDTO = custOrderManageService.queryOrderDetailByOrderCode(orderCode, new OfcUserMsgDTO());
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, orderInfoDTO);
        } catch (BusinessException ex) {
            logger.error("查询订单明细信息发生异常：异常详情{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        } catch (Exception ex) {
            logger.error("查询订单明细信息发生未知异常：异常详情{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return result;
    }


}
