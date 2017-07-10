package com.xescm.ofc.web.restcontroller.operationWorkbench.storageOrder;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.csc.model.dto.QueryWarehouseDto;
import com.xescm.csc.model.dto.warehouse.CscWarehouseDto;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.csc.provider.CscWarehouseEdasService;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcStorageDTO;
import com.xescm.ofc.model.dto.ofc.RealGoodsDTO;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.rmc.edas.domain.dto.RmcWarehouseDto;
import com.xescm.rmc.edas.domain.qo.RmcWareHouseQO;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.whc.edas.dto.WmsDetailsDTO;
import com.xescm.whc.edas.service.WhcOrderCancelEdasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hujintao on 2017/7/5.
 */
@Controller
@RequestMapping(
        value = {"/page/ofc/storageInfo"},
        produces = {"application/json;charset=UTF-8"}
)
@Api(
        value = "StorageInfoController",
        tags = {"仓单"},
        description = "仓储订单接口",
        produces = "application/json;charset=UTF-8"
)
public class OfcStorageInfoController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(OfcStorageInfoController.class);

    @Resource
    private RmcWarehouseEdasService rmcWarehouseEdasService;

    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;

    @Resource
    private CscCustomerEdasService cscCustomerEdasService;

    @Resource
    private OfcOrderManageService ofcOrderManageService;

    @Resource
    private WhcOrderCancelEdasService whcOrderCancelEdasService;

    @Resource
    private CscWarehouseEdasService cscWarehouseEdasService;


    /**
     * 加载当前用户下的仓库信息
     * @return
     */
    @RequestMapping(value = "/loadWarehouseByUser",method = RequestMethod.POST)
    @ResponseBody
    public Object loadWarehouseByUser(){
        try {
            RmcWareHouseQO rmcWareHouseQO=new RmcWareHouseQO();
            Wrapper<List<RmcWarehouseRespDto>> warehouseResult=rmcWarehouseEdasService.queryWarehouseList(rmcWareHouseQO);
            if(warehouseResult.getCode()!=warehouseResult.SUCCESS_CODE){
                logger.error("查询用户下的仓库产生异常{}",warehouseResult.getMessage());
                return WrapMapper.wrap(Wrapper.ERROR_CODE,warehouseResult.getMessage());
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", warehouseResult.getResult());
        }catch (Exception ex) {
            logger.error("查询用户下的仓库产生异常{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
    }

    @RequestMapping(value = "/loadAreaAndBaseByUser",method = RequestMethod.POST)
    @ResponseBody
    public Object loadAreaAndBaseByUser() {
        Map<String, List<OfcGroupVo>> groupMap;
        try {
            groupMap = ofcOrderManageOperService.loadGroupList();
            if (groupMap==null) {
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"没有查询到大区和基地信息");
            }
        } catch (Exception ex) {
            logger.error("查询用户下的大区和基地信息异常{}", ex.getMessage(), ex);

            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功",groupMap);
    }

    /**
     * 查询订单
     *
     * @param ofcStorageDTO  分页
     * @param
     * @return Object
     */
    @RequestMapping(
            value = {"/queryOrderStorageDataOper"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    @ApiOperation(
            notes = "查询仓储订单",
            httpMethod = "POST",
            value = "查询仓储订单"
    )
    public Object queryOrderStorageDataOper(@ApiParam(name = "ofcStorageDTO",value = "仓单Dto") @RequestBody Page<OfcStorageDTO> ofcStorageDTO) {
        try {
            PageHelper.startPage(ofcStorageDTO.getPageNum(), ofcStorageDTO.getPageSize());
            AuthResDto authResDto = getAuthResDtoByToken();
            List<OrderSearchOperResult> dataList = ofcOrderManageOperService.queryOrderStorageDataOper(authResDto, ofcStorageDTO.getParam());
            PageInfo<OrderSearchOperResult> pageInfo = new PageInfo<>(dataList);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException ex) {
            logger.error("运营平台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("运营平台查询订单出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }


    /**
     * 根据所选大区查询基地
     */
    @RequestMapping(
            value = {"/queryBaseListByArea/{areaCode}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    @ApiOperation(
            notes = "根据大区编号查询基地",
            httpMethod = "POST",
            value = "根据大区编号查询基地"
    )
    public Wrapper<?> queryBaseListByArea(@ApiParam(name = "areaCode",value = "大区编码") @PathVariable String areaCode) {
        logger.info("运营中心订单管理根据所选大区查询基地,入参:areaCode = {}", areaCode);
        if (PubUtils.isSEmptyOrNull(areaCode)) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "该大区编码为空!无法查询其基地!");
        }
        UamGroupDto uamGroupDto = new UamGroupDto();
        uamGroupDto.setSerialNo(areaCode);
        List<OfcGroupVo> ofcGroupVoList;
        try {
            ofcGroupVoList = ofcOrderManageOperService.getBaseListByCurArea(uamGroupDto);
        } catch (BusinessException ex) {
            logger.info("根据所选大区查询基地出错：{", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.info("根据所选大区查询基地出错：{", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "根据所选大区查询基地查询成功", ofcGroupVoList);
    }

    /**
     * 订单的复制
     * @param orderCode
     * @return
     */


    @RequestMapping(
            value = {"/copyOrderOper/{orderCode}"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    @ApiOperation(
            notes = "复制订单",
            httpMethod = "POST",
            value = "复制订单"
    )
    public Wrapper<?> copyOrder(@ApiParam(name = "orderCode",value = "订单号" ) @PathVariable String orderCode){
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        String result;
        try{
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
            logger.info("被复制的订单号为:{}",orderCode);
            result = ofcOrderManageService.copyOrder(orderCode,authResDtoByToken);
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单复制出现异常orderCode：{},{}", "", orderCode,ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,result);
    }

    /**
     *
     * @param realGoodsDTO
     *
     */
    @RequestMapping(value ="/queryRealGood",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(
            notes = "实收详情",
            httpMethod = "POST",
            value = "实收详情"
    )
    public Wrapper<?> queryRealGood(@ApiParam(name = "realGoodsDTO",value = "实收详情DTO")@RequestBody RealGoodsDTO realGoodsDTO){
        Wrapper<?> response;
        try{
            if(realGoodsDTO == null){
                throw new BusinessException("实收详情DTO不能为空！");
            }
            if(PublicUtil.isEmpty(realGoodsDTO.getOrderCode())){
                throw new BusinessException("订单编号不能为空！");
            }
            if(PublicUtil.isEmpty(realGoodsDTO.getBusinessType())){
                throw new BusinessException("业务类型不能为空！");
            }
            WmsDetailsDTO wmsDetailsDTO=new WmsDetailsDTO();
            wmsDetailsDTO.setOrderNo(realGoodsDTO.getOrderCode());
            wmsDetailsDTO.setBillType(realGoodsDTO.getBusinessType());
            response=whcOrderCancelEdasService.queryDetailsWmsByBillType(wmsDetailsDTO);
            if(response==null){
                throw new BusinessException("查询实收实出货品明细出现异常");
            }
            if(response.getCode()!=Wrapper.SUCCESS_CODE){
                throw new BusinessException(response.getMessage());
            }
        }catch(Exception ex){
            logger.error("查询实收实出货品明细出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, response.getResult());
    }

    /**
     * 客户编码查询客户下的仓库信息
     * @param customerCode 客户编码
     * @return
     */
    @RequestMapping(value = "/queryWarehouseByCustomerCode/{customerCode}",method = RequestMethod.POST)
    @ResponseBody
    public Object queryWarehouseByCustomerCode(@ApiParam(name = "customerCode",value = "客户编码" ) @PathVariable String customerCode){
        try {
            if(PublicUtil.isEmpty(customerCode)){
                throw new BusinessException("客户编码不可以为空！");
            }
            //客户编码查询出绑定的仓库编码
            List<RmcWarehouseRespDto> warehouseRespDtoList=new ArrayList<>();
            QueryWarehouseDto dto=new QueryWarehouseDto();
            dto.setCustomerCode(customerCode);
            Wrapper<List<CscWarehouseDto>>  warehouse=cscWarehouseEdasService.getCscWarehouseByCustomerId(dto);
            if(warehouse.getCode()==warehouse.SUCCESS_CODE){
                //通过查询出的仓库编码查询出仓库的信息
                if(!PublicUtil.isEmpty(warehouse.getResult())){
                    RmcWarehouseDto rmcWarehouseDto=new RmcWarehouseDto();
                    for (CscWarehouseDto cscWarehouseDto : warehouse.getResult()){
                        rmcWarehouseDto.setWarehouseCode(cscWarehouseDto.getWarehouseCode());
                        Wrapper<RmcWarehouseRespDto> resp=rmcWarehouseEdasService.queryRmcWarehouseByCode(rmcWarehouseDto);
                        if(resp.getCode()==Wrapper.SUCCESS_CODE){
                            warehouseRespDtoList.add(resp.getResult());
                        }else{
                            logger.error("通过仓库编码查询仓库信息产生异常{},仓库编码为{}",resp.getMessage(),rmcWarehouseDto.getWarehouseCode());
                        }
                    }
                }else{
                    logger.info("客户没有开通仓库{}",warehouse.getMessage());
                }
            }else{
                logger.error("通过客户编码查询客户绑定的仓库编码产生异常{}",warehouse.getMessage());
                return WrapMapper.wrap(Wrapper.ERROR_CODE,warehouse.getMessage());
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", warehouseRespDtoList);
        }catch (Exception ex) {
            logger.error("客户编码查询绑定的仓库信息出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }

    }

    @RequestMapping(value = "/orderStorageDetails/{orderCode}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(
            notes = "仓单详情",
            httpMethod = "POST",
            value = "仓单详情"
    )
    public Object orderStorageDetails(@ApiParam(name = "orderCode",value = "订单号" ) @PathVariable  String orderCode){
        Map result=null;
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
            result=ofcOrderManageService.orderStorageDetails(orderCode);
            if(result==null){
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"没有查询到订单详情");
            }
        }catch (Exception e){
            logger.error("查询订单详情出现异常:{}",e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,result);
    }


}
