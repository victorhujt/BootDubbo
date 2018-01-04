package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.csc.model.dto.QueryWarehouseDto;
import com.xescm.csc.model.dto.warehouse.CscWarehouseDto;
import com.xescm.csc.provider.CscWarehouseEdasService;
import com.xescm.ofc.domain.OfcWarehouseInformation;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcWarehouseInformationMapper;
import com.xescm.ofc.model.dto.ofc.ModifyWarehouseDTO;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import com.xescm.rmc.edas.domain.dto.RmcWarehouseDto;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.uam.provider.UamGroupEdasService;
import com.xescm.whc.edas.dto.WareHouseDTO;
import com.xescm.whc.edas.dto.WhcDeliveryDTO;
import com.xescm.whc.edas.service.WhcOutOrderWareHouseMatchService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcWarehouseInformationServiceImpl extends BaseService<OfcWarehouseInformation> implements OfcWarehouseInformationService{
    @Resource
    private OfcWarehouseInformationMapper ofcWarehouseInformationMapper;
    @Resource
    private CscWarehouseEdasService cscWarehouseEdasService;
    @Resource
    private RmcWarehouseEdasService rmcWarehouseEdasService;

    @Resource
    private UamGroupEdasService uamGroupEdasService;
    @Resource
    private WhcOutOrderWareHouseMatchService whcOutOrderWareHouseMatchService;

    @Override
    public int deleteByOrderCode(Object key) {

        return ofcWarehouseInformationMapper.deleteByOrderCode(key);
    }

    @Override
    public int updateByOrderCode(Object key) {

        return ofcWarehouseInformationMapper.updateByOrderCode(key);
    }

    @Override
    public OfcWarehouseInformation warehouseInformationSelect(String orderCode) {
        OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationMapper.ofcWarehouseInformationSelect(orderCode);
        if(ofcWarehouseInformation == null){
            return new OfcWarehouseInformation();
        }
        return ofcWarehouseInformation;
    }


    @Override
    public List<RmcWarehouseRespDto> getWarehouseListByCustCode(String customerCode) {///1
        try{
            QueryWarehouseDto cscWarehouse = new QueryWarehouseDto();
            cscWarehouse.setCustomerCode(customerCode);
            logger.info("###################################当前的客户编码为："+customerCode);
            Wrapper<List<CscWarehouseDto>> cscWarehouseByCustomerId = cscWarehouseEdasService.getCscWarehouseByCustomerId(cscWarehouse);
            if(Wrapper.ERROR_CODE == cscWarehouseByCustomerId.getCode()){
                throw new BusinessException(cscWarehouseByCustomerId.getMessage());
            }
            List<CscWarehouseDto> result = cscWarehouseByCustomerId.getResult();
            logger.info("###############################查询该客户对应的仓库信息为："+result+"###########");
            if(null == result){
                return new ArrayList<>();
            }
            List<RmcWarehouseRespDto> warehouseList = new ArrayList<>();
            for(CscWarehouseDto cscWH : result){
                RmcWarehouseDto rmcWarehouse = new RmcWarehouseDto();
                rmcWarehouse.setWarehouseCode(cscWH.getWarehouseCode());
                RmcWarehouseRespDto rmcWarehouseResult;
                Wrapper<RmcWarehouseRespDto> rmcWarehouseByid =  rmcWarehouseEdasService.queryRmcWarehouseByCode(rmcWarehouse);
                if(Wrapper.ERROR_CODE == rmcWarehouseByid.getCode()){
                    continue;
                }
                rmcWarehouseResult = rmcWarehouseByid.getResult();
                if (null == rmcWarehouseResult) {
                    continue;
                }
                warehouseList.add(rmcWarehouseResult);
            }
            if(warehouseList.size() < 1){
                return new ArrayList<>();
            }
            return warehouseList;

        }catch (BusinessException ex){
            throw new BusinessException(ex.getMessage());
        }catch (Exception ex){
            throw new BusinessException("下单页面抓取仓库信息失败",ex);
        }

    }


    @Override
    public OfcWarehouseInformation queryByOrderCode(String orderCode) {
        logger.info("通过订单号查询订单仓储信息 orderCode ==> {}", orderCode);
        if (PubUtils.isSEmptyOrNull(orderCode)) {
            logger.error("通过订单号查询订单仓储信息, 入参为空");
            throw new BusinessException("查询订单仓储信息失败!");
        }
        OfcWarehouseInformation ofcWarehouseInformation = new OfcWarehouseInformation();
        ofcWarehouseInformation.setOrderCode(orderCode);
        List<OfcWarehouseInformation> select = ofcWarehouseInformationMapper.select(ofcWarehouseInformation);
        if (select.size() > 1) {
            logger.error("通过订单号查询订单仓储信息查询到多条");
            throw new BusinessException("查询订单仓储信息失败!");
        }
        if (CollectionUtils.isEmpty(select)) {
            logger.error("通过订单号查询不到订单仓储信息");
            return null;
        }
        return select.get(0);
    }

    /**
     * 客户查询已经开通的仓库
     * @param custCode
     * @return
     */
    @Override
    public List<RmcWarehouseRespDto> queryWarehouseByCustomerCode(String custCode) {
        List<RmcWarehouseRespDto> warehouses = new ArrayList<>();
        QueryWarehouseDto dto = new QueryWarehouseDto();
        dto.setCustomerCode(custCode);
        Wrapper<List<CscWarehouseDto>>  warehouse = cscWarehouseEdasService.getCscWarehouseByCustomerId(dto);
        if (warehouse.getCode() == Wrapper.SUCCESS_CODE) {
            //通过查询出的仓库编码查询出仓库的信息
            if (!PublicUtil.isEmpty(warehouse.getResult())) {
                RmcWarehouseDto rmcWarehouseDto = new RmcWarehouseDto();
                for (CscWarehouseDto cscWarehouseDto : warehouse.getResult()) {
                    rmcWarehouseDto.setWarehouseCode(cscWarehouseDto.getWarehouseCode());
                    Wrapper<RmcWarehouseRespDto> resp = rmcWarehouseEdasService.queryRmcWarehouseByCode(rmcWarehouseDto);
                    if (resp != null) {
                        if (resp.getCode() == Wrapper.SUCCESS_CODE && resp.getResult() != null) {
                            warehouses.add(resp.getResult());
                        }
                    }
                }
            } else {
                logger.info("客户没有开通仓库{}",warehouse.getMessage());
            }
        }
        return warehouses;
    }

    @Override
    public List<RmcWarehouseRespDto> queryWarehouseByBaseCode(ModifyWarehouseDTO modifyWarehouseDTO) {
            List<String> codes = new ArrayList<>();
            List<RmcWarehouseRespDto> rws = new ArrayList<>();
            String serialNo = modifyWarehouseDTO.getSerialNo();
            Wrapper<UamGroupDto> uamGroup =  uamGroupEdasService.queryByGroupSerialNo(serialNo);
            if (uamGroup != null) {
                logger.info("uamGroupEdasService.queryByGroupSerialNo response code is {}",uamGroup.getCode());
                if (uamGroup.getCode() == Wrapper.SUCCESS_CODE) {
                    if (uamGroup.getResult() != null) {
                        String code = uamGroup.getResult().getGroupCode();
                        if (PubUtils.isSEmptyOrNull(code)) {
                            throw new BusinessException("基地编码为空");
                        }
                        codes.add(code);
                        Wrapper<List<RmcWarehouseRespDto>>  warehouses =  rmcWarehouseEdasService.queryWareHouseInfoByBaseCode(codes);
                        logger.info("rmcWarehouseEdasService.queryWareHouseInfoByBaseCode response code is {}",warehouses.getCode());
                        if (warehouses.getCode() == Wrapper.SUCCESS_CODE) {
                            if (CollectionUtils.isNotEmpty(warehouses.getResult())) {
                                rws.addAll(warehouses.getResult());
                            }
                        }
                    } else {
                        logger.info("没有查询到基地编码，基地编码为:{}",serialNo);
                        throw new BusinessException("没有查询到基地编码！");
                    }
                }
            }
            return rws;
    }

    /**
     * 仓储单没有仓库进行仓库匹配
     */
    @Override
    public WareHouseDTO matchWareHouse(WhcDeliveryDTO deliveryDTO) {
        String orderCode = deliveryDTO.getOrderCode();
        logger.info("订单号{}开始匹配仓库:{}",orderCode);
        logger.info("匹配仓库的参数为:{}",deliveryDTO);
        Wrapper<WareHouseDTO> wareHouseResp = whcOutOrderWareHouseMatchService.matchWareHouse(deliveryDTO);
        logger.info("匹配仓库的响应为:{}",wareHouseResp);
        if (wareHouseResp.getCode() == Wrapper.SUCCESS_CODE && wareHouseResp.getResult() != null ) {
            return wareHouseResp.getResult();
        }
        return null;
    }
}
