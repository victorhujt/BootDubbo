package com.xescm.ofc.web.restcontroller.operationWorkbench.exceptOrder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcExceptOrder;
import com.xescm.ofc.enums.OrderPotEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcExceptOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcKVDTO;
import com.xescm.ofc.service.OfcExceptOrderService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/page/ofc/except/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcExceptOrderRestController", tags = "OfcExceptOrderRestController", description = "异常订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcExceptOrderMainController extends BaseController{

    @Resource
    private OfcExceptOrderService ofcExceptOrderService;

    @RequestMapping(value = "/queryData", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<PageInfo<OfcExceptOrder>> selectExceptOrders(@RequestBody OfcExceptOrderDTO ofcExceptOrderDTO) {
        logger.info("运营平台查询异常订单{}", ofcExceptOrderDTO);
        try {
            if (null == ofcExceptOrderDTO){
                throw new BusinessException("运营平台查询异常订单出错");
            }
            PageHelper.startPage(ofcExceptOrderDTO.getPageNum(), ofcExceptOrderDTO.getPageSize());
            getAuthResDtoByToken();
            List<OfcExceptOrder> ofcExceptOrders = ofcExceptOrderService.selectByDTO(ofcExceptOrderDTO);
            PageInfo<OfcExceptOrder> pageInfo = new PageInfo<>(ofcExceptOrders);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException ex) {
            logger.error("运营平台查询异常订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("运营平台查询异常订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @RequestMapping(value = "/loadYesterdayOrder", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<PageInfo<OfcExceptOrder>> loadYesterdayOrder() {
        logger.info("加载前一天0点至24点所有订单到各节点Redis库下");
        try {
            int num = ofcExceptOrderService.loadYesterdayOrder();
            if (num == 0) {
                throw new BusinessException("加载前一天0点至24点所有订单到各节点Redis库下失败, 查无订单");
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
        } catch (BusinessException ex) {
            logger.error("运营平台查询异常订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("运营平台查询异常订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @RequestMapping(value = "loadPots", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<List<OfcKVDTO>> loadPots() {
        List<OfcKVDTO> list;
        try {
            list = OrderPotEnum.getList();
            if (CollectionUtils.isEmpty(list)){
                throw new BusinessException("查询异常订单结点出错");
            }
        } catch (BusinessException ex) {
            logger.error("查询异常订单结点出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("查询异常订单结点出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, list);
    }

}
