package com.xescm.ofc.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.ofc.config.RestConfig;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcAttachment;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.enums.BusinessTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.dto.ofc.AttachmentDto;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
import com.xescm.ofc.service.OfcAttachmentService;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.PubUtils;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 手机订单
 * Created by hujintao on 2016/12/15.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcMobileOrderRest extends BaseController {
    @Autowired
    private OfcAttachmentService ofcAttachmentService;

    @Autowired
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
        logger.debug("==>手机订单详情code code={}", code);
        ModelAndView modelAndView = new ModelAndView("mobile_order_detail_opera");
        modelAndView.addObject(OrderConstConstant.OFC_WEB_URL, restConfig.getOfcWebUrl());
        OfcMobileOrder condition = new OfcMobileOrder();
        condition.setMobileOrderCode(code);
        OfcMobileOrderVo mobileOrder = ofcMobileOrderService.selectOneOfcMobileOrder(condition);
        modelAndView.addObject("mobileOrder", mobileOrder);
        return modelAndView;
    }

    @RequestMapping(value = "/acceptMobileOrder/{orderCode}")
    public ModelAndView acceptMobileOrder(@PathVariable("orderCode") String code) {
        logger.debug("==>手机订单详情code code={}", code);
        ModelAndView modelAndView = new ModelAndView("mobile_order_accept_opera");
        modelAndView.addObject(OrderConstConstant.OFC_WEB_URL, restConfig.getOfcWebUrl());
        OfcMobileOrder condition = new OfcMobileOrder();
        condition.setMobileOrderCode(code);
        OfcMobileOrderVo mobileOrder = ofcMobileOrderService.selectOneOfcMobileOrder(condition);
        modelAndView.addObject("mobileOrder", mobileOrder);
        return modelAndView;
    }

    /**
     *
     * @param model
     * @param ofcOrderDTOStr        订单基本信息、收发货方信息
     * @param orderGoodsListStr     货品信息
     * @param cscContantAndCompanyDtoConsignorStr   发货人信息
     * @param cscContantAndCompanyDtoConsigneeStr   收货人信息
     * @param cscSupplierInfoDtoStr                 供应商信息
     * @param tag           标识下单、编辑、运输开单
     * @param response
     * @return
     */
    @RequestMapping("/mobileorderPlaceCon")
    @ResponseBody
    public Wrapper<?> mobileOrderPlace(Model model, String ofcOrderDTOStr, String orderGoodsListStr, String cscContantAndCompanyDtoConsignorStr
            , String cscContantAndCompanyDtoConsigneeStr, String cscSupplierInfoDtoStr, String tag, HttpServletResponse response, String mobileOrderCode){
        logger.debug("==>拍照下单订单中心下单或编辑实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        logger.debug("==>拍照下单订单中心下单或编辑标志位 tag={}", tag);
        String resultMessage = null;
        String orderCode="";
        try {
            orderGoodsListStr = orderGoodsListStr.replace("~`","");
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if(PubUtils.isSEmptyOrNull(ofcOrderDTOStr)){
                ofcOrderDTOStr = JSONUtils.objectToJson(new OfcOrderDTO());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignorStr)){
                cscContantAndCompanyDtoConsignorStr = JSONUtils.objectToJson(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsigneeStr)){
                cscContantAndCompanyDtoConsigneeStr = JSONUtils.objectToJson(new CscContantAndCompanyDto());
            }
            if(PubUtils.isSEmptyOrNull(cscSupplierInfoDtoStr)){
                cscSupplierInfoDtoStr = JSONUtils.objectToJson(new CscSupplierInfoDto());
            }
            // List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<OfcGoodsDetailsInfo>();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                //orderGoodsListStr = JSONUtils.objectToJson(new OfcGoodsDetailsInfo());
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            OfcOrderDTO ofcOrderDTO = JSONUtils.jsonToPojo(ofcOrderDTOStr, OfcOrderDTO.class);
            logger.info(cscContantAndCompanyDtoConsignorStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignor = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsignorStr, CscContantAndCompanyDto.class);
            logger.info(cscContantAndCompanyDtoConsigneeStr);
            CscContantAndCompanyDto cscContantAndCompanyDtoConsignee = JSONUtils.jsonToPojo(cscContantAndCompanyDtoConsigneeStr, CscContantAndCompanyDto.class);
            if(cscContantAndCompanyDtoConsignor==null){
                throw new BusinessException("发货人信息不允许为空！");
            }
            if(cscContantAndCompanyDtoConsignee==null){
                throw new BusinessException("收货人信息不允许为空！");
            }
            CscSupplierInfoDto cscSupplierInfoDto = JSONUtils.jsonToPojo(cscSupplierInfoDtoStr,CscSupplierInfoDto.class);
            //校验业务类型，如果是卡班，必须要有运输单号
            if(StringUtils.equals(ofcOrderDTO.getBusinessType(), BusinessTypeEnum.CABANNES.getCode())){
                if(StringUtils.isBlank(ofcOrderDTO.getTransCode())){
                    throw new Exception("业务类型是卡班，运输单号是必填项");
                }
            }
            if(null !=ofcOrderDTO){
                if (null == ofcOrderDTO.getProvideTransport()){
                    ofcOrderDTO.setProvideTransport(OrderConstConstant.WAREHOUSEORDERNOTPROVIDETRANS);
                }
                if (null == ofcOrderDTO.getUrgent()){
                    ofcOrderDTO.setUrgent(OrderConstConstant.DISTRIBUTIONORDERNOTURGENT);
                }
            }else{
                return com.xescm.ofc.wrap.WrapMapper.wrap(Wrapper.ERROR_CODE,"订单相关信息有误");
            }
            resultMessage = ofcMobileOrderService.placeOrder(ofcOrderDTO,ofcGoodsDetailsInfos,tag,authResDtoByToken,authResDtoByToken.getGroupRefCode()
                    ,cscContantAndCompanyDtoConsignor,cscContantAndCompanyDtoConsignee,cscSupplierInfoDto,orderCode);

            if("未定义错误".equals(resultMessage)||"用户操作异常".equals(resultMessage)||"页面跳转出错".equals(resultMessage)){
                return com.xescm.ofc.wrap.WrapMapper.wrap(Wrapper.ERROR_CODE,resultMessage);
            }
            //更新拍照订单的状态，订单号
            OfcMobileOrder order=new OfcMobileOrder();
            order.setMobileOrderCode(mobileOrderCode);
            order.setAccepter(authResDtoByToken.getUserName());
            order.setAppcetDate(new Date());
            order.setMobileOrderStatus("1");//已处理
            order.setOrderCode(orderCode);
            ofcMobileOrderService.updateByMobileCode(order);
        } catch (BusinessException ex){
            logger.error("订单中心下单或编辑出现异常:{}", ex.getMessage(), ex);
            //更新拍照订单的状态，订单号
            OfcMobileOrder order=new OfcMobileOrder();
            order.setMobileOrderCode(mobileOrderCode);
           // order.setAccepter(authResDtoByToken.getUserName());
            order.setAppcetDate(new Date());
            order.setMobileOrderStatus("1");//已处理
            order.setOrderCode(orderCode);
            ofcMobileOrderService.updateByMobileCode(order);
            return com.xescm.ofc.wrap.WrapMapper.wrap(Wrapper.ERROR_CODE,"订单中心下单或编辑出现异常");
        } catch (Exception ex) {
            if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                logger.error("订单中心下单或编辑出现异常:{}", "获取订单号发生重复!", ex);
                return com.xescm.ofc.wrap.WrapMapper.wrap(Wrapper.ERROR_CODE, "获取订单号发生重复!");
            } else {
                logger.error("订单中心下单或编辑出现未知异常:{}", ex.getMessage(), ex);
                return com.xescm.ofc.wrap.WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
            }
        }
        return com.xescm.ofc.wrap.WrapMapper.wrap(Wrapper.SUCCESS_CODE,resultMessage);
    }

    @RequestMapping(value = "/mobileOrder/updatePicParamByserialNo", method = RequestMethod.POST)
    public Wrapper<?> updatePicParamByserialNo(AttachmentDto attachmentDto) {
        OfcAttachment ofcAttachment=new OfcAttachment();
        try {
            if(attachmentDto == null){
                throw new BusinessException("参数不能为空");
            }
            if(StringUtils.isEmpty(attachmentDto.getSerialNo())){
                throw new BusinessException("附件流水号不能为空");
            }

            if(StringUtils.isEmpty(attachmentDto.getPicParam())){
                throw new BusinessException("附件流水号操作命令不能为空");
            }
            logger.info("操作的附件流水号为:{}",attachmentDto.getSerialNo());
            BeanUtils.copyProperties(ofcAttachment,attachmentDto);
            ofcAttachmentService.updatePicParamByserialNo(ofcAttachment);
        } catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.debug("更新附件操作失败={}", e.getMessage(), e);
            e.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,  e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }
}