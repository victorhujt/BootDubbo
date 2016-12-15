package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcMobileOrderDto;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujintao on 2016/12/12.
 */
@RequestMapping(value = "/ofc/api", produces = {"application/json;charset=UTF-8"})
@RestController
public class OfcMobileOrderRest extends BaseController {


    @Autowired
    private OfcMobileOrderService ofcMobileOrderService;

    @RequestMapping(value = "mobileOrder/saveMobileOrder", method = RequestMethod.POST)
    @ApiOperation(value = "保存手机订单信息", response = Wrapper.class)
    public Wrapper<?> saveMobileOrder(@ApiParam(name = "ofcMobileOrderDto", value = "手机订单信息") @RequestBody  OfcMobileOrderDto ofcMobileOrderDto) {
        logger.debug("==>保存拍照录单信息 mobileOrder={}", ofcMobileOrderDto);
        try {
            if(ofcMobileOrderDto == null){
                throw new BusinessException("参数不能为空");
            }
            OfcMobileOrder ofcMobileOrder=new OfcMobileOrder();
            BeanUtils.copyProperties(ofcMobileOrderDto,ofcMobileOrder);
            ofcMobileOrderService.save(ofcMobileOrder);
        } catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.debug("保存拍照录单信息={}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,  e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }


    /**
     * 流水号查询拍照录单订单
     * @param
     * @return
     */
    @RequestMapping(value="mobileOrder/queryMobileOrderByCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通过流水号查询手机订单信息", notes = "返回包装手机订单信息json", response = Wrapper.class)
    private Wrapper<?> queryMobileOrderByCode(@ApiParam(name ="ofcMobileOrderDto", value = "手机订单信息") @RequestBody OfcMobileOrderDto ofcMobileOrderDto){
        OfcMobileOrder result=null;
        try {
            if(ofcMobileOrderDto == null){
                throw new BusinessException("参数不能为空");
            }
            if (StringUtils.isBlank(ofcMobileOrderDto.getMobileOrderCode())){
                throw new BusinessException("流水号不能为空!");
            }
            OfcMobileOrder condition=new OfcMobileOrder();
            BeanUtils.copyProperties(ofcMobileOrderDto,condition);
             result= ofcMobileOrderService.selectOne(condition);
        } catch (Exception e) {
            logger.error("订单号查询出错：orderCode{},{}", ofcMobileOrderDto.getMobileOrderCode(), e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE,e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }
}
