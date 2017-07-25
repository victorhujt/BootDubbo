package com.xescm.ofc.web.restcontroller.operationWorkbench.distributionOrder;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.csc.provider.CscGoodsEdasService;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by lyh on 2017/7/5.
 */
@Controller
@RequestMapping(value = "/page/ofc/distri/common", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcOperDistriMainController", tags = "OfcOperDistriMainController", description = "城配开单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcOperDistriCommonController extends BaseController{

    @Resource
    private CscGoodsEdasService cscGoodsEdasService;

    @ResponseBody
    @RequestMapping(value = "/loadGoodsBatchImportMsg", method = {RequestMethod.POST})
    @ApiOperation(value = "批量添加货品时页面加载数据", httpMethod = "POST", notes = "城配开单确认下单")
    public Wrapper<List> placeOrdersListCon(@RequestBody String orderLists) {
        logger.info("批量添加货品时页面加载数据==> orderLists={}", orderLists);
        List result;
        try {
            Wrapper<List> listWrapper = cscGoodsEdasService.loadGoodsBatchImportMsg();
            if (listWrapper.getCode() != Wrapper.SUCCESS_CODE) {
                logger.error(listWrapper.getMessage());
                return WrapMapper.wrap(Wrapper.ERROR_CODE, listWrapper.getMessage());
            }
            result = listWrapper.getResult();
        } catch (BusinessException ex) {
            logger.error("批量添加货品时页面加载数据失败!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "批量添加货品时页面加载数据失败!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

}
