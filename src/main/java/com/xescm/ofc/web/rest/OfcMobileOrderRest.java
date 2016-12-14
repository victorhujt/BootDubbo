package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.PubUtils;
import com.xescm.uam.utils.PublicUtil;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujintao on 2016/12/12.
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcMobileOrderRest extends BaseController {

    @Autowired
    private OfcMobileOrderService ofcMobileOrderService;

    /**
     * 获取丁丁的企业名称
     */
    @RequestMapping(value="/queryCustomerName", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> queryCustomerName(){
        return null;
    }

    @RequestMapping("/saveMobileOrder")
    @ResponseBody
    public ModelAndView saveMobileOrder(OfcMobileOrder mobileOrder){
        ModelAndView modelAndView = new ModelAndView("");
        try {
            if(mobileOrder!=null){
                ofcMobileOrderService.saveOfcMobileOrder(mobileOrder);
            }
            modelAndView.addObject("mobileOrder",mobileOrder);
        } catch (Exception ex) {
        }
            return modelAndView;
    }

    /**
     * 获取录单记录
     * @return
     */
    @RequestMapping(value="/queryOrderNotes", method = RequestMethod.POST)
    @ResponseBody
    public List<OfcMobileOrder> queryOrderNotes(String mobileOrderStatus){
        ModelAndView modelAndView = new ModelAndView("");
        List<OfcMobileOrder> ofcMobileOrder=new ArrayList<>();
        try {
            mobileOrderStatus=PubUtils.trimAndNullAsEmpty(mobileOrderStatus);
            ofcMobileOrder=ofcMobileOrderService.queryOrderNotes(mobileOrderStatus);
        }catch (Exception e){
            modelAndView.addObject("info","查询失败");
            logger.info("查询历史订单异常，{}",e.getMessage(),e);
        }
        return ofcMobileOrder;
    }


   // @RequestMapping(value = "/queryMobileOrderDataOper", method = {RequestMethod.POST})
 //   @ResponseBody
//    public Object queryOrderOper(Page<OrderOperForm> page, OrderOperForm form) {
//        try {
//            PageHelper.startPage(page.getPageNum(), page.getPageSize());
//            List<OrderSearchOperResult> dataList = ofcOrderManageOperService.queryOrderList(form);
//            PageInfo<OrderSearchOperResult> pageInfo = new PageInfo<>(dataList);
//            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
//        } catch (Exception ex) {
//            logger.error("运营平台查询订单出错：{}", ex);
//            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
//        }
//    }

    @RequestMapping(consumes = "multipart/form-data", value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> uploadFile(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
            AuthResDto authResDto = getAuthResDtoByToken();
        //    String attachmentSerialNo = dmsAttachmentService.uploadFile(multipartRequest,authResDto);
         //   map.put("serialNO", attachmentSerialNo);
        } catch (BusinessException ex) {
            logger.error("上传照片, 出现异常={}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("上传照片, 出现异常={}", ex.getMessage(), ex);
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, map);
    }

    /**
     * 流水号查询拍照录单订单
     * @param mobileOrderCode  流水号
     * @return
     */
    @RequestMapping(value="/queryMobileOrderByCode", method = RequestMethod.POST)
    @ResponseBody
    private Wrapper<?> queryMobileOrderByCode(String  mobileOrderCode){
        OfcMobileOrder result=null;
        try {
            if (StringUtils.isBlank(mobileOrderCode)) {
                throw new Exception("流水号不能为空!");
            }
            OfcMobileOrder orderCondition=new OfcMobileOrder();
            orderCondition.setMobileOrderCode(mobileOrderCode);
             result= ofcMobileOrderService.selectOne(orderCondition);
        } catch (Exception e) {
            logger.info("订单号查询出错：orderCode{},{}", mobileOrderCode, e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE,e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }
}
