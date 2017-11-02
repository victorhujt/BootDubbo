package com.xescm.ofc.web.restcontroller.operationWorkbench.orderRevise;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.service.OfcOrderReviseService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountDetailDto;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dragon on 2017/8/30.
 */
@Controller
@RequestMapping(value = "/page/ofc/orderRevise")
public class OfcOrderReviseController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcOrderReviseService ofcOrderReviseService;

    /**
     * 订单修改
     *
     * @param ofcOrderDTOStr 订单基本信息、收发货方信息
     * @return Wrapper
     */
    @RequestMapping("/orderReviseCon")
    @ResponseBody
    public Wrapper<?> orderPlace(@RequestBody OfcOrderDTO ofcOrderDTOStr) {
        logger.info("==>订单修改实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        String resultMessage;
        String orderCode = ofcOrderDTOStr.getOrderCode();
        try {
            if (PubUtils.isSEmptyOrNull(orderCode)) {
                throw new BusinessException("订单号不能为空");
            }
            if (CollectionUtils.isEmpty(ofcOrderDTOStr.getGoodsList())) {
                throw new BusinessException("订单{"+orderCode+"}货品不能为空");
            }
            boolean flag = checkReviseStatus(ofcOrderDTOStr);
            if (flag) {
                resultMessage = "订单修改成功!";
            } else {
                resultMessage = "订单修改失败!";
            }
        } catch (BusinessException ex) {
            logger.error("订单修改出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单修改出现未知异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, resultMessage);
    }


    // 修改时询问结算中心是否可以修改（未结算）
    public boolean checkReviseStatus(OfcOrderDTO ofcOrderDTOStr) {
        // 封装
        GoodsAmountSyncDto goodsAmountSyncDto = new GoodsAmountSyncDto();
        List<GoodsAmountDetailDto> goodsAmountDetailDtos = new ArrayList<GoodsAmountDetailDto>();
        // 赋值
        goodsAmountSyncDto.setCustCode(ofcOrderDTOStr.getCustCode());
        goodsAmountSyncDto.setCustName(ofcOrderDTOStr.getCustName());
        goodsAmountSyncDto.setCustOrderCode(ofcOrderDTOStr.getCustOrderCode());
        // 封装
        List<OfcGoodsDetailsInfo> goodsList = ofcOrderDTOStr.getGoodsList();
        for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : goodsList) {
            GoodsAmountDetailDto goodsAmountDetailDto = new GoodsAmountDetailDto();
            // 行号
            if (!PubUtils.isOEmptyOrNull(ofcGoodsDetailsInfo.getPaasLineNo())){
                goodsAmountDetailDto.setPassLineNo(ofcGoodsDetailsInfo.getPaasLineNo());
            }
            // 货品编码
            if (!PubUtils.isOEmptyOrNull(ofcGoodsDetailsInfo.getGoodsCode())){
                goodsAmountDetailDto.setGoodsCode(ofcGoodsDetailsInfo.getGoodsCode());
            }
            // 货品名称
            if (!PubUtils.isOEmptyOrNull(ofcGoodsDetailsInfo.getGoodsName())){
                goodsAmountDetailDto.setGoodsName(ofcGoodsDetailsInfo.getGoodsName());
            }
            if (!PubUtils.isOEmptyOrNull(ofcGoodsDetailsInfo.getQuantity())){
                goodsAmountDetailDto.setQty(ofcGoodsDetailsInfo.getQuantity().toString());
            }
            if (!PubUtils.isOEmptyOrNull(ofcGoodsDetailsInfo.getUnit())){
                goodsAmountDetailDto.setUnit(ofcGoodsDetailsInfo.getUnit());
            }
            if (!PubUtils.isOEmptyOrNull(ofcGoodsDetailsInfo.getCubage())){
                goodsAmountDetailDto.setVolume(ofcGoodsDetailsInfo.getCubage().toString());
            }
            goodsAmountDetailDto.setWeight(ofcGoodsDetailsInfo.getWeight().toString());
            goodsAmountDetailDtos.add(goodsAmountDetailDto);
        }
        // 赋值
        goodsAmountSyncDto.setGoodsAmountDetailDtoList(goodsAmountDetailDtos);
        // 校验
        Wrapper<?> result = ofcOrderReviseService.goodsAmountSync(goodsAmountSyncDto,ofcOrderDTOStr.getOrderCode());
        if (Wrapper.SUCCESS_CODE == result.getCode()) {
            return true;
        } else {
            return false;
        }
    }
}
