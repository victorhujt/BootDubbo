package com.xescm.ofc.web.restcontroller.customerWorkbench.storageOrder;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcSaveStorageDTO;
import com.xescm.ofc.service.OfcCustOrderPlaceService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2017/9/8.
 */

@Controller
@RequestMapping(value = "/page/ofc/cust/storage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcCustStorageOrderController", tags = "OfcCustStorageOrderController", description = "客户工作台仓储订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcCustStorageOrderController extends BaseController{

    @Resource
    private OfcCustOrderPlaceService ofcCustOrderPlaceService;

    @ApiOperation(value = "客户工作台仓储订单下单或编辑", httpMethod = "POST", notes = "返回组织信息")
    @RequestMapping(value ="saveStorage/{tag}", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> saveStorage(@ApiParam(name = "ofcSaveStorageDTO",value = "仓单Dto") @RequestBody OfcSaveStorageDTO ofcSaveStorageDTO, @PathVariable String tag) {
        try {
            ofcCustOrderPlaceService.saveStorageOrder(ofcSaveStorageDTO, tag, getAuthResDtoByToken());
        } catch (BusinessException ex) {
            logger.error("仓储订单下单或编辑出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("仓储订单下单或编辑出现未知异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"仓储下单成功");
    }


}
