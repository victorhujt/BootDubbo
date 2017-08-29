package com.xescm.ofc.web.restcontroller.operationWorkbench.orderManage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.model.dto.ofc.AuditOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcOrderInfoDTO;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.model.dto.group.UamGroupDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 订单管理
 * @author: nothing
 * @date: 2017/7/12 16:19
 */
@Controller
@RequestMapping(value = "/page/ofc/ordermanage/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcOrderManageController", tags = "OfcOrderManageController", description = "订单管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcOrderManageController extends BaseController {

    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;

    @ResponseBody
    @RequestMapping(value = "/queryOrdersPage", method = {RequestMethod.POST})
    @ApiOperation(value = "分页查询订单列表", httpMethod = "POST", notes = "返回订单列表")
    public Wrapper<PageInfo<OrderSearchOperResult>> queryOrdersPage(@ApiParam(name = "page", value = "订单列表分页") @RequestBody Page<OrderOperForm> page) {
        logger.info("==>queryOrdersPage   page:{}", page);
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            AuthResDto authResDto = getAuthResDtoByToken();
            List<OrderSearchOperResult> dataList = ofcOrderManageOperService.queryOrderList(authResDto, page.getParam());
            PageInfo<OrderSearchOperResult> pageInfo = new PageInfo<>(dataList);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException ex) {
            logger.error("运营平台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("运营平台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryUamGroupByType/{type}", method = {RequestMethod.POST})
    @ApiOperation(value = "根据类型查询组织信息", httpMethod = "POST", notes = "返回组织信息")
    public Wrapper<List<OfcGroupVo>> queryUamGroupByType(@ApiParam(name = "type", value = "类型") @PathVariable String type) {
        logger.info("==>queryUamGroup   type:{}", type);
        Wrapper<List<OfcGroupVo>> result;
        try {
            Map<String, List<OfcGroupVo>> res = ofcOrderManageOperService.loadGroupList();
            if (res.keySet().size() > 0) {
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, res.get(type));
            } else {
                result = WrapMapper.wrap(Wrapper.ERROR_CODE, "查询结果为空！");
            }
        } catch (BusinessException ex) {
            logger.error("查询组织信息发生异常：异常详情{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("查询组织信息发生未知异常：异常详情{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return result;
    }
    /**
     * 根据所选大区查询基地
     */
    @RequestMapping(
            value = {"/queryBaseListByArea/{areaCode}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    @ApiOperation(
            notes = "根据大区编号查询基地",
            httpMethod = "POST",
            value = "根据大区编号查询基地"
    )
    public Wrapper<?> queryBaseListByArea(@ApiParam(name = "areaCode",value = "大区编码") @PathVariable String areaCode) {
        logger.info("运营中心订单管理根据所选大区查询基地,入参:areaCode = {}", areaCode);
        if (PubUtils.isSEmptyOrNull(areaCode)) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "该大区编码为空!无法查询其基地!");
        }
        UamGroupDto uamGroupDto = new UamGroupDto();
        uamGroupDto.setSerialNo(areaCode);
        List<OfcGroupVo> ofcGroupVoList;
        try {
            ofcGroupVoList = ofcOrderManageOperService.getBaseListByCurArea(uamGroupDto);
        } catch (BusinessException ex) {
            logger.info("根据所选大区查询基地出错：{", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.info("根据所选大区查询基地出错：{", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "根据所选大区查询基地查询成功", ofcGroupVoList);
    }

    @ResponseBody
    @RequestMapping(value = "/queryOrderDetailByOrderCode/{orderCode}", method = {RequestMethod.POST})
    @ApiOperation(value = "根据类型查询组织信息", httpMethod = "POST", notes = "返回组织信息")
    public Wrapper<OfcOrderInfoDTO> queryOrderDetailByOrderCode(@ApiParam(name = "orderCode", value = "订单号") @PathVariable String orderCode) {
        logger.info("==>queryOrderDetailByOrderCode   orderCode:{}", orderCode);
        Wrapper<OfcOrderInfoDTO> result;
        try {
            if (PubUtils.isNull(orderCode)) {
                result = WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, Wrapper.ILLEGAL_ARGUMENT_MESSAGE);
            } else {
                OfcOrderInfoDTO orderInfoDTO = ofcOrderManageOperService.queryOrderDetailByOrderCode(orderCode);
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, orderInfoDTO);
            }
        } catch (BusinessException ex) {
            logger.error("查询订单明细信息发生异常：异常详情{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        } catch (Exception ex) {
            logger.error("查询订单明细信息发生未知异常：异常详情{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return result;
    }

    /**
     * 根据所选基地反查大区
     */
    @RequestMapping(value = "queryAreaMsgByBase/{baseCode}", method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "根据所选基地反查大区", httpMethod = "POST", notes = "返回组织信息")
    public Wrapper<?> queryAreaMsgByBase(@ApiParam(name = "orderCode", value = "订单号") @PathVariable String baseCode) {
        logger.info("根据所选基地反查大区,入参:baseCode = {}", baseCode);
        if (PubUtils.isSEmptyOrNull(baseCode)) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "该基地编码为空!无法查询其所属大区!");
        }
        UamGroupDto uamGroupDto = new UamGroupDto();
        uamGroupDto.setSerialNo(baseCode);
        OfcGroupVo ofcGroupVo;
        try {
            ofcGroupVo = ofcOrderManageOperService.queryAreaMsgByBase(uamGroupDto);
        } catch (BusinessException ex) {
            logger.info("根据所选基地反查大区出错：{", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.info("根据所选基地反查大区出错：{", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "根据所选基地反查大区查询成功", ofcGroupVo);
    }

    /**
     * 审核与反审核订单
     *
     * @param auditOrderDTO 审核参数
     * @return      Wrapper
     */
    @ResponseBody
    @RequestMapping(value = "/auditTransportOrder", method = RequestMethod.POST)
    @ApiOperation(value = "审核、反审核订单", httpMethod = "POST", notes = "审核、反审核结果")
    public Wrapper<String> auditTransportOrder(@ApiParam(name = "auditOrderDTO", value = "订单审核实体") @RequestBody AuditOrderDTO auditOrderDTO) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            if (auditOrderDTO != null && StringUtils.isBlank(auditOrderDTO.getOrderCode())) {
                throw new BusinessException("订单编号不能为空！");
            } else if (StringUtils.isBlank(auditOrderDTO.getReviewTag())) {
                throw new BusinessException("订单标识不能为空！");
            }
            String result = ofcOrderManageService.orderAutoAuditForTran(auditOrderDTO.getOrderCode(), auditOrderDTO.getReviewTag(), authResDtoByToken);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "订单审核成功", result);
        } catch (BusinessException ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }
}
