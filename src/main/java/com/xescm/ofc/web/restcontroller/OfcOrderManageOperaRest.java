package com.xescm.ofc.web.restcontroller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.model.dto.form.OrderStorageOperForm;
import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.service.*;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.model.dto.group.UamGroupDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
/**
 * 运营中心 订单管理
 * Created by hiyond on 2016/11/22.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderManageOperaRest extends BaseController {

    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcBatchOrderVoService ofcBatchOrderVoService;
    @Resource
    private OrderFollowOperService orderFollowOperService;

    /**
     * 查询订单
     *
     * @param page  分页
     * @param form      查询实体
     * @return Object
     */
    @RequestMapping(value = "/queryOrderStorageDataOper", method = {RequestMethod.POST})
    @ResponseBody
    public Object queryOrderStorageDataOper(Page<OrderOperForm> page, OrderStorageOperForm form, String tag) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            AuthResDto authResDto = getAuthResDtoByToken();
            List<OrderSearchOperResult> dataList = ofcOrderManageOperService.queryOrderStorageDataOper(authResDto, form,tag);
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

    @RequestMapping(value = "/queryOrderDataOper", method = {RequestMethod.POST})
    @ResponseBody
    public Object queryOrderOper(Page<OrderOperForm> page, OrderOperForm form) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            AuthResDto authResDto = getAuthResDtoByToken();
            List<OrderSearchOperResult> dataList = ofcOrderManageOperService.queryOrderList(authResDto, form);
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









    /**
     * 审核与反审核订单
     *
     * @param orderCode     订单编号
     * @param orderStatus       订单状态
     * @param reviewTag     审核标记
     * @return      Wrapper
     */
    @RequestMapping(value = "/orderOrNotAuditOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderAuditOper(String orderCode, String orderStatus, String reviewTag) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
            if (StringUtils.isBlank(orderStatus)) {
                throw new Exception("订单状态不能为空！");
            }
            if (StringUtils.isBlank(reviewTag)) {
                throw new Exception("订单标识不能为空！");
            }
            String result = ofcOrderManageService.orderAudit(orderCode, orderStatus, reviewTag, authResDtoByToken);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } catch (BusinessException ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     * 仓储订单的审核与反审核  暂用该方法    如果改版后的方法兼容  该方法可去除
     * @param orderCode
     * @param reviewTag
     * @return
     */
    @RequestMapping(value = "/auditOrderOrNotAuditOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> auditOrderOrNotAuditOper(String orderCode,String reviewTag) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
            if (StringUtils.isBlank(reviewTag)) {
                throw new Exception("订单标识不能为空！");
            }
            String result = ofcOrderManageService.auditStorageOrder(orderCode,reviewTag, authResDtoByToken);
            if(!result.equals(String.valueOf(Wrapper.SUCCESS_CODE))){
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "审核或反审核出现异常");
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
        } catch (BusinessException ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }


    /**
     * 订单删除
     *
     * @param orderCode     订单编号
     * @return  Wrapper
     */
    @RequestMapping(value = "/orderDeleteOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderDeleteOper(String orderCode, String orderStatus) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
            if (StringUtils.isBlank(orderStatus)) {
                throw new Exception("订单状态不能为空！");
            }
            String result = ofcOrderManageService.orderDelete(orderCode, orderStatus, authResDtoByToken);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } catch (BusinessException ex) {
            logger.error("订单中心订单管理订单删除出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单删除出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }


    /**
     * 订单取消
     * @param orderCode     订单编号
     * @return  Wrapper
     */
    @RequestMapping(value = "/orderCancelOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderCancelOper(String orderCode) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
            String result = ofcOrderManageService.orderCancel(orderCode,authResDtoByToken);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } catch (BusinessException ex) {
            logger.error("订单中心订单管理订单取消出现异常orderCode：{},{}", "", orderCode, ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单取消出现异常orderCode：{},orderStatus：{},{}", "", orderCode, orderStatus, ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "订单中心订单管理订单取消出现异常");
        }
    }


    /**
     * 订单的复制
     * @param orderCode
     * @return
     */
    @RequestMapping(value ="/copyOrderOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> copyOrder(String orderCode){
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        String result;
        try{
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
         result = ofcOrderManageService.copyOrder(orderCode,authResDtoByToken);
    } catch (Exception ex) {
        logger.error("订单中心订单管理订单复制出现异常orderCode：{},{}", "", orderCode,ex.getMessage(), ex);
        return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
    }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,result);
    }

    /**
     * 订单详情
     *
     * @param orderCode     订单编号
     * @return      ModelAndView
     */
    @RequestMapping(value = "/orderDetailPageByCode/{orderCode}", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView orderDetailByOrderCode(@PathVariable String orderCode,Model model) {
        ModelAndView modelAndView = new ModelAndView("order_detail_opera");
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号为空");
            }
            //订单基本信息
            OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
            ofcFundamentalInformation.setOrderCode(orderCode);
            ofcFundamentalInformation = ofcFundamentalInformationService.selectOne(ofcFundamentalInformation);
            //订单配送基本信息
            OfcDistributionBasicInfo ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
            ofcDistributionBasicInfo.setOrderCode(orderCode);
            ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectOne(ofcDistributionBasicInfo);
            //财务信息
            OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(orderCode);
            //订单状态集合
            OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
            ofcOrderStatus.setOrderCode(orderCode);
//            List<OfcOrderStatus> ofcOrderStatusList = ofcOrderStatusService.select(ofcOrderStatus);
            List<OfcOrderStatus> ofcOrderStatusList = orderFollowOperService.queryOrderStatus(orderCode, "orderCode");
//            ofcOrderStatusList = SortOrderStatusUtils.sortOrderStatus(ofcOrderStatusList);
            //最新订单状态
            ofcOrderStatus = ofcOrderStatusService.queryLastUpdateOrderByOrderCode(orderCode);
            //货品信息
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
//            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);

            if (ofcFinanceInformation != null) {
                ofcFinanceInformation.setPickUpGoods(defalutString(ofcFinanceInformation.getPickUpGoods()));
                ofcFinanceInformation.setTwoDistribution(defalutString(ofcFinanceInformation.getTwoDistribution()));
                ofcFinanceInformation.setReturnList(defalutString(ofcFinanceInformation.getReturnList()));
                ofcFinanceInformation.setInsure(defalutString(ofcFinanceInformation.getInsure()));
                ofcFinanceInformation.setCollectFlag(defalutString(ofcFinanceInformation.getCollectFlag()));
                ofcFinanceInformation.setOpenInvoices(defalutString(ofcFinanceInformation.getOpenInvoices()));
            }

            modelAndView.addObject("ofcFundamentalInformation", ofcFundamentalInformation);
            modelAndView.addObject("ofcDistributionBasicInfo", ofcDistributionBasicInfo);
            modelAndView.addObject("ofcFinanceInformation", ofcFinanceInformation);
            modelAndView.addObject("ofcFundamentalInformation", ofcFundamentalInformation);
            modelAndView.addObject("ofcGoodsDetailsInfoList", ofcGoodsDetailsInfoList);
            modelAndView.addObject("ofcOrderStatusList", ofcOrderStatusList);

            modelAndView.addObject("ofcOrderStatus", ofcOrderStatus);
            setDefaultModel(model);
        }catch (Exception ex) {
            logger.error("订单中心订单管理订单取消出现异常orderCode：{},{}", orderCode, ex.getMessage(), ex);
        }
        return modelAndView;
    }

    private String defalutString(String str) {
        return StringUtils.equals(str, "1") ? "是" : "否";
    }

    /**
     * 订单批次查询
     *
     * @param orderBatchCode    订单批次号
     * @return      ModelAndView
     */
    @RequestMapping(value = "/orderDetailBatchOpera/{orderBatchCode}", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView orderDetailBatchOpera(@PathVariable String orderBatchCode, Model model) {
        ModelAndView modelAndView = new ModelAndView("order_detail_batch_opera");
        try {
            if (StringUtils.isBlank(orderBatchCode)) {
                throw new Exception("订单批次号不能为空！");
            }
            OfcBatchOrderVo ofcBatchOrderVo = ofcBatchOrderVoService.queryByBatchNumber(orderBatchCode);
            modelAndView.addObject("ofcBatchOrderVo", ofcBatchOrderVo);
            modelAndView.addObject("orderBatchNumber", orderBatchCode);
        }catch (Exception ex) {
            logger.error("订单批次号查询出错：orderBatchCode{},{}", orderBatchCode, ex.getMessage(), ex);
        }
        setDefaultModel(model);
        return modelAndView;
    }


    /**
     * 根据批次号查询订单
     *
     * @param page  分页
     * @param form  查询实体
     * @return Object
     */
    @RequestMapping(value = "queryOrderByOrderBatchNumber", method = {RequestMethod.POST})
    @ResponseBody
    public Object queryOrderByOrderBatchNumber(Page<OrderOperForm> page, OrderOperForm form) {
        logger.debug("根据批次号查询订单page:{},form:{}", page, form);
        String orderBatchNumber = form.getOrderBatchNumber();
        try {
            if (StringUtils.isBlank(orderBatchNumber)) {
                throw new Exception("订单批次号不能为空");
            }
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OrderSearchOperResult> ofcBatchOrderVos = ofcOrderManageOperService.queryOrderByOrderBatchNumber(orderBatchNumber);
            PageInfo<OrderSearchOperResult> pageInfo = new PageInfo<>(ofcBatchOrderVos);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException ex) {
            logger.info("订单批次号查询出错：orderBatchNumer{},{}", orderBatchNumber, ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.info("订单批次号查询出错：orderBatchNumer{},{}", orderBatchNumber, ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     * 根据订单编号查询货品信息
     *
     * @param orderCode 订单编号
     * @return Object
     */
    @RequestMapping(value = "queryGoodsInfoByOrderCode", method = {RequestMethod.POST})
    @ResponseBody
    public Object queryGoodsInfoByOrderCode(String orderCode) {
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单号不能为空");
            }
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            List<OfcGoodsDetailsInfo> ofcBatchOrderVos = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ofcBatchOrderVos);
        } catch (BusinessException ex) {
            logger.info("订单号查询出错：orderCode{},{}", orderCode, ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.info("订单号查询出错：orderCode{},{}", orderCode, ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     * 根据所选大区查询基地
     */
    @RequestMapping(value = "queryBaseListByArea", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> queryBaseListByArea(String areaCode) {
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

    /**
     * 根据所选基地反查大区
     */
    @RequestMapping(value = "queryAreaMsgByBase", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> queryAreaMsgByBase(String baseCode) {
        logger.info("运营中心订单管理根据所选基地反查大区,入参:baseCode = {}", baseCode);
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
}