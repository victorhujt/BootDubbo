package com.xescm.ofc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.edas.model.dto.dpc.req.TranPlanOfcReqDto;
import com.xescm.ofc.edas.model.dto.dpc.resp.TranPlanOfcRespDto;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcTransplanInfoMapper;
import com.xescm.ofc.model.vo.ofc.OfcTransplanInfoVo;
import com.xescm.ofc.service.OfcTransplanInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcTransplanInfoServiceImpl extends BaseService<OfcTransplanInfo> implements OfcTransplanInfoService {
    private static final Logger logger = LoggerFactory.getLogger(OfcTransplanInfoServiceImpl.class);
    @Autowired
    private OfcTransplanInfoMapper ofcTransplanInfoMapper;
    @Override
    public List<OfcTransplanInfo> ofcTransplanInfoScreenList(String orderCode) {
        if(!"".equals(PubUtils.trimAndNullAsEmpty(orderCode))){
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode",orderCode);
            return ofcTransplanInfoMapper.ofcTransplanInfoScreenList(mapperMap);
        }else {
            throw new BusinessException("运输计划单查询入参为空");
        }
    }

    @Override
    public List<OfcTransplanInfoVo> ofcTransplanInfoVoList(String planCode) {
        if(!"".equals(PubUtils.trimAndNullAsEmpty(planCode))){
            Map<String,String> mapperMap = new HashMap<>();
            mapperMap.put("planCode",planCode);
            return ofcTransplanInfoMapper.ofcTransplanInfoVoList(mapperMap);
        }else {
            throw new BusinessException("运输计划单查询入参为空");
        }
    }

    @Override
    public int queryNotInvalidAndNotCompleteTransOrder(String orderCode) {
        int result = ofcTransplanInfoMapper.queryNotInvalidAndNotCompleteTransOrder(orderCode);
        return result;
    }

    /**
     * 根据订单编号查询计划单编号列表
     * @param orderCode
     * @return
     */
    @Override
    public List<String> queryPlanCodesByOrderCode(String orderCode) {
        return ofcTransplanInfoMapper.queryTransCodeByOrderCode(orderCode);
    }

    /**
     * 根据订单编号查询未完成运输计划单编号列表
     * @param orderCode
     * @return
     */
    @Override
    public List<String> queryUncompletedPlanCodesByOrderCode(String orderCode) {
        return ofcTransplanInfoMapper.queryUncompletedPlanCodesByOrderCode(orderCode);
    }

    @Override
    public Wrapper<PageInfo<TranPlanOfcRespDto>> tranPlanSel(TranPlanOfcReqDto TranPlanOfcReqDto) {
        PageInfo<TranPlanOfcRespDto> TranPlanOfcRespDtoPageInfo=null;
        StringBuffer departurePlaceCode = new StringBuffer();
        StringBuffer destinationCode = new StringBuffer();
        if(!PubUtils.trimAndNullAsEmpty(TranPlanOfcReqDto.getDepartureProvince()).equals("")){
            departurePlaceCode.append(TranPlanOfcReqDto.getDepartureProvince());
            if(!PubUtils.trimAndNullAsEmpty(TranPlanOfcReqDto.getDepartureCity()).equals("")){
                departurePlaceCode.append(",");
                departurePlaceCode.append(TranPlanOfcReqDto.getDepartureCity());
            }
            if(!PubUtils.trimAndNullAsEmpty(TranPlanOfcReqDto.getDepartureDistrict()).equals("")){
                departurePlaceCode.append(",");
                departurePlaceCode.append(TranPlanOfcReqDto.getDepartureDistrict());
            }
            if(!PubUtils.trimAndNullAsEmpty(TranPlanOfcReqDto.getDepartureTowns()).equals("")){
                departurePlaceCode.append(",");
                departurePlaceCode.append(TranPlanOfcReqDto.getDepartureTowns());
            }
            TranPlanOfcReqDto.setDeparturePlaceCode(departurePlaceCode.toString());
        }
        if(!PubUtils.trimAndNullAsEmpty(TranPlanOfcReqDto.getDestinationProvince()).equals("")){
            destinationCode.append(TranPlanOfcReqDto.getDestinationProvince());
            if(!PubUtils.trimAndNullAsEmpty(TranPlanOfcReqDto.getDestinationCity()).equals("")){
                destinationCode.append(",");
                destinationCode.append(TranPlanOfcReqDto.getDestinationCity());
            }
            if(!PubUtils.trimAndNullAsEmpty(TranPlanOfcReqDto.getDestinationDistrict()).equals("")){
                destinationCode.append(",");
                destinationCode.append(TranPlanOfcReqDto.getDestinationDistrict());
            }
            if(!PubUtils.trimAndNullAsEmpty(TranPlanOfcReqDto.getDestinationTown()).equals("")){
                destinationCode.append(",");
                destinationCode.append(TranPlanOfcReqDto.getDestinationTown());
            }
            TranPlanOfcReqDto.setDestinationCode(destinationCode.toString());
        }
        List<TranPlanOfcRespDto> TranPlanOfcRespDtoList=ofcTransplanInfoMapper.tranPlanSel(TranPlanOfcReqDto);
        PageHelper.startPage(TranPlanOfcReqDto.getPageNum() ,TranPlanOfcReqDto.getPageSize());
        if(PubUtils.isNotNullAndBiggerSize(TranPlanOfcRespDtoList,0)){
            TranPlanOfcRespDtoPageInfo=new PageInfo<>(TranPlanOfcRespDtoList);
        }else{
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "暂时未查询到相关计划单信息");
        }
        if(TranPlanOfcRespDtoPageInfo==null){
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "查询相关任务单信息失败");
        }
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,TranPlanOfcRespDtoPageInfo);
    }
}
