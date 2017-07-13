package com.xescm.ofc.web.restcontroller.operationWorkbench.storageOrder;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.QueryWarehouseDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.warehouse.CscWarehouseDto;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.csc.provider.CscWarehouseEdasService;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.*;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.rmc.edas.domain.dto.RmcWarehouseDto;
import com.xescm.rmc.edas.domain.qo.RmcWareHouseQO;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import com.xescm.whc.edas.dto.WmsDetailsDTO;
import com.xescm.whc.edas.service.WhcOrderCancelEdasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.*;

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
    public Object queryOrderStorageDataOper(@ApiParam(name = "ofcStorageDTO",value = "仓单Dto") @RequestBody Page<OfcQueryStorageDTO> ofcStorageDTO) {
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

    @RequestMapping(value ="saveStorage/{tag}", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> saveStorage(@ApiParam(name = "ofcSaveStorageDTO",value = "仓单Dto") @RequestBody OfcSaveStorageDTO ofcSaveStorageDTO,  @PathVariable String tag) {
        try {
            if (ofcSaveStorageDTO == null) {
                throw new BusinessException("订单的基本信息不能为空");
            }

            if(PubUtils.isSEmptyOrNull(tag)){
                throw new BusinessException("下单标志不能为空");
            }

            if(!(ORDER_TAG_STOCK_SAVE.equals(tag) || ORDER_TAG_STOCK_EDIT.equals(tag) || ORDER_TAG_STOCK_IMPORT.equals(tag))){
                throw new BusinessException("下单标志类型错误");
            }

            //货品信息
            List<OfcGoodsDetailsInfoDTO> ofcGoodsDetailsInfos = ofcSaveStorageDTO.getGoodsDetailsInfo();
            if(CollectionUtils.isEmpty(ofcGoodsDetailsInfos)){
                throw new BusinessException("仓储下单时货品信息不能为空");
            }
            //订单基本信息
            OfcFundamentalInformationDTO OfcFundamentalInformationDTO = ofcSaveStorageDTO.getFundamentalInformation();
            //仓储信息
            OfcWarehouseInformationDTO ofcWarehouseInformationDTO = ofcSaveStorageDTO.getWarehouseInformation();
            //发货人信息
            CscContantAndCompanyDto consignor = ofcSaveStorageDTO.getConsignor();
            //收货人信息
            CscContantAndCompanyDto consignee = ofcSaveStorageDTO.getConsignee();
            //供应商信息
            CscSupplierInfoDto supplier=ofcSaveStorageDTO.getSupplier();
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            logger.info("==>仓储开单或编辑实体 OfcSaveStorageDTO={}", JacksonUtil.toJson(ofcSaveStorageDTO));
           // logger.info("==>仓储开单或编辑标志位 tag={}", tag);
            if(ofcWarehouseInformationDTO.getProvideTransport() == 1) {
                if (trimAndNullAsEmpty(OfcFundamentalInformationDTO.getBusinessType()).substring(0, 2).equals("61")) {
                    if(consignee == null) {
                        throw new BusinessException("需要提供运输时,配送基本信息收货方不能为空");
                    }
                } else if (trimAndNullAsEmpty(OfcFundamentalInformationDTO.getBusinessType()).substring(0, 2).equals("62")) {
                    if (consignor == null) {
                        throw new BusinessException("需要提供运输时,配送基本信息发货方不能为空");
                    }
                }
                // 带运输仓储单默认签收回单，费用为0
                OfcFinanceInformationDTO ofcFinanceInformationDTO = new OfcFinanceInformationDTO();
                ofcFinanceInformationDTO.setReturnList("1");
                ofcFinanceInformationDTO.setReturnListFee(new BigDecimal(0));
            }
            //发货方信息
            if(consignor == null){
                consignor=new CscContantAndCompanyDto();
            }
            if (consignee == null) {
                consignee=new CscContantAndCompanyDto();
            }
            Wrapper<?> result=ofcOrderManageService.saveStorageOrder(ofcSaveStorageDTO,ofcGoodsDetailsInfos,tag,consignor,consignee,supplier,authResDtoByToken);
            if(result.getCode()!=Wrapper.SUCCESS_CODE){
                if(!org.apache.commons.lang.StringUtils.isEmpty(result.getMessage())){
                    throw new BusinessException(result.getMessage());
                }else{
                    throw new BusinessException(Wrapper.ERROR_MESSAGE);
                }
            }
        } catch (BusinessException ex){
            logger.error("仓储订单下单或编辑出现异常:{}", ex.getMessage(), ex);
            if(!org.apache.commons.lang.StringUtils.isEmpty(ex.getMessage())){
                return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
            }else{
                return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logger.error("仓储订单下单或编辑出现未知异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"仓储下单成功");
    }
}
