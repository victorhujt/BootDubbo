package com.xescm.ofc.service.impl;

import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.provider.CscGoodsEdasService;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.csc.CscGoodsApiDTO;
import com.xescm.ofc.service.OfcOperCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * Created by lyh on 2017/7/11.
 */
@Service
public class OfcOperCommonServiceImpl implements OfcOperCommonService {

    Logger logger = LoggerFactory.getLogger(OfcOperCommonServiceImpl.class);

    @Resource
    private CscGoodsEdasService cscGoodsEdasService;

    @Override
    public PageInfo<CscGoodsApiVo> queryGoodsByPage(CscGoodsApiDTO cscGoodsApiDto) {
        //对入参进行检验
        this.checkGoodsApiDto(cscGoodsApiDto);
        CscGoodsApiDto dto = new CscGoodsApiDto();
        BeanUtils.copyProperties(cscGoodsApiDto, dto);
        dto.setPNum(cscGoodsApiDto.getPageNum());
        dto.setPSize(cscGoodsApiDto.getPageSize());
        Wrapper<PageInfo<CscGoodsApiVo>> pageInfoWrapper = cscGoodsEdasService.queryCscGoodsPageList(dto);
        if (null == pageInfoWrapper || pageInfoWrapper.getCode() != Wrapper.SUCCESS_CODE) {
            logger.error("调用csc接口查询货品出错! ");
            throw new BusinessException("调用csc接口查询货品出错");
        }
        return pageInfoWrapper.getResult();
    }

    private void checkGoodsApiDto(CscGoodsApiDTO cscGoodsApiDto) {
        logger.info("cscGoodsApiDto==>{}", cscGoodsApiDto);
        if (null == cscGoodsApiDto) {
            logger.error("调用csc接口查询货品入参不可为空! ");
            throw new BusinessException("调用csc接口查询货品入参不可为空!");
        }
        if (PubUtils.isSEmptyOrNull(cscGoodsApiDto.getCustomerCode())) {
            logger.error("客户编码不能为空! ");
            throw new BusinessException("客户编码不能为空");
        }
    }
}
