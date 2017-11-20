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
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.dto.QueryCustomerNameAvgueDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.*;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.rmc.edas.domain.qo.RmcWareHouseQO;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import com.xescm.uam.provider.UamGroupEdasService;
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
import java.util.List;
import java.util.Map;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderConstConstant.TRACE_STATUS_5;
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
    private UamGroupEdasService uamGroupEdasService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;


    /**
     * 加载当前用户下的仓库信息
     * @return
     */
    @RequestMapping(value = "/loadWarehouseByUser",method = RequestMethod.POST)
    @ResponseBody
    public Object loadWarehouseByUser() {
        try {
            RmcWareHouseQO rmcWareHouseQO=new RmcWareHouseQO();
            Wrapper<List<RmcWarehouseRespDto>> warehouseResult=rmcWarehouseEdasService.queryWarehouseList(rmcWareHouseQO);
            if (warehouseResult.getCode() != Wrapper.SUCCESS_CODE) {
                logger.error("查询用户下的仓库产生异常{}",warehouseResult.getMessage());
                return WrapMapper.wrap(Wrapper.ERROR_CODE,warehouseResult.getMessage());
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", warehouseResult.getResult());
        } catch (Exception ex) {
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
    public Wrapper<?> copyOrder(@ApiParam(name = "orderCode",value = "订单号" ) @PathVariable String orderCode) {
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
    public Wrapper<?> queryRealGood(@ApiParam(name = "realGoodsDTO",value = "实收详情DTO")@RequestBody RealGoodsDTO realGoodsDTO) {
        Wrapper<?> response;
        try{
            if (realGoodsDTO == null) {
                throw new BusinessException("实收详情DTO不能为空！");
            }
            if (PublicUtil.isEmpty(realGoodsDTO.getOrderCode())) {
                throw new BusinessException("订单编号不能为空！");
            }
            if (PublicUtil.isEmpty(realGoodsDTO.getBusinessType())) {
                throw new BusinessException("业务类型不能为空！");
            }
            WmsDetailsDTO wmsDetailsDTO=new WmsDetailsDTO();
            wmsDetailsDTO.setOrderNo(realGoodsDTO.getOrderCode());
            wmsDetailsDTO.setBillType(realGoodsDTO.getBusinessType());
            response=whcOrderCancelEdasService.queryDetailsWmsByBillType(wmsDetailsDTO);
            if (response == null) {
                throw new BusinessException("查询实收实出货品明细出现异常");
            }
            if (response.getCode()!=Wrapper.SUCCESS_CODE) {
                throw new BusinessException(response.getMessage());
            }
        } catch(Exception ex) {
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
    public Object queryWarehouseByCustomerCode(@ApiParam(name = "customerCode",value = "客户编码" ) @PathVariable String customerCode) {
        try {
            if (PublicUtil.isEmpty(customerCode)) {
                throw new BusinessException("客户编码不可以为空！");
            }
            //客户编码查询出绑定的仓库编码
            List<RmcWarehouseRespDto>  warehouseRespDtoList =  ofcWarehouseInformationService.queryWarehouseByCustomerCode(customerCode);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", warehouseRespDtoList);
        }catch (BusinessException e){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception ex) {
            logger.error("客户编码查询绑定的仓库信息出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }

    }

    /**
     * 查询基地下的仓库
     * @param modifyWarehouseDTO
     */
    @RequestMapping(value = "/queryWarehouseBySerialNo",method = RequestMethod.POST)
    @ResponseBody
    public Object queryWarehouseBySerialNo(@ApiParam(name = "modifyWarehouseDTO",value = "修改仓库编码dto" ) @RequestBody ModifyWarehouseDTO modifyWarehouseDTO) {
        List<RmcWarehouseRespDto> rws;
        try{
            if (modifyWarehouseDTO == null) {
                throw new BusinessException("修改仓库DTO不能为空！");
            }
            if (PubUtils.isSEmptyOrNull(modifyWarehouseDTO.getCustCode())) {
                throw new BusinessException("客户编码不能为空！");
            }
            if (PubUtils.isSEmptyOrNull(modifyWarehouseDTO.getSerialNo())) {
                throw new BusinessException("基地编码不能为空！");
            }
            rws = ofcWarehouseInformationService.queryWarehouseByBaseCode(modifyWarehouseDTO);
        }catch (BusinessException e) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());

        }catch (Exception ex){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"内部异常");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", rws);
    }


    @RequestMapping(value = "/orderStorageDetails/{orderCode}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(
            notes = "仓单详情",
            httpMethod = "POST",
            value = "仓单详情"
    )
    public Object orderStorageDetails(@ApiParam(name = "orderCode",value = "订单号" ) @PathVariable  String orderCode) {
        Map result;
        try {
            if (StringUtils.isBlank(orderCode)) {
                throw new Exception("订单编号不能为空！");
            }
            result=ofcOrderManageService.orderStorageDetails(orderCode);
            if (result==null) {
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"没有查询到订单详情");
            }
        } catch (Exception e) {
            logger.error("查询订单详情出现异常:{}",e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,result);
    }

    @RequestMapping(value = "saveStorage/{tag}", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> saveStorage(@ApiParam(name = "ofcSaveStorageDTO", value = "仓单Dto") @RequestBody OfcSaveStorageDTO ofcSaveStorageDTO, @PathVariable String tag) {
        // 金融中心锁定客户不允许出库，追加逻辑
        String customerCode = ofcSaveStorageDTO.getFundamentalInformation().getCustCode();
        String orderBusinessType = ofcSaveStorageDTO.getFundamentalInformation().getBusinessType().substring(0, 2);
        // 当状态为出库 且 保存状态时
        if (orderBusinessType.equals(TRACE_STATUS_5) && "save".equals(tag)) {
            QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
            queryCustomerCodeDto.setCustomerCode(customerCode);
            Wrapper<CscCustomerVo> customerVoWrapper = cscCustomerEdasService.queryCustomerByCustomerCodeDto(queryCustomerCodeDto);
            if (Wrapper.ERROR_CODE == customerVoWrapper.getCode()) {
                throw new BusinessException("校验客户锁定状态时出现异常");
            }
            String customerStatus = customerVoWrapper.getResult().getCustomerStatus();
            if (!PubUtils.isSEmptyOrNull(customerStatus) && "1".equals(customerStatus)){
                throw new BusinessException("此客户被金融中心锁定，辛苦联系金融中心同事！");
            }
        }
        try {
            if (ofcSaveStorageDTO == null) {
                throw new BusinessException("订单的基本信息不能为空");
            }

            if (PubUtils.isSEmptyOrNull(tag)) {
                throw new BusinessException("下单标志不能为空");
            }

            if (!(ORDER_TAG_STOCK_SAVE.equals(tag) || ORDER_TAG_STOCK_EDIT.equals(tag) || ORDER_TAG_STOCK_IMPORT.equals(tag))) {
                throw new BusinessException("下单标志类型错误");
            }

            //货品信息
            List<OfcGoodsDetailsInfoDTO> ofcGoodsDetailsInfos = ofcSaveStorageDTO.getGoodsDetailsInfo();
            if (CollectionUtils.isEmpty(ofcGoodsDetailsInfos)) {
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
            CscSupplierInfoDto supplier = ofcSaveStorageDTO.getSupplier();
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            logger.info("==>仓储开单或编辑实体 OfcSaveStorageDTO={}", JacksonUtil.toJson(ofcSaveStorageDTO));
            // logger.info("==>仓储开单或编辑标志位 tag={}", tag);
            if (ofcWarehouseInformationDTO.getProvideTransport() == 1) {
                if (trimAndNullAsEmpty(OfcFundamentalInformationDTO.getBusinessType()).substring(0, 2).equals("61")) {
                    if (consignee == null) {
                        throw new BusinessException("需要提供运输时,配送基本信息收货方不能为空");
                    }
                } else if (trimAndNullAsEmpty(OfcFundamentalInformationDTO.getBusinessType()).substring(0, 2).equals("62")) {
                    if (consignor == null) {
                        throw new BusinessException("需要提供运输时,配送基本信息发货方不能为空");
                    }
                }
            }
            //发货方信息
            if (consignor == null) {
                consignor = new CscContantAndCompanyDto();
            }
            if (consignee == null) {
                consignee = new CscContantAndCompanyDto();
            }
            Wrapper<?> result = ofcOrderManageService.saveStorageOrder(ofcSaveStorageDTO, ofcGoodsDetailsInfos, tag, consignor, consignee, supplier, authResDtoByToken);
            if (result.getCode() != Wrapper.SUCCESS_CODE) {
                if (!org.apache.commons.lang.StringUtils.isEmpty(result.getMessage())) {
                    throw new BusinessException(result.getMessage());
                } else {
                    throw new BusinessException(Wrapper.ERROR_MESSAGE);
                }
            }
        } catch (BusinessException ex) {
            logger.error("仓储订单下单或编辑出现异常:{}", ex.getMessage(), ex);
            if (!org.apache.commons.lang.StringUtils.isEmpty(ex.getMessage())) {
                return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
            } else {
                return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logger.error("仓储订单下单或编辑出现未知异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "仓储下单成功");
    }

    /**
     * 仓储订单的审核与反审核  暂用该方法    如果改版后的方法兼容  该方法可去除
     * @param auditOrderDTOs
     * @return
     */
    @RequestMapping(value = "/auditOrderOrNotAuditOper", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(
            notes = "审核订单",
            httpMethod = "POST",
            value = "审核订单"
    )
    public Wrapper<?> auditOrderOrNotAuditOper(@ApiParam(name = "AuditOrderDTO",value = "审核订单的DTO") @RequestBody List<AuditOrderDTO> auditOrderDTOs) {
        try {
            AuthResDto authResDtoByToken = getAuthResDtoByToken();

            if (CollectionUtils.isEmpty(auditOrderDTOs)) {
                throw new Exception("审核DTO不能为空！");
            }
            int count = 0;
            for (AuditOrderDTO auditOrderDTO:auditOrderDTOs) {
                if (auditOrderDTO == null) {
                    throw new Exception("审核DTO不能为空！");
                }
                if (StringUtils.isBlank(auditOrderDTO.getOrderCode())) {
                    throw new Exception("订单编号不能为空！");
                }
                if (StringUtils.isBlank(auditOrderDTO.getReviewTag())) {
                    throw new Exception("订单标识不能为空！");
                }
                String result = ofcOrderManageService.auditStorageOrder(auditOrderDTO.getOrderCode(),auditOrderDTO.getReviewTag(), authResDtoByToken);
                if (!result.equals(String.valueOf(Wrapper.SUCCESS_CODE))) {
                    return WrapMapper.wrap(Wrapper.ERROR_CODE, "审核或反审核出现异常");
                } else {
                    count++;
                }
            }
            if (count > 0) {
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
            } else {
                return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
            }
        } catch (BusinessException ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单审核反审核出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
    }

    /**
     * 订单取消
     * @param orderCodes     订单编号
     * @return  Wrapper
     */
    @ApiOperation(
            notes = "取消订单",
            httpMethod = "POST",
            value = "取消订单"
    )
    @RequestMapping(value = "/orderCancelOper", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderCancelOper(@ApiParam(name = "orderCode",value = "订单号" ) @RequestBody List<String> orderCodes) {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        try {
            if (CollectionUtils.isEmpty(orderCodes)) {
                throw new Exception("订单编号不能为空！");
            }
            int count = 0;
            for (int i = 0; i<orderCodes.size();i++) {
                String orderCode = orderCodes.get(i);
                if (StringUtils.isBlank(orderCode)) {
                    continue;
                }
                String result = ofcOrderManageService.orderCancel(orderCode,authResDtoByToken);
                if (!PubUtils.isSEmptyOrNull(result) && "200".equals(result)) {
                    count++;
                }
            }
            if (count > 0) {
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
            } else {
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "订单删除失败");
            }
        } catch (BusinessException ex) {
            logger.error("订单中心订单管理订单取消出现异常,{}", "", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "订单取消出现异常");
        }
    }

    /**
     * 订单删除
     *
     * @param orderCodes     订单编号
     * @return  Wrapper
     */
    @RequestMapping(value = "/orderDeleteOper", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(
            notes = "删除订单",
            httpMethod = "POST",
            value = "删除订单"
    )
    public Wrapper<?> orderDeleteOper(@ApiParam(name = "orderCode",value = "订单号" ) @RequestBody List<String>  orderCodes) {
        try {
           if (CollectionUtils.isEmpty(orderCodes)) {
                throw new Exception("订单编号不能为空！");
            }
            int count = 0;
            for (int i = 0; i<orderCodes.size();i++) {
                String orderCode = orderCodes.get(i);
                if (StringUtils.isBlank(orderCode)) {
                    continue;
                }
                String result = ofcOrderManageService.orderDelete(orderCode);
                if (!PubUtils.isSEmptyOrNull(result) && "200".equals(result)) {
                    count++;
                }
            }
            if (count > 0) {
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
            } else {
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "订单删除失败");
            }
        } catch (BusinessException ex) {
            logger.error("订单中心订单管理订单删除出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心订单管理订单删除出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     * 根据客户名称分页查询客户
     *
     * @param customer 客户名称
     * @return
     */
    @RequestMapping(value = "/queryCustomerByName", method = RequestMethod.POST)
    @ResponseBody
    public Object queryCustomerByName(@RequestBody Page<String> customer) {
        logger.info("城配开单根据客户名称查询客户==> custName={}", customer.getParam());
        logger.info("城配开单根据客户名称查询客户==> pageNum={}", customer.getPageNum());
        logger.info("城配开单根据客户名称查询客户==> pageSize={}", customer.getPageSize());
        Wrapper<PageInfo<CscCustomerVo>> result;
        try {
            QueryCustomerNameAvgueDto queryParam = new QueryCustomerNameAvgueDto();
            queryParam.setCustomerName(customer.getParam());
            queryParam.setPageNum(customer.getPageNum());
            queryParam.setPageSize(customer.getPageSize());
            result = cscCustomerEdasService.queryCustomerByNameAvgue(queryParam);
            if (Wrapper.ERROR_CODE == result.getCode()) {
                logger.error("查询客户列表失败,查询结果有误!");
            }
        } catch (BusinessException ex) {
            logger.error("==>城配开单根据客户名称查询客户发生错误：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("==>城配开单根据客户名称查询客户发生异常：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "城配开单根据客户名称查询客户发生异常！");
        }
        return result;
    }
}
