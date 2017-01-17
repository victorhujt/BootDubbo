package com.xescm.ofc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.edas.model.dto.dpc.req.TranPlanOfcReqDTO;
import com.xescm.ofc.edas.model.dto.dpc.resp.TranPlanOfcRespDTO;
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
    public Wrapper<PageInfo<TranPlanOfcRespDTO>> tranPlanSel(TranPlanOfcReqDTO tranPlanOfcReqDTO) {
        PageInfo<TranPlanOfcRespDTO> tranPlanOfcRespDTOPageInfo=null;
        StringBuffer departurePlaceCode = new StringBuffer();
        StringBuffer destinationCode = new StringBuffer();
        if(!PubUtils.trimAndNullAsEmpty(tranPlanOfcReqDTO.getDepartureProvince()).equals("")){
            departurePlaceCode.append(tranPlanOfcReqDTO.getDepartureProvince());
            if(!PubUtils.trimAndNullAsEmpty(tranPlanOfcReqDTO.getDepartureCity()).equals("")){
                departurePlaceCode.append(",");
                departurePlaceCode.append(tranPlanOfcReqDTO.getDepartureCity());
            }
            if(!PubUtils.trimAndNullAsEmpty(tranPlanOfcReqDTO.getDepartureDistrict()).equals("")){
                departurePlaceCode.append(",");
                departurePlaceCode.append(tranPlanOfcReqDTO.getDepartureDistrict());
            }
            if(!PubUtils.trimAndNullAsEmpty(tranPlanOfcReqDTO.getDepartureTowns()).equals("")){
                departurePlaceCode.append(",");
                departurePlaceCode.append(tranPlanOfcReqDTO.getDepartureTowns());
            }
            tranPlanOfcReqDTO.setDeparturePlaceCode(departurePlaceCode.toString());
        }
        if(!PubUtils.trimAndNullAsEmpty(tranPlanOfcReqDTO.getDestinationProvince()).equals("")){
            destinationCode.append(tranPlanOfcReqDTO.getDestinationProvince());
            if(!PubUtils.trimAndNullAsEmpty(tranPlanOfcReqDTO.getDestinationCity()).equals("")){
                destinationCode.append(",");
                destinationCode.append(tranPlanOfcReqDTO.getDestinationCity());
            }
            if(!PubUtils.trimAndNullAsEmpty(tranPlanOfcReqDTO.getDestinationDistrict()).equals("")){
                destinationCode.append(",");
                destinationCode.append(tranPlanOfcReqDTO.getDestinationDistrict());
            }
            if(!PubUtils.trimAndNullAsEmpty(tranPlanOfcReqDTO.getDestinationTown()).equals("")){
                destinationCode.append(",");
                destinationCode.append(tranPlanOfcReqDTO.getDestinationTown());
            }
            tranPlanOfcReqDTO.setDestinationCode(destinationCode.toString());
        }
        List<TranPlanOfcRespDTO> tranPlanOfcRespDTOList=ofcTransplanInfoMapper.tranPlanSel(tranPlanOfcReqDTO);
        PageHelper.startPage(tranPlanOfcReqDTO.getPageNum() ,tranPlanOfcReqDTO.getPageSize());
        if(PubUtils.isNotNullAndBiggerSize(tranPlanOfcRespDTOList,0)){
            tranPlanOfcRespDTOPageInfo=new PageInfo<>(tranPlanOfcRespDTOList);
        }else{
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "暂时未查询到相关计划单信息");
        }
        if(tranPlanOfcRespDTOPageInfo==null){
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "查询相关任务单信息失败");
        }
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,tranPlanOfcRespDTOPageInfo);
    }
}
