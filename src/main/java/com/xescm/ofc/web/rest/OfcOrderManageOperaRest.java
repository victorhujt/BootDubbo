package com.xescm.ofc.web.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.form.OrderOperForm;
import com.xescm.ofc.enums.OrderStatusEnum;
import com.xescm.ofc.enums.PlanEnum;
import com.xescm.ofc.enums.ResourceEnum;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscStoreAPIClient;
import com.xescm.ofc.feign.client.FeignRmcCompanyAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 运营 订单管理
 * Created by hiyond on 2016/11/22.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderManageOperaRest extends BaseController {

    @Autowired
    private OfcOrderManageOperService ofcOrderManageOperService;

    @Autowired
    private OfcOrderManageService ofcOrderManageService;

    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;

    @Autowired
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;

    @Autowired
    private OfcFinanceInformationService ofcFinanceInformationService;

    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Autowired
    private PlanAndStorageService planAndStorageService;
    @Autowired
    private OfcBatchOrderVoService ofcBatchOrderVoService;


    /**
     * 运营→订单管理 orderManageOpera
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/orderManageOpera", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView orderManageOpera(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_manage_opera");
        setDefaultModel(model);
        return modelAndView;
    }

    /**
     * 查询订单
     *
     * @param page
     * @param form
     * @return
     */
    @RequestMapping(value = "/queryOrderDataOper", method = {RequestMethod.POST})
    @ResponseBody
    public Object queryOrderOper(Page<OrderOperForm> page, OrderOperForm form) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OrderScreenResult> dataList = ofcOrderManageOperService.queryOrderOper(form);
            //PageInfo<OrderScreenResult> pageInfo = new PageInfo<OrderScreenResult>(dataList);
            PageInfo<OrderScreenResult> pageInfo = new PageInfo<>(dataList);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (Exception ex) {
            logger.error("运营平台查询订单出错：{}", ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

    /**
     * 审核与反审核订单
     *
     * @param orderCode
     * @param orderStatus
     * @param reviewTag
     * @return
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
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

    /**
     * 订单删除
     *
     * @param orderCode
     * @return
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
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单删除出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

    /**
     * 订单取消
     *
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/orderCancelOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderCancelOper(String orderCode, String orderStatus) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
            if (StringUtils.isBlank(orderStatus)) {
                throw new Exception("订单状态不能为空！");
            }
            String result = ofcOrderManageService.orderCancel(orderCode, orderStatus, authResDtoByToken);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单取消出现异常orderCode：{},orderStatus：{},{},错误：{}", orderCode, orderStatus, ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

    /**
     * 订单详情
     *
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/orderDetailPageByCode/{orderCode}")
    public ModelAndView orderDetailByOrderCode(@PathVariable String orderCode) {
        try {
            ModelAndView modelAndView = new ModelAndView("order_detail_opera");
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号为空");
            }
            OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
            ofcFundamentalInformation.setOrderCode(orderCode);
            ofcFundamentalInformation = ofcFundamentalInformationService.selectOne(ofcFundamentalInformation);
            if (ofcFundamentalInformation != null) {
                //平台类型如果为4为鲜易网，如果不为空为三方
                String platformType = ofcFundamentalInformation.getPlatformType();
                platformType = StringUtils.equals(platformType, "4") ? "鲜易网" : StringUtils.isNotBlank(platformType) ? "三方" : platformType;
                ofcFundamentalInformation.setPlatformType(platformType);
            }

            OfcDistributionBasicInfo ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
            ofcDistributionBasicInfo.setOrderCode(orderCode);
            ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectOne(ofcDistributionBasicInfo);

            OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(orderCode);

            OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
            ofcOrderStatus.setOrderCode(orderCode);
            List<OfcOrderStatus> ofcOrderStatusList = ofcOrderStatusService.select(ofcOrderStatus);
            ofcOrderStatus = ofcOrderStatusService.queryOrderByOrderCode(orderCode);
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
            List<PlanAndStorage> storageList = planAndStorageService.queryPlanAndStorage(orderCode, "");
            List<PlanAndStorage> planList = planAndStorageService.queryPlanAndStorageTrans(orderCode, "");
            storageList.addAll(planList);
            for (PlanAndStorage planAndStorage : storageList) {
                String resourceAllocationStatus = ResourceEnum.getDescByCode(planAndStorage.getResourceAllocationStatus());
                planAndStorage.setResourceAllocationStatus(resourceAllocationStatus);
                String pl = PlanEnum.getDescByCode(planAndStorage.getPlannedSingleState());
                planAndStorage.setPlannedSingleState(pl);
                planAndStorage.setBusinessType(BusinessTypeEnum.getBusinessTypeDescByCode(planAndStorage.getBusinessType()));
            }
            if (ofcFinanceInformation != null) {
                ofcFinanceInformation.setPickUpGoods(defaultString(ofcFinanceInformation.getPickUpGoods()));
                ofcFinanceInformation.setTwoDistribution(defaultString(ofcFinanceInformation.getTwoDistribution()));
                ofcFinanceInformation.setReturnList(defaultString(ofcFinanceInformation.getReturnList()));
                ofcFinanceInformation.setInsure(defaultString(ofcFinanceInformation.getInsure()));
                ofcFinanceInformation.setCollectFlag(defaultString(ofcFinanceInformation.getCollectFlag()));
            }

            modelAndView.addObject("ofcFundamentalInformation", ofcFundamentalInformation);
            modelAndView.addObject("ofcDistributionBasicInfo", ofcDistributionBasicInfo);
            modelAndView.addObject("ofcFinanceInformation", ofcFinanceInformation);
            modelAndView.addObject("ofcFundamentalInformation", ofcFundamentalInformation);
            modelAndView.addObject("ofcGoodsDetailsInfoList", ofcGoodsDetailsInfoList);
            modelAndView.addObject("ofcOrderStatusList", ofcOrderStatusList);

            modelAndView.addObject("storageList", storageList);
            modelAndView.addObject("ofcOrderStatus", ofcOrderStatus);
//            modelAndView.addObject("planList", planList);

            return modelAndView;
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单取消出现异常orderCode：{},{}",  orderCode, ex);
        }
        return null;
    }

    private String defaultString(String flag) {
        return StringUtils.equals(flag, "1") ? "是" : StringUtils.equals(flag, "0") ? "否" : "";
    }

    /**
     * 订单批次
     *
     * @param orderBatchCode
     * @return
     */
    @RequestMapping(value = "/orderDetailBatchOpera/{orderBatchCode}")
    public ModelAndView orderDetailBatchOpera(@PathVariable String orderBatchCode) {
        ModelAndView modelAndView = new ModelAndView("order_detail_batch_opera");
        try {
            if (StringUtils.isBlank(orderBatchCode)) {
                throw new Exception("订单批次号不能为空！");
            }
            OfcBatchOrderVo ofcBatchOrderVo = ofcBatchOrderVoService.queryByBatchNumber(orderBatchCode);
            modelAndView.addObject("ofcBatchOrderVo", ofcBatchOrderVo);
            modelAndView.addObject("orderBatchNumber", orderBatchCode);
        } catch (Exception ex) {
            logger.info("订单批次号查询出错：orderBatchCode{},{}", orderBatchCode, ex);
        }
        return modelAndView;
    }


    /**
     * 根据批次号查询订单
     *
     * @param page
     * @param form
     * @return
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
            List<OfcFundamentalInformation> ofcBatchOrderVos = ofcFundamentalInformationService.queryOrderByOrderBatchNumber(orderBatchNumber);
            //PageInfo<OfcFundamentalInformation> pageInfo = new PageInfo<OfcFundamentalInformation>(ofcBatchOrderVos);
            PageInfo<OfcFundamentalInformation> pageInfo = new PageInfo<>(ofcBatchOrderVos);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (Exception ex) {
            logger.info("订单批次号查询出错：orderBatchNumer{},{}", orderBatchNumber, ex);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ex.getMessage());
        }
    }

    @RequestMapping(value = "/queryGoodsInfoByOrderCode", method = {RequestMethod.POST})
    @ResponseBody
    private Object queryGoodsInfoByOrderCode(String orderCode) {
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单号不能为空");
            }
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
            ofcGoodsDetailsInfo.setOrderCode(orderCode);
            List<OfcGoodsDetailsInfo> ofcBatchOrderVos = ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ofcBatchOrderVos);
        } catch (Exception ex) {
            logger.info("订单号查询出错：orderCode{},{}", orderCode, ex);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ex.getMessage());
        }
    }

    @RequestMapping("selectCustPage")
    public ModelAndView selectCustPage() {
        return new ModelAndView("select_cust_page");
    }

    @RequestMapping(value = "querySelectCustData", method = RequestMethod.POST)
    @ResponseBody
    public Object querySelectCustData(String custName) {
        logger.info("获取客户信息：custName{}", custName);
        try {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, custName);
        } catch (Exception ex) {
            logger.error("获取客户信息失败：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ex.getMessage());
        }
    }

}
