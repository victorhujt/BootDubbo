package com.xescm.ofc.web.restcontroller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcExceptOrder;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcExceptOrderDTO;
import com.xescm.ofc.service.OfcExceptOrderService;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/ofc/except/order")
public class OfcExceptOrderRestController extends BaseController{

    @Resource
    private OfcExceptOrderService ofcExceptOrderService;


    @RequestMapping(value = "select", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<PageInfo<OfcExceptOrder>> selectExceptOrders(OfcExceptOrderDTO exceptOrderDTO) {
        logger.info("运营平台查询异常订单{}", exceptOrderDTO);
        try {
            if (null == exceptOrderDTO) throw new BusinessException("运营平台查询异常订单出错");
            PageHelper.startPage(exceptOrderDTO.getPageNum(), exceptOrderDTO.getPageSize());
            getAuthResDtoByToken();
            List<OfcExceptOrder> ofcExceptOrders = ofcExceptOrderService.selectByDTO(exceptOrderDTO);
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

}
