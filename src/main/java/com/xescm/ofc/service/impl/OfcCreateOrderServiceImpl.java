package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.dto.QueryStoreDto;
import com.xescm.csc.model.dto.QueryWarehouseDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.dto.edas.company.CscQueryStoreCodeReqDto;
import com.xescm.csc.model.dto.packing.GoodsPackingDto;
import com.xescm.csc.model.dto.warehouse.CscWarehouseDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.model.vo.CscStorevo;
import com.xescm.csc.provider.*;
import com.xescm.epc.edas.service.EpcBaiDuEdasService;
import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.constant.OrderConstant;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.edas.model.dto.ofc.OfcCreateOrderDTO;
import com.xescm.ofc.edas.model.dto.ofc.OfcCreateOrderGoodsInfoDTO;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcAddressReflectMapper;
import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.model.dto.coo.CreateOrderTrans;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.rmc.edas.domain.vo.RmcAddressCodeVo;
import com.xescm.rmc.edas.domain.vo.RmcAddressNameVo;
import com.xescm.rmc.edas.service.RmcAddressEdasService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xescm.ofc.constant.GenCodePreffixConstant.PAAS_LINE_NO;
import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.TRANSPORT_ORDER;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.REVIEW;

@Service
public class OfcCreateOrderServiceImpl implements OfcCreateOrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private CscCustomerEdasService cscCustomerEdasService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private OfcAddressReflectService ofcAddressReflectService;
    @Resource
    private CscWarehouseEdasService cscWarehouseEdasService;
    @Resource
    private CscStoreEdasService cscStoreEdasService;
    @Resource
    private CscGoodsEdasService cscGoodsEdasService;
    @Resource
    private RmcAddressEdasService rmcAddressEdasService;
    @Resource
    private EpcBaiDuEdasService epcBaiDuEdasService;
    @Resource
    private OfcCreateOrderMapper createOrdersMapper;
    @Resource
    private OfcAddressReflectMapper ofcAddressReflectMapper;
    @Resource
    private CscContactCompanyEdasService cscContactCompanyEdasService;
    @Resource
    private DefaultMqProducer mqProducer;
    @Resource
    private MqConfig mqConfig;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private CscContactEdasService cscContactEdasService;

    @Resource
    private CodeGenUtils codeGenUtils;
    @Override
    public int queryCountByOrderStatus(String orderCode, String orderStatus) {
        return createOrdersMapper.queryCountByOrderStatus(orderCode, orderStatus);
    }

    @Transactional
    public ResultModel ofcCreateOrder(OfcCreateOrderDTO createOrderEntity, String orderCode) throws BusinessException {
        ResultModel resultModel;
        try {
            //校验订单日期
            String orderTime = createOrderEntity.getOrderTime();
            resultModel = CheckUtils.checkOrderTime(orderTime);
            if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                logger.error("校验订单日期【{}】失败：错误信息:{}, {}", orderTime, resultModel.getCode(), resultModel.getDesc());
                return resultModel;
            }

            //校验数据：货主编码 对应客户中心的custId
            String custCode = createOrderEntity.getCustCode();

            //校验货主编码
            resultModel = CheckUtils.checkCustCode(custCode);
            if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                logger.error("校验数据{}失败：{}", "货主编码", resultModel.getCode());
                return resultModel;
            }

            String orderBusinessType = createOrderEntity.getBusinessType().substring(0, 2);
            QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
            queryCustomerCodeDto.setCustomerCode(custCode);
            Wrapper<CscCustomerVo> customerVoWrapper = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
            if (customerVoWrapper.getResult() == null) {
                logger.error("获取货主信息失败：custId:{}，{}", custCode, customerVoWrapper.getMessage());
                return new ResultModel(ResultModel.ResultEnum.CODE_0009);
            } else if (orderBusinessType.equals(TRACE_STATUS_5)) {
                // 金融中心锁定客户不允许出库，追加逻辑
                String customerStatus = customerVoWrapper.getResult().getCustomerStatus();
                if (!PubUtils.isSEmptyOrNull(customerStatus) && "1".equals(customerStatus)){
                    return new ResultModel(ResultModel.ResultEnum.CODE_4001);
                }
                createOrderEntity.setCustName(customerVoWrapper.getResult().getCustomerName());
            } else  {
                createOrderEntity.setCustName(customerVoWrapper.getResult().getCustomerName());
            }

            //校验数据：订单类型
            String orderType = createOrderEntity.getOrderType();
            resultModel = CheckUtils.checkOrderType(orderType);
            if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                logger.error("校验数据{}失败：{},订单类型：{}", "订单类型", resultModel.getCode(), orderType);
                return resultModel;
            }
            //校验：业务类型
            String businessType = createOrderEntity.getBusinessType();
            resultModel = CheckUtils.checkBusinessType(orderType, businessType);
            if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                logger.error("校验数据{}失败：{}，订单类型,{},业务类型:{}", "业务类型", resultModel.getCode(), orderType, businessType);
                return resultModel;
            }

            //校验：店铺编码，获取该客户下的店铺编码
            String storeCode = null;
            //店铺名称
            String storeName = null;
            QueryStoreDto storeDto = new QueryStoreDto();
            storeDto.setCustomerCode(custCode);
            Wrapper<List<CscStorevo>> cscStoreVoList = cscStoreEdasService.getStoreByCustomerId(storeDto);
            if (!CollectionUtils.isEmpty(cscStoreVoList.getResult())) {
                logger.info("获取该客户下的店铺编码接口返回成功，custCode:{},接口返回值:{}", custCode, ToStringBuilder.reflectionToString(cscStoreVoList));
                CscStorevo cscStorevo = cscStoreVoList.getResult().get(0);
                storeCode = cscStorevo.getStoreCode();
                storeName = cscStorevo.getStoreName();
            }

            createOrderEntity.setStoreCode(storeCode);

            // 校验收发货方，如果收发货方编码不为空，则查询收发货方信息；否则校验传入收发货方字段
            resultModel = checkContactInfo(createOrderEntity);
            if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                logger.error("校验数据{}失败：{}", "发货方与收货方", resultModel.getCode());
                return resultModel;
            }

            //仓库编码
            String warehouseCode = createOrderEntity.getWarehouseCode();
            QueryWarehouseDto cscWarehouse = new QueryWarehouseDto();
            cscWarehouse.setCustomerCode(custCode);
            Wrapper<List<CscWarehouseDto>> cscWarehouseByCustomerId = cscWarehouseEdasService.getCscWarehouseByCustomerId(cscWarehouse);
            resultModel = CheckUtils.checkWarehouseCode(cscWarehouseByCustomerId, warehouseCode, orderType);
            if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                logger.error("校验数据{}失败：{}, 获取仓库编码接口返回:{}", "仓库编码", resultModel.getCode(), ToStringBuilder.reflectionToString(cscWarehouseByCustomerId));
                return resultModel;
            }

            //供应商
            //checkSupport(createOrderEntity, custCode);

            //校验：货品档案信息，校验货品明细

            if (OrderConstant.TRANSPORT_ORDER.equals(orderType)) {
                resultModel = checkGoodsDetailInfo(createOrderEntity, custCode, orderType);
                if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                    logger.error("校验订单商品信息失败：{}", resultModel.getDesc());
                    return resultModel;
                }
            }
            //转换 dto → do
            CreateOrderTrans createOrderTrans = new CreateOrderTrans(createOrderEntity, orderCode);
            OfcFundamentalInformation ofcFundamentalInformation = createOrderTrans.getOfcFundamentalInformation();
            ofcFundamentalInformation.setStoreName(storeName);
            OfcDistributionBasicInfo ofcDistributionBasicInfo = createOrderTrans.getOfcDistributionBasicInfo();
            // 设置货品分类编码、名称(配送表字段)
            this.setOfcDistributionGoodsInfo(createOrderEntity, custCode, ofcDistributionBasicInfo);
            OfcFinanceInformation ofcFinanceInformation = createOrderTrans.getOfcFinanceInformation();
            OfcWarehouseInformation ofcWarehouseInformation = createOrderTrans.getOfcWarehouseInformation();
            OfcOrderStatus ofcOrderStatus = createOrderTrans.getOfcOrderStatus();
            validateGoodsPackage(createOrderEntity,ofcFundamentalInformation);
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = createOrderTrans.getOfcGoodsDetailsInfoList();
            //调用创建订单方法
            resultModel = this.createOrders(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
            if (StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                //操作成功
                logger.info("校验数据成功，执行创单操作成功；orderCode:{}", orderCode);
            }
        } catch (BusinessException e) {
            logger.error("创建订单发生异常：{}", e);
            throw e;
        } catch (Exception e) {
            logger.error("创建订单发生未知异常：{}", e);
            throw e;
        }
        return resultModel;
    }

    private void validateGoodsPackage(OfcCreateOrderDTO createOrderEntity, OfcFundamentalInformation ofcFundamentalInformation) {
        List<OfcCreateOrderGoodsInfoDTO> tempList = new ArrayList<>();
        String orderCode = ofcFundamentalInformation.getOrderCode();
        //没有匹配到包装的货品编码
        List<String> noPackageGoodsCodes = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        StringBuilder str1 = new StringBuilder();
        {
            String custCode = createOrderEntity.getCustCode();
            for (OfcCreateOrderGoodsInfoDTO goodsInfo :createOrderEntity.getCreateOrderGoodsInfos()) {
                if (WAREHOUSE_DIST_ORDER.equals(createOrderEntity.getOrderType())) {
                    boolean isHavePackage = false;
                    CscGoodsApiDto cscGoods = new CscGoodsApiDto();
                    String goodsCode = goodsInfo.getGoodsCode();
                    String unit = goodsInfo.getUnit();
                    //天津自动化仓 用天津仓包装校验
                    if ("000001".equals(createOrderEntity.getWarehouseCode())) {
                        cscGoods.setWarehouseCode("ck0024");
                    } else {
                        cscGoods.setWarehouseCode(createOrderEntity.getWarehouseCode());
                    }
                    cscGoods.setFromSys("WMS");
                    cscGoods.setGoodsCode(goodsInfo.getGoodsCode());
                    cscGoods.setCustomerCode(createOrderEntity.getCustCode());
                    cscGoods.setPNum(1);
                    cscGoods.setPSize(10);
                    try{
                        logger.info("匹配包装的参数为:{}", JacksonUtil.toJson(cscGoods));
                        logger.info("createOrderEntity:{}",JacksonUtil.toJson(createOrderEntity));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Wrapper<PageInfo<CscGoodsApiVo>> goodsRest = ofcGoodsDetailsInfoService.validateGoodsByCode(cscGoods);
                    try{
                        logger.info("匹配包装的响应结果为:{}",JacksonUtil.toJson(goodsRest));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (goodsRest != null && Wrapper.SUCCESS_CODE == goodsRest.getCode() && goodsRest.getResult() != null &&
                            PubUtils.isNotNullAndBiggerSize(goodsRest.getResult().getList(), 0)) {
                        CscGoodsApiVo cscGoodsApiVo = goodsRest.getResult().getList().get(0);
                        goodsInfo.setGoodsType(cscGoodsApiVo.getGoodsTypeParentName());
                        goodsInfo.setGoodsTypeCode(cscGoodsApiVo.getGoodsTypeId());
                        goodsInfo.setGoodsCategory(cscGoodsApiVo.getGoodsTypeName());
                        goodsInfo.setGoodsCategoryCode(cscGoodsApiVo.getGoodsType());
                        List<GoodsPackingDto>  packages = cscGoodsApiVo.getGoodsPackingDtoList();
                        List<GoodsPackingDto>  dcPackages = goodsInfo.getSkuPackageList();
                        if (!CollectionUtils.isEmpty(packages)) {
                            if (createOrderEntity.getBusinessType().indexOf("62") != -1 && !"000001".equals(createOrderEntity.getWarehouseCode())) {
                                boolean isExistPackage = false;
                                if (!CollectionUtils.isEmpty(dcPackages)) {
                                    logger.info("orderCode is {}",orderCode);
                                    logger.info("入库单货品编码:{}开始比对包装",goodsCode);
                                    for (GoodsPackingDto packingDto : dcPackages){
                                        isExistPackage = false;
                                        //入库比对包装
                                        for (GoodsPackingDto dcPackingDto : packages) {
                                            if (StringUtils.equals(packingDto.getLevelDescription(),dcPackingDto.getLevelDescription()) &&
                                                    packingDto.getLevelSpecification().doubleValue() == dcPackingDto.getLevelSpecification().doubleValue()) {
                                                isExistPackage = true;
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    logger.info("大成传入的包装信息为空 orderCode is {}",orderCode);
                                    goodsInfo.setRemark("大成传入的包装信息为空");
                                }
                                logger.info("orderCode is {}",orderCode);
                                logger.info("入库单货品编码比对包装结果为:{}",isExistPackage);
                                if (!isExistPackage && !CollectionUtils.isEmpty(dcPackages)) {
                                    goodsInfo.setRemark("货品编码比对包装结果有误");
                                    goodsInfo.setCustName(createOrderEntity.getCustName());
                                    goodsInfo.setCustCode(custCode);
                                    tempList.add(goodsInfo);
                                }
                            }
                            //出入库业务只匹配包装存不存在
                            logger.info("出入库单货品编码:{}开始匹配包装换算为主单位数量",goodsCode);
                            for (GoodsPackingDto packingDto : packages) {
                                if (StringUtils.equals(unit,packingDto.getLevelDescription())) {
                                    logger.info("orderCode is {}",orderCode);
                                    logger.info("goodsInfo.getUnit() is {}",goodsInfo.getUnit());
                                    logger.info("packingDto.getLevelDescription() is {}",packingDto.getLevelDescription());
                                    if (StringUtils.equals(goodsInfo.getUnit(),packingDto.getLevelDescription())) {
                                        BigDecimal ls = packingDto.getLevelSpecification();
                                        if (ls == null || ls.compareTo(new BigDecimal(0)) == 0) {
                                            break;
                                        }
                                        goodsInfo.setConversionRate(ls);
                                        goodsInfo.setPackageName(packingDto.getLevelDescription());
                                        goodsInfo.setPackageType(packingDto.getLevel());
                                        BigDecimal quantity = new BigDecimal(goodsInfo.getQuantity());
                                        BigDecimal pquantity = quantity.divide(packingDto.getLevelSpecification(),3,BigDecimal.ROUND_HALF_DOWN);//保留三位小数
                                        goodsInfo.setPrimaryQuantity(pquantity);
                                        isHavePackage = true;
                                        break;
                                    }
                                }
                            }
                            //没有匹配到包装直接返回错误
                            if (!isHavePackage) {
                                logger.info("orderCode is {}",orderCode);
                                logger.info("出库单货品编码:{}没有匹配到包装",goodsCode);
                                if (!noPackageGoodsCodes.contains(goodsCode)) {
                                    goodsInfo.setRemark("货品编码包装或规格比对有误");
                                    goodsInfo.setPackageName(unit);
                                    noPackageGoodsCodes.add(goodsCode);
                                }
                            }
                        } else {
                            logger.info("orderCode is {}",orderCode);
                            logger.info("货品编码:{}csc接口没有查询到包装信息",goodsCode);
                            //没有匹配到包装直接返回错误
                            if (!noPackageGoodsCodes.contains(goodsCode)) {
                                goodsInfo.setRemark("货品编码没有查询到包装信息");
                                goodsInfo.setPackageName(unit);
                                noPackageGoodsCodes.add(goodsCode);
                            }
                            //csc没有包装 但是大成接口过来的有包装
                            if (!CollectionUtils.isEmpty(goodsInfo.getSkuPackageList()) && !"000001".equals(createOrderEntity.getWarehouseCode())) {
                                goodsInfo.setRemark("货品编码没有查询到包装信息");
                                goodsInfo.setPackageName(unit);
                                goodsInfo.setCustName(createOrderEntity.getCustName());
                                goodsInfo.setCustCode(custCode);
                                tempList.add(goodsInfo);
                            }
                        }
                    } else {
                        logger.info("orderCode is {}",orderCode);
                        logger.info("货品编码:{}csc接口查询到包装信息异常",goodsCode);
                        goodsInfo.setRemark("货品编码查询包装信息异常");
                        goodsInfo.setPackageName(goodsInfo.getUnit());
                        // TODO 推送CSC待创建商品
                        goodsInfo.setCustName(createOrderEntity.getCustName());
                        goodsInfo.setCustCode(custCode);
                        tempList.add(goodsInfo);
                    }
                }
            }
        }

        {
            try {
                if (tempList.size() > 0) {
                    String msgStr = JacksonUtil.toJson(tempList);
                    mqProducer.sendMsg(msgStr,mqConfig.getOfc2CscGoodsTopic(),"","noGoods");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (noPackageGoodsCodes.size() > 0) {
                str.append("订单对应货品编码为");
                for (int i = 0; i < noPackageGoodsCodes.size(); i++) {
                    if (i == noPackageGoodsCodes.size() -1) {
                        str.append(noPackageGoodsCodes.get(i));
                    } else {
                        str.append(noPackageGoodsCodes.get(i)).append(",");
                    }
                }
                str.append("没有包装.");
                logger.info("订单号为:{}",ofcFundamentalInformation.getOrderCode());
                logger.info(str.toString());
            }
            if (tempList.size() > 0) {
                str1.append("订单对应货品编码为");
                for (int i = 0; i < tempList.size(); i++) {
                    OfcCreateOrderGoodsInfoDTO temp = tempList.get(i);
                    if (str1.toString().indexOf(temp.getGoodsCode()) != -1) {
                        continue;
                    }
                    if (i == tempList.size() -1) {
                        str1.append(tempList.get(i).getGoodsCode());
                    } else {
                        str1.append(tempList.get(i).getGoodsCode()).append(",");
                    }
                }
                str1.append("不存在.");
                logger.info("订单号为:{}",ofcFundamentalInformation.getOrderCode());
                logger.info(str1.toString());
            }
            String exceptionReason = str.append(str1.toString()).toString();
            if (!PubUtils.isSEmptyOrNull(exceptionReason)) {
                ofcFundamentalInformation.setIsException(ISEXCEPTION);
                ofcFundamentalInformation.setExceptionReason(exceptionReason);
            }
            //更新订单时  校验通过如果之前是异常订单 将异常订单变为正常订单
            OfcFundamentalInformation information = ofcFundamentalInformationService.selectByKey(orderCode);
            if (information != null) {
                if (!PubUtils.isSEmptyOrNull(information.getExceptionReason())) {
                    if (PubUtils.isSEmptyOrNull(exceptionReason)) {
                        ofcFundamentalInformation.setIsException("0");// 0 正常  1 异常
                        ofcFundamentalInformation.setExceptionReason("");//异常原因置为空
                    }
                }
            }
        }
    }




    /**
     * 校验货品编码
     * @param createOrderEntity
     * @param custCode
     * @param orderType
     * @return
     */
    private ResultModel checkGoodsDetailInfo(OfcCreateOrderDTO createOrderEntity, String custCode, String orderType) {
        ResultModel resultModel;
        List<OfcCreateOrderGoodsInfoDTO> createOrderGoodsInfos = createOrderEntity.getCreateOrderGoodsInfos();
        if (PubUtils.isNotNullAndBiggerSize(createOrderGoodsInfos, 0)) {
            for (OfcCreateOrderGoodsInfoDTO goodsInfo : createOrderGoodsInfos) {
                String goodsCode = goodsInfo.getGoodsCode();
                CscGoodsApiDto cscGoods = new CscGoodsApiDto();
                cscGoods.setCustomerCode(custCode);
                cscGoods.setGoodsCode(goodsCode);
                Wrapper<List<CscGoodsApiVo>> goodsRest = cscGoodsEdasService.queryCscGoodsList(cscGoods);
                if (Wrapper.SUCCESS_CODE == goodsRest.getCode()) {
                    for (CscGoodsApiVo goodsApiVo : goodsRest.getResult()) {
                        goodsInfo.setGoodsType(goodsApiVo.getGoodsTypeParentName());
                        goodsInfo.setGoodsTypeCode(goodsApiVo.getGoodsTypeId());
                        goodsInfo.setGoodsCategory(goodsApiVo.getGoodsTypeName());
                        goodsInfo.setGoodsCategoryCode(goodsApiVo.getGoodsType());
                    }
                }
//                if (OrderConstant.WAREHOUSE_DIST_ORDER.equals(orderType)) {
//                    resultModel = CheckUtils.checkGoodsInfo(goodsRest, goodsInfo);
//                    if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
//                        logger.error("校验数据：{}货品编码：{}失败：{}", "货品档案信息", goodsCode, resultModel.getCode());
//                        return resultModel;
//                    }
//                }
                //2017年3月29日 lyh 追加逻辑: 表头体积重量数量由表体货品决定
                this.fixOrderGoodsMsg(createOrderEntity, goodsInfo);
            }
        } else {
            return new ResultModel(ResultModel.ResultEnum.CODE_2000);
        }
        return new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }
    /**
     * 设置货品分类编码、名称
     * @param createOrderEntity
     * @param custCode
     * @param distributionBasicInfo
     */
    private void setOfcDistributionGoodsInfo(OfcCreateOrderDTO createOrderEntity, String custCode,
                                             OfcDistributionBasicInfo distributionBasicInfo) {
        List<OfcCreateOrderGoodsInfoDTO> orderGoodsInfos = createOrderEntity.getCreateOrderGoodsInfos();
        if (PubUtils.isNotNullAndBiggerSize(orderGoodsInfos, 0)) {
            // 货品大类编码、名称
            String goodsTypeCode = null, goodsTypeParentName = null;
            for (OfcCreateOrderGoodsInfoDTO goodsInfo : orderGoodsInfos) {
                String goodsCode = goodsInfo.getGoodsCode();
                CscGoodsApiDto cscGoods = new CscGoodsApiDto();
                cscGoods.setCustomerCode(custCode);
                cscGoods.setGoodsCode(goodsCode);
                Wrapper<List<CscGoodsApiVo>> goodsRest = cscGoodsEdasService.queryCscGoodsList(cscGoods);
                if (Wrapper.SUCCESS_CODE == goodsRest.getCode()) {
                    for (CscGoodsApiVo goodsApiVo : goodsRest.getResult()) {
                        // 货品大类ID
                        String goodsTypeId = goodsApiVo.getGoodsTypeId();
                        // 货品大类名称
                        String goodsTypeName = goodsApiVo.getGoodsTypeParentName();
                        // 设置 ofcDistributionBasicInfo 类别名称、编码
                      goodsTypeCode = PubUtils.isOEmptyOrNull(goodsTypeCode) && !PubUtils.isOEmptyOrNull(goodsTypeId) ? goodsTypeId : goodsTypeCode;
                      goodsTypeParentName = PubUtils.isOEmptyOrNull(goodsTypeParentName) && !PubUtils.isOEmptyOrNull(goodsTypeName) ? goodsTypeName : goodsTypeParentName;
                    }
                }
            }
            distributionBasicInfo.setGoodsType(goodsTypeCode);
            distributionBasicInfo.setGoodsTypeName(goodsTypeParentName);
        }
    }

    /**
     *  2017年3月29日 lyh 追加逻辑: 表头体积重量数量由表体货品决定
     * @param createOrderEntity 表头
     * @param createOrderGoodsInfo 货品
     */
    private void fixOrderGoodsMsg(OfcCreateOrderDTO createOrderEntity, OfcCreateOrderGoodsInfoDTO createOrderGoodsInfo) {
        logger.info("表头体积重量数量计算 == > 表头 createOrderEntity :{}", createOrderEntity);
        logger.info("表头体积重量数量计算 == > 货品 createOrderGoodsInfo :{}", createOrderGoodsInfo);
        //货品信息
        String quantityDetail = createOrderGoodsInfo.getQuantity();
        String cubageDetail = createOrderGoodsInfo.getCubage();
        String weightDetail = createOrderGoodsInfo.getWeight();
        if (!PubUtils.isSEmptyOrNull(quantityDetail)) {
            String quantityHead = createOrderEntity.getQuantity();
            if (PubUtils.isSEmptyOrNull(quantityHead)) {
                quantityHead = "0";
            }
            BigDecimal quan = new BigDecimal(quantityDetail);
            BigDecimal quantityResult = new BigDecimal(quantityHead);
            quantityResult = quantityResult.add(quan);
            createOrderEntity.setQuantity(quantityResult.toString());
        }
        if (!PubUtils.isSEmptyOrNull(weightDetail)) {
            String weightHead = createOrderEntity.getWeight();
            if (PubUtils.isSEmptyOrNull(weightHead)) {
                weightHead = "0";
            }
            BigDecimal weig = new BigDecimal(weightDetail);
            BigDecimal weightHeadResult = new BigDecimal(weightHead);
            weightHeadResult = weightHeadResult.add(weig);
            createOrderEntity.setWeight(weightHeadResult.toString());
        }
        if (!PubUtils.isSEmptyOrNull(cubageDetail)) {
            String cubageHead = createOrderEntity.getCubage();
            if (PubUtils.isSEmptyOrNull(cubageHead)) {
                cubageHead = "0";
            }
            BigDecimal cuba = new BigDecimal(cubageDetail);
            BigDecimal cubageHeadResult = new BigDecimal(cubageHead);
            cubageHeadResult = cubageHeadResult.add(cuba);
            createOrderEntity.setCubage(cubageHeadResult.toString());
        }
        logger.info("表头体积重量数量计算结束 == > 表头 quantity :{}", createOrderEntity.getQuantity() + " weight:{}" +
            createOrderEntity.getWeight() + " cubage:{}" + createOrderEntity.getCubage());
    }


    /**
     * 保存到数据库
     * 先根据客户订单编号与货主便编码查询是否已存在订单，
     * 如果存在，判断是否是待审核，如果是待审核进行更改
     * 否则不进行操作
     * 保存或更新订单后调用自动审核的方法
     *
     * @param ofcFundamentalInformation     订单基本信息
     * @param ofcDistributionBasicInfo      运输单基本信息
     * @param ofcFinanceInformation         费用明细
     * @param ofcWarehouseInformation       仓储信息
     * @param ofcGoodsDetailsInfoList       货品明细
     * @param ofcOrderStatus        订单状态
     * @return      ResultModel
     * @throws BusinessException    异常
     */
    @Transactional
    ResultModel createOrders(OfcFundamentalInformation ofcFundamentalInformation,
                             OfcDistributionBasicInfo ofcDistributionBasicInfo,
                             OfcFinanceInformation ofcFinanceInformation,
                             OfcWarehouseInformation ofcWarehouseInformation,
                             List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList, OfcOrderStatus ofcOrderStatus) throws BusinessException {
        //订单记录表只添加不修改
        //插入或更新订单中心基本信息
        String custOrderCode = ofcFundamentalInformation.getCustOrderCode();
        String custCode = ofcFundamentalInformation.getCustCode();
        //根据客户订单编号与货主代码查询是否已经存在订单
        OfcFundamentalInformation information = ofcFundamentalInformationService.queryOfcFundInfoByCustOrderCodeAndCustCode(custOrderCode, custCode);
        boolean sEmptyOrNull = this.checkAddressPass(ofcDistributionBasicInfo);
        this.fixOrEeAddrCode(ofcDistributionBasicInfo);
        if (information != null) {
            String orderCode = information.getOrderCode();
            OfcOrderStatus queryOrderStatus = ofcOrderStatusService.queryLastTimeOrderByOrderCode(orderCode);
            //订单已存在,获取订单的最新状态,只有待审核的才能更新
            if (queryOrderStatus != null && !StringUtils.equals(queryOrderStatus.getOrderStatus(), PENDING_AUDIT)) {
                logger.error("订单已经审核custOrderCode:{},custCode:{}", custOrderCode, custCode);
                return new ResultModel(ResultModel.ResultEnum.CODE_1001);
            }
            ofcFundamentalInformation.setOrderCode(orderCode);
            ofcDistributionBasicInfo.setOrderCode(orderCode);
            ofcWarehouseInformation.setOrderCode(orderCode);
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
            ofcDistributionBasicInfoService.update(ofcDistributionBasicInfo);
            ofcWarehouseInformationService.update(ofcWarehouseInformation);
            ofcFinanceInformationService.update(ofcFinanceInformation);
            ofcGoodsDetailsInfoService.deleteAllByOrderCode(orderCode);
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfoList) {
                ofcGoodsDetailsInfo.setOrderCode(orderCode);
                ofcGoodsDetailsInfo.setPaasLineNo(codeGenUtils.getPaasLineNo(PAAS_LINE_NO));
                ofcGoodsDetailsInfoService.fillGoodType(ofcGoodsDetailsInfo);
                ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            }
            ofcOrderManageService.fillAreaAndBase(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcWarehouseInformation);
            try {
                if (!StringUtils.equals(ofcFundamentalInformation.getIsException(),ISEXCEPTION)) {
                    //自动审核通过 review:审核；rereview:反审核
                    if (sEmptyOrNull) {
                        //自动审核通过 review:审核；rereview:反审核
                        this.orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
                    } else {
                        this.fixOrEeAddress(ofcDistributionBasicInfo);
                        //然后再更新运输信息
                        ofcDistributionBasicInfoService.update(ofcDistributionBasicInfo);
                        //如果能匹配成功, 就继续审核, 如果匹配不成功才是未审核
                        sEmptyOrNull = this.checkAddressPass(ofcDistributionBasicInfo);
                        if (sEmptyOrNull) {
                            this.orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
                        }
                    }
                    logger.info("订单基本信息:{}",ToStringBuilder.reflectionToString(ofcFundamentalInformation));
                    logger.info("订单基本信息:{}",ToStringBuilder.reflectionToString(ofcFundamentalInformation));

                }

            } catch (BusinessException ex) {
                logger.error("自动审核异常，{}", ex);
                throw new BusinessException("自动审核异常", ex);
            }
            return new ResultModel(ResultModel.ResultEnum.CODE_0000);
        } else {
            ofcFundamentalInformationService.save(ofcFundamentalInformation);
            if (sEmptyOrNull) {
                ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
            }
            ofcWarehouseInformationService.save(ofcWarehouseInformation);
            ofcFinanceInformationService.save(ofcFinanceInformation);
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfoList) {
                ofcGoodsDetailsInfo.setPaasLineNo(codeGenUtils.getPaasLineNo(PAAS_LINE_NO));
                ofcGoodsDetailsInfoService.fillGoodType(ofcGoodsDetailsInfo);
                ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            }
            ofcOrderStatusService.save(ofcOrderStatus);
            ofcOrderManageService.fillAreaAndBase(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcWarehouseInformation);
            try {
                if (!StringUtils.equals(ofcFundamentalInformation.getIsException(),ISEXCEPTION)) {
                    //地址编码不为空才走自动审核, 为空的状态还是待审核, 并调用EPC端口补齐
                    if (sEmptyOrNull) {
                        //自动审核通过 review:审核；rereview:反审核
                        this.orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
                    } else {
                        this.fixOrEeAddress(ofcDistributionBasicInfo);
                        //然后再保存运输信息
                        ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
                        //如果能匹配成功, 就继续审核, 如果匹配不成功才是未审核
                        sEmptyOrNull = this.checkAddressPass(ofcDistributionBasicInfo);
                        if (sEmptyOrNull) {
                            this.orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
                        }
                    }
                    logger.info("订单基本信息:{}",ToStringBuilder.reflectionToString(ofcFundamentalInformation));
                }
                //推结算
               // ofcOrderManageService.pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,ofcGoodsDetailsInfoList);
            } catch (BusinessException ex) {
                logger.error("自动审核异常，{}", ex);
                throw new BusinessException("自动审核异常", ex);
            }
            return new ResultModel(ResultModel.ResultEnum.CODE_0000);
        }
    }

    private boolean checkAddressPass(OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        String departurePlaceCode = ofcDistributionBasicInfo.getDeparturePlaceCode();
        String departureProvince = ofcDistributionBasicInfo.getDepartureProvince();
        String destinationProvince = ofcDistributionBasicInfo.getDestinationProvince();
        String departureCity = ofcDistributionBasicInfo.getDepartureCity();
        String destinationCity = ofcDistributionBasicInfo.getDestinationCity();
        return !PubUtils.isSEmptyOrNull(departurePlaceCode) && !PubUtils.isSEmptyOrNull(departureProvince)
                && !PubUtils.isSEmptyOrNull(departureCity) && !PubUtils.isSEmptyOrNull(destinationProvince) && !PubUtils.isSEmptyOrNull(destinationCity);
    }

    private void fixOrEeAddrCode(OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        String departuePlaceCode = this.explainAddressByRmc(ofcDistributionBasicInfo.getDepartureProvince()
                , ofcDistributionBasicInfo.getDepartureCity(), ofcDistributionBasicInfo.getDepartureDistrict());
        if (!PubUtils.isSEmptyOrNull(departuePlaceCode)) {
            ofcDistributionBasicInfo.setDeparturePlaceCode(departuePlaceCode);
        }
        String destinationCode = this.explainAddressByRmc(ofcDistributionBasicInfo.getDestinationProvince()
                , ofcDistributionBasicInfo.getDestinationCity(), ofcDistributionBasicInfo.getDestinationDistrict());
        if (!PubUtils.isSEmptyOrNull(destinationCode)) {
            ofcDistributionBasicInfo.setDestinationCode(destinationCode);
        }
    }

    /**
     * 调用EPC接口解析完整地址
     * @param ofcDistributionBasicInfo 运输信息
     */
    private void fixOrEeAddress(OfcDistributionBasicInfo ofcDistributionBasicInfo) {
        logger.info("调用EPC接口解析完整地址 ofcDistributionBasicInfo ==> {}", ofcDistributionBasicInfo);
        String departurePlace = ofcDistributionBasicInfo.getDeparturePlace();
        String destination = ofcDistributionBasicInfo.getDestination();
        //调用EPC接口进行解析
        if (!PubUtils.isSEmptyOrNull(departurePlace)) {
            OfcAddressReflect ofcAddressReflect = ofcAddressReflectService.selectByAddress(departurePlace);
            logger.info("查询本地映射结果: ofcAddressReflect {}", ofcAddressReflect);
            if (null != ofcAddressReflect && !PubUtils.isSEmptyOrNull(ofcAddressReflect.getProvince())
                    && !PubUtils.isSEmptyOrNull(ofcAddressReflect.getCity())) {
                logger.info("映射成功!");
                ofcAddressReflectService.reflectAddressToDis(ofcAddressReflect, ofcDistributionBasicInfo, "departure");
            } else {
                logger.info("映射失败!");
                logger.info("开始调用Epc接口进行解析!");
                String province = ofcDistributionBasicInfo.getDepartureProvince();
                String city = ofcDistributionBasicInfo.getDepartureCity();
                String district = ofcDistributionBasicInfo.getDepartureDistrict();
                String town = ofcDistributionBasicInfo.getDepartureTowns();
                province = PubUtils.isSEmptyOrNull(province) ? "" : province;
                city = PubUtils.isSEmptyOrNull(city) ? "" : city;
                district = PubUtils.isSEmptyOrNull(district) ? "" : district;
                town = PubUtils.isSEmptyOrNull(town) ? "" : town;
                String addr = province + city + district + town + departurePlace;
                Wrapper departurePlaceResult = epcBaiDuEdasService.showLocationStr(addr);
                logger.info("调用Epc接口进行解析结果: {}", departurePlaceResult);
                Object result = departurePlaceResult.getResult();
                boolean checkEpcAddrPass = this.checkEpcAddrPass(result);
                if (departurePlaceResult.getCode() == Wrapper.ERROR_CODE || !checkEpcAddrPass) {
                    logger.error("出发完整地址调用EPC接口解析完整地址失败! destinationResult :{}", departurePlaceResult);
                    if (null == ofcAddressReflect) {
                        ofcAddressReflect = new OfcAddressReflect();
                        ofcAddressReflect.setAddress(departurePlace);
                        if (ofcAddressReflectMapper.insert(ofcAddressReflect) < 1)
                            logger.error("存储出发完整地址映射失败!");
                    }
                } else {
                    com.alibaba.fastjson.JSONObject departurePlaceObj = JSON.parseObject((String) departurePlaceResult.getResult());
                    Object departureProvince = departurePlaceObj.get("province");
                    Object departureCity = departurePlaceObj.get("city");
                    Object departureDistrict = departurePlaceObj.get("district");
                    if (null != departureProvince) {
                        String depProvince = (String) departureProvince;
                        ofcDistributionBasicInfo.setDepartureProvince(depProvince);
                        if (null != departureCity) {
                            String depCity = (String) departureCity;
                            ofcDistributionBasicInfo.setDepartureCity(depCity);
                            if (null != departureDistrict) {
                                String depDistrict = (String) departureDistrict;
                                ofcDistributionBasicInfo.setDepartureDistrict(depDistrict);
                                //调用RMC接口, 查询省市区名称对应的编码, 并赋值
                                logger.info("调用RMC接口, 查询省市区名称对应的编码, 并赋值");
                                String departuePlaceCode = this.explainAddressByRmc(depProvince, depCity, depDistrict);
                                if (PubUtils.isSEmptyOrNull(departuePlaceCode)) {
                                    logger.error("调用RMC接口, 查询出发省市区名称对应的编码失败! ");
                                }
                                ofcDistributionBasicInfo.setDeparturePlaceCode(departuePlaceCode);
                                /*ofcAddressReflect = new OfcAddressReflect();
                                ofcAddressReflectService.reflectAddressToRef(ofcAddressReflect, ofcDistributionBasicInfo, "departure");
                                int insert = ofcAddressReflectMapper.insert(ofcAddressReflect);
                                if (insert < 1) {
                                    logger.error("存储明细地址映射失败!");
                                    throw new BusinessException("存储明细地址映射失败!");
                                }*/
                            }

                        }

                    }
                }
            }
        }

        if (!PubUtils.isSEmptyOrNull(destination)) {
            OfcAddressReflect ofcAddressReflect = ofcAddressReflectService.selectByAddress(destination);
            logger.info("查询本地映射结果: ofcAddressReflect {}", ofcAddressReflect);
            if (null != ofcAddressReflect && !PubUtils.isSEmptyOrNull(ofcAddressReflect.getProvince())
                    && !PubUtils.isSEmptyOrNull(ofcAddressReflect.getCity())) {
                logger.info("映射成功!");
                ofcAddressReflectService.reflectAddressToDis(ofcAddressReflect, ofcDistributionBasicInfo, "destination");
            } else {
                logger.info("映射失败!");
                logger.error("开始调用Epc接口进行解析! ");
                String province = ofcDistributionBasicInfo.getDestinationProvince();
                String city = ofcDistributionBasicInfo.getDestinationCity();
                String district = ofcDistributionBasicInfo.getDestinationDistrict();
                String town = ofcDistributionBasicInfo.getDestinationTowns();
                province = PubUtils.isSEmptyOrNull(province) ? "" : province;
                city = PubUtils.isSEmptyOrNull(city) ? "" : city;
                district = PubUtils.isSEmptyOrNull(district) ? "" : district;
                town = PubUtils.isSEmptyOrNull(town) ? "" : town;
                String addr = province + city + district + town + destination;
                Wrapper destinationResult = epcBaiDuEdasService.showLocationStr(addr);
                logger.info("调用Epc接口进行解析结果: {}", destinationResult);
                Object result = destinationResult.getResult();
                boolean checkEpcAddrPass = this.checkEpcAddrPass(result);
                if (destinationResult.getCode() == Wrapper.ERROR_CODE || !checkEpcAddrPass) {
                    logger.error("到达完整地址调用EPC接口解析完整地址失败! destinationResult :{}", destinationResult);
                    if (null == ofcAddressReflect) {
                        ofcAddressReflect = new OfcAddressReflect();
                        ofcAddressReflect.setAddress(destination);
                        if (ofcAddressReflectMapper.insert(ofcAddressReflect) < 1)
                            logger.error("存储到达完整地址映射失败!");
                    }
                } else {
                    com.alibaba.fastjson.JSONObject destinationObj = JSON.parseObject((String) destinationResult.getResult());
                    Object destinationProvince = destinationObj.get("province");
                    Object destinationCity = destinationObj.get("city");
                    Object destinationDistrict = destinationObj.get("district");
                    if (null != destinationProvince) {
                        String desProvince = (String) destinationProvince;
                        ofcDistributionBasicInfo.setDestinationProvince(desProvince);
                        if (null != destinationCity) {
                            String desCity = (String) destinationCity;
                            ofcDistributionBasicInfo.setDestinationCity(desCity);
                            if (null != destinationDistrict) {
                                String desDistrict = (String) destinationDistrict;
                                ofcDistributionBasicInfo.setDestinationDistrict(desDistrict);
                                //调用RMC接口, 查询省市区名称对应的编码, 并赋值
                                logger.info("调用RMC接口, 查询省市区名称对应的编码, 并赋值");
                                String destinationCode = this.explainAddressByRmc(desProvince, desCity, desDistrict);
                                if (PubUtils.isSEmptyOrNull(destinationCode)) {
                                    logger.error("调用RMC接口, 查询到达省市区名称对应的编码失败! ");
                                }
                                ofcDistributionBasicInfo.setDestinationCode(destinationCode);
                                /*ofcAddressReflect = new OfcAddressReflect();
                                ofcAddressReflectService.reflectAddressToRef(ofcAddressReflect, ofcDistributionBasicInfo, "destination");
                                int insert = ofcAddressReflectMapper.insert(ofcAddressReflect);
                                if (insert < 1) {
                                    logger.error("存储明细地址映射失败!");
                                    throw new BusinessException("存储明细地址映射失败!");
                                }*/
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * 校验Epc地址解析结果是否成功
     * @param result
     * @return
     */
    private boolean checkEpcAddrPass(Object result) {
        if (null == result || PubUtils.isSEmptyOrNull((String) result)) {
            logger.info("校验Epc地址解析结果失败! result为空");
            return false;
        }
        String addr = (String) result;
        logger.info("校验Epc地址解析结果是否成功 addr : {}", addr);
        com.alibaba.fastjson.JSONObject departurePlaceObj = JSON.parseObject(addr);
        Object province = departurePlaceObj.get("province");
        Object city = departurePlaceObj.get("city");
        Object district = departurePlaceObj.get("district");
        if (null == province || PubUtils.isSEmptyOrNull((String) province)
                || null == city || PubUtils.isSEmptyOrNull((String) city)
                || null == district || PubUtils.isSEmptyOrNull((String) district)) {
            logger.info("校验Epc地址解析结果失败!");
            return false;
        }
        logger.info("校验Epc地址解析结果成功!");
        return true;
    }


    /**
     * 调用RMC接口, 通过省市区名称取得对应编码
     * @param province 省名称
     * @param city 市名称
     * @param district 区名称
     */
    private String explainAddressByRmc(String province, String city, String district) {
        logger.info("调用RMC接口, 通过省市区名称取得对应编码 province ==> {}", province);
        logger.info("调用RMC接口, 通过省市区名称取得对应编码 city ==> {}", city);
        logger.info("调用RMC接口, 通过省市区名称取得对应编码 district ==> {}", district);
        RmcAddressNameVo rmcAddressNameVo = new RmcAddressNameVo();
        rmcAddressNameVo.setProvinceName(province);
        rmcAddressNameVo.setCityName(city);
        rmcAddressNameVo.setDistrictName(district);
        if (PubUtils.isSEmptyOrNull(province) && PubUtils.isSEmptyOrNull(city) && PubUtils.isSEmptyOrNull(district)) {
            logger.error("调用RMC接口, 通过省市区名称取得对应编码, 省市区名称三者必填!");
            return null;
//            throw new BusinessException("调用RMC接口, 通过省市区名称取得对应编码, 省市区名称三者必填!");
        }
        Wrapper<RmcAddressCodeVo> codeByName = rmcAddressEdasService.findCodeByName(rmcAddressNameVo);
        if (codeByName.getCode() == Wrapper.ERROR_CODE || codeByName.getResult() == null) {
            logger.error("调用RMC接口, 通过省市区名称取得对应编码,失败! 错误信息:{}", codeByName.getMessage());
            return null;
//            throw new BusinessException(codeByName.getMessage());
        }
        RmcAddressCodeVo rmcAddressCodeVo = codeByName.getResult();
        String provinceCode = rmcAddressCodeVo.getProvinceCode();
        String cityCode = rmcAddressCodeVo.getCityCode();
        String districtCode = rmcAddressCodeVo.getDistrictCode();
        if (!PubUtils.isSEmptyOrNull(provinceCode)) {
            StringBuilder sb = new StringBuilder(provinceCode);
            if (!PubUtils.isSEmptyOrNull(cityCode)) {
                sb.append(",").append(cityCode);
                if (!PubUtils.isSEmptyOrNull(districtCode)) {
                    sb.append(",").append(districtCode);
                    return sb.toString();
                }
            }
        }
        logger.info("================ 通过省市区名称取得对应编码: province ==> {}, city ==> {}, district ==> {}  返回结果：{}, {}, {}",
            province, city, district, provinceCode, cityCode, districtCode);
        return null;
    }

    /**
     * 自动审核
     * @param ofcFundamentalInformation     订单基本
     * @param ofcDistributionBasicInfo      运输
     * @param ofcFinanceInformation         费用明细
     * @param ofcWarehouseInformation       仓储信息
     * @param ofcGoodsDetailsInfoList       货品明细
     */
    private void orderApply(OfcFundamentalInformation ofcFundamentalInformation,
                           OfcDistributionBasicInfo ofcDistributionBasicInfo,
                           OfcFinanceInformation ofcFinanceInformation,
                           OfcWarehouseInformation ofcWarehouseInformation,
                           List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList, OfcOrderStatus ofcOrderStatus) {
        //自动审核通过 review:审核；rereview:反审核
        AuthResDto authResDto = new AuthResDto();
        authResDto.setGroupRefName(CREATE_ORDER_BYAPI);
        String auditResult = ofcOrderManageService.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetailsInfoList, ofcDistributionBasicInfo
                , ofcWarehouseInformation, ofcFinanceInformation, PENDING_AUDIT, REVIEW, authResDto);
        logger.info("订单基本信息:{}",ToStringBuilder.reflectionToString(ofcFundamentalInformation));
        logger.info("自动审核操作：" + auditResult);
    }

    /**
     * 根据省市区名称获取编码
     *
     * @param addressDto    地址实体
     * @return  Map
     */
    public Map<String, String> getAddressCode(RmcAddressNameVo addressDto) {
        if (StringUtils.isBlank(addressDto.getProvinceName())
                || StringUtils.isBlank(addressDto.getCityName())
                || StringUtils.isBlank(addressDto.getDistrictName())) {
            throw new BusinessException("省市区地址信息不全");
        }
        Map<String, String> resuteMap = new HashMap<>();
        Wrapper<?> wrapperResult = rmcAddressEdasService.findCodeByName(addressDto);
        String result = (String) wrapperResult.getResult();
        if (StringUtils.isBlank(result)) {
            throw new BusinessException("无法获取收货方省市区地址编码");
        }
        JSONObject jsonObject = JSONObject.fromObject(JSONObject.fromObject(result).getString("result"));
        String provinceCode = jsonObject.getString("provinceCode");
        String cityCode = jsonObject.getString("cityCode");
        String districtCode = jsonObject.getString("districtCode");
        resuteMap.put("provinceCode", provinceCode);
        resuteMap.put("cityCode", cityCode);
        resuteMap.put("districtCode", districtCode);
        return resuteMap;
    }

    /**
     * 根据省市区名称获取编码
     * 补历史订单临时使用
     * @param addressDto    地址实体
     * @return      Map
     */
    public Map<String, String> getAddressCodeTemp(RmcAddressNameVo addressDto) {
        if (StringUtils.isBlank(addressDto.getProvinceName())
                || StringUtils.isBlank(addressDto.getCityName()) || StringUtils.isBlank(addressDto.getDistrictName())) {
            logger.info("根据省市区名称获取编码(补历史订单),当前订单入参省或市或区名称为空");
            return null;
        }
        Map<String, String> resuteMap = new HashMap<>();
        Wrapper<RmcAddressCodeVo> wrapperResult = rmcAddressEdasService.findCodeByName(addressDto);
        RmcAddressCodeVo result = wrapperResult.getResult();
        if (PubUtils.isNull(result)) {
            logger.info("根据省市区名称获取编码(补历史订单),当前订单的省市区找不到对应编码");
            return null;
        }
        String provinceCode = result.getProvinceCode();
        String cityCode = result.getCityCode();
        String districtCode = result.getDistrictCode();
        resuteMap.put("provinceCode", provinceCode);
        resuteMap.put("cityCode", cityCode);
        resuteMap.put("districtCode", districtCode);
        return resuteMap;
    }


    /**
     * 校验收发货方信息
     * @param createOrderEntity
     * @return
     */
    public ResultModel checkContactInfo(OfcCreateOrderDTO createOrderEntity) {
        ResultModel resultModel;
        ResultModel restConsignor = checkConsignorInfo(createOrderEntity);
        if (ResultModel.ResultEnum.CODE_0000.getCode().equals(restConsignor.getCode())) {
            ResultModel restConsignee = checkConsigneeInfo(createOrderEntity);
            if (ResultModel.ResultEnum.CODE_0000.getCode().equals(restConsignee.getCode())) {
                resultModel = new ResultModel(ResultModel.ResultEnum.CODE_0000);
            } else {
                resultModel = restConsignee;
            }
        } else {
            resultModel = restConsignor;
        }
        return resultModel;
    }

    // 发货方校验
    public ResultModel checkConsignorInfo(OfcCreateOrderDTO createOrderEntity) {
        // 发货方编码
        String consignorCode = createOrderEntity.getConsignorCode();
        String custCode = createOrderEntity.getCustCode();
        if (!PubUtils.isOEmptyOrNull(consignorCode)) {
            ResultModel resultModel = queryContactAndSet(createOrderEntity, custCode, "2", consignorCode);
            if (ResultModel.ResultEnum.CODE_3001.getCode().equals(resultModel.getCode())) {
                createOrderEntity.setConsignorCode(null);
                return checkConsignorInfo(createOrderEntity);
            } else {
                return resultModel;
            }
        } else {
            String orderType = createOrderEntity.getOrderType();
            String consignor_name = createOrderEntity.getConsignorName();
            String consignor_contact = createOrderEntity.getConsignorContact();
            String consignor_phone = createOrderEntity.getConsignorPhone();
            String consignor_address = createOrderEntity.getConsignorAddress();
            String provide_transport = createOrderEntity.getProvideTransport();
            if (TRANSPORT_ORDER.equals(orderType) || WAREHOUSE_DIST_ORDER.equals(orderType)) {
                if (TRANSPORT_ORDER.equals(orderType)) {
                    if (StringUtils.isBlank(consignor_name)) {
                        return new ResultModel("1000", "发货方名称信息不能为空");
                    } else if (StringUtils.isBlank(consignor_contact)) {
                        return new ResultModel("1000", "发货方联系人信息不能为空");
                    } else if (StringUtils.isBlank(consignor_phone)) {
                        return new ResultModel("1000", "发货方联系电话信息不能为空");
                    } else if (StringUtils.isBlank(consignor_address)) {
                        return new ResultModel("1000", "发货方地址信息不能为空");
                    }
                }
            }
            return new ResultModel(ResultModel.ResultEnum.CODE_0000);
        }
    }

    // 收货方校验
    public ResultModel checkConsigneeInfo(OfcCreateOrderDTO createOrderEntity) {
        // 收货方编码
        String consigneeCode = createOrderEntity.getConsigneeCode();
        String custCode = createOrderEntity.getCustCode();
        if (!PubUtils.isOEmptyOrNull(consigneeCode)) {
            //大成客户只传客收货方编码和名称 补全收货方信息
            if (WAREHOUSE_DIST_ORDER.equals(createOrderEntity.getOrderType())) {
                CscContantAndCompanyDto  cscContantAndCompanyDto = new CscContantAndCompanyDto();
                CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
                cscContactCompanyDto.setContactCompanyCode(consigneeCode);
                cscContactCompanyDto.setContactCompanyName(createOrderEntity.getConsigneeName());
                CscContactDto cscContactDto = new CscContactDto();
                cscContactDto.setPurpose("1");
                cscContantAndCompanyDto.setCscContactDto(cscContactDto);
                cscContantAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
                cscContantAndCompanyDto.setPageNum(1);
                cscContantAndCompanyDto.setPageSize(10);
                cscContantAndCompanyDto.setCustomerCode(createOrderEntity.getCustCode());
                Wrapper<PageInfo<CscContantAndCompanyResponseDto>> pageInfoWrapper = cscContactEdasService.queryCscReceivingInfoListWithPage(cscContantAndCompanyDto);
                if (null == pageInfoWrapper || pageInfoWrapper.getCode() != Wrapper.SUCCESS_CODE) {
                    return new ResultModel(ResultModel.ResultEnum.CODE_NO_CONSIGNEE);
                }
                PageInfo<CscContantAndCompanyResponseDto> result = pageInfoWrapper.getResult();
                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto= result.getList().get(0);
                createOrderEntity.setConsigneeProvinceCode(cscContantAndCompanyResponseDto.getProvince());
                createOrderEntity.setConsigneeProvince(cscContantAndCompanyResponseDto.getProvinceName());
                createOrderEntity.setConsigneeCityCode(cscContantAndCompanyResponseDto.getCity());
                createOrderEntity.setConsigneeCity(cscContantAndCompanyResponseDto.getCityName());
                createOrderEntity.setConsigneeCountyCode(cscContantAndCompanyResponseDto.getArea());
                createOrderEntity.setConsigneeCounty(cscContantAndCompanyResponseDto.getAreaName());
                createOrderEntity.setConsigneeTownCode(cscContantAndCompanyResponseDto.getStreet());
                createOrderEntity.setConsigneeTown(cscContantAndCompanyResponseDto.getStreetName());
                createOrderEntity.setConsigneeAddress(cscContantAndCompanyResponseDto.getAddress());
                createOrderEntity.setConsigneeCode(cscContantAndCompanyResponseDto.getContactCompanyCode());
                createOrderEntity.setConsigneeName(cscContantAndCompanyResponseDto.getContactCompanyName());
                createOrderEntity.setConsigneeContact(cscContantAndCompanyResponseDto.getContactName());
                createOrderEntity.setConsigneePhone(cscContantAndCompanyResponseDto.getPhone());
                createOrderEntity.setConsigneeFax(cscContantAndCompanyResponseDto.getFax());
                createOrderEntity.setConsigneeEmail(cscContantAndCompanyResponseDto.getEmail());
                createOrderEntity.setConsigneeContactCode(cscContantAndCompanyResponseDto.getContactCode());
                createOrderEntity.setConsigneeZip(cscContantAndCompanyResponseDto.getPostCode());
            }else {
                ResultModel resultModel = queryContactAndSet(createOrderEntity, custCode,"1", consigneeCode);
                if (ResultModel.ResultEnum.CODE_3001.getCode().equals(resultModel.getCode())) {
                    createOrderEntity.setConsigneeCode(null);
                    return checkConsigneeInfo(createOrderEntity);
                } else {
                    return resultModel;
                }
            }

        } else {
            boolean isNeedValidateConSignee = false;
            String orderType = createOrderEntity.getOrderType();
            String consignee_name = createOrderEntity.getConsigneeName();
            String consignee_contact = createOrderEntity.getConsigneeContact();
            String consignee_phone = createOrderEntity.getConsigneePhone();
            String consignee_address = createOrderEntity.getConsigneeAddress();
            String provide_transport = createOrderEntity.getProvideTransport();
            if (TRANSPORT_ORDER.equals(orderType) || WAREHOUSE_DIST_ORDER.equals(orderType)) {
                if (WAREHOUSE_DIST_ORDER.equals(orderType)) {
                    if ( createOrderEntity.getBusinessType().indexOf("61") != -1 ) {
                        isNeedValidateConSignee = true;
                    }
                }else {
                    isNeedValidateConSignee = true;
                }
                if (isNeedValidateConSignee) {
                    if (StringUtils.isBlank(consignee_name)) {
                        return new ResultModel("1000", "收货方名称信息不能为空");
                    } else if (StringUtils.isBlank(consignee_contact)) {
                        return new ResultModel("1000", "收货方联系人信息不能为空");
                    } else if (StringUtils.isBlank(consignee_phone)) {
                        return new ResultModel("1000", "收货方联系电话信息不能为空");
                    } else if (StringUtils.isBlank(consignee_address)) {
                        return new ResultModel("1000", "收货方地址信息不能为空");
                    }
                }
            }
        }
        return new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }

    private ResultModel queryContactAndSet(OfcCreateOrderDTO createOrderEntity, String companyCode, String purpose, String storeCode) {
        String custCode = createOrderEntity.getCustCode();
        CscQueryStoreCodeReqDto param = new CscQueryStoreCodeReqDto();
        param.setStoreCode(storeCode);
        param.setCustomerCode(companyCode);
        param.setPurpose(purpose);
        try {
            Wrapper<List<CscContantAndCompanyResponseDto>> result = cscContactCompanyEdasService.queryStoreCodeByCustomerAndStoreCode(param);
            String json = JacksonUtil.toJson(result);
            logger.info("=====================" + json);
            if (Wrapper.SUCCESS_CODE == result.getCode() && PubUtils.isNotNullAndBiggerSize(result.getResult(), 0)) {
                CscContantAndCompanyResponseDto contactInfo = result.getResult().get(0);
                String provinceCode = contactInfo.getProvince();
                String provinceName = contactInfo.getProvinceName();
                String cityCode = contactInfo.getCity();
                String cityName = contactInfo.getCityName();
                String countyCode = contactInfo.getArea();
                String countyName = contactInfo.getAreaName();
                String townCode = contactInfo.getStreet();
                String townName = contactInfo.getStreetName();
                String address = contactInfo.getAddress();
                String contactCompanyCode = contactInfo.getContactCompanyCode();
                String contactCompanyName = contactInfo.getContactCompanyName();
                String contactName = contactInfo.getContactName();
                String phone = contactInfo.getPhone();
                String fax = contactInfo.getFax();
                String email = contactInfo.getEmail();
                String zip = contactInfo.getPostCode();
                if ("1".equals(purpose)) {
                    createOrderEntity.setConsigneeProvinceCode(provinceCode);
                    createOrderEntity.setConsigneeProvince(provinceName);
                    createOrderEntity.setConsigneeCityCode(cityCode);
                    createOrderEntity.setConsigneeCity(cityName);
                    createOrderEntity.setConsigneeCountyCode(countyCode);
                    createOrderEntity.setConsigneeCounty(countyName);
                    createOrderEntity.setConsigneeTownCode(townCode);
                    createOrderEntity.setConsigneeTown(townName);
                    createOrderEntity.setConsigneeAddress(address);
                    createOrderEntity.setConsigneeCode(contactCompanyCode);
                    createOrderEntity.setConsigneeName(contactCompanyName);
                    createOrderEntity.setConsigneeContact(contactName);
                    createOrderEntity.setConsigneePhone(phone);
                    createOrderEntity.setConsigneeFax(fax);
                    createOrderEntity.setConsigneeEmail(email);
                    createOrderEntity.setConsigneeZip(zip);
                } else if ("2".equals(purpose)) {
                    createOrderEntity.setConsignorProvinceCode(provinceCode);
                    createOrderEntity.setConsignorProvince(provinceName);
                    createOrderEntity.setConsignorCityCode(cityCode);
                    createOrderEntity.setConsignorCity(cityName);
                    createOrderEntity.setConsignorCountyCode(countyCode);
                    createOrderEntity.setConsignorCounty(countyName);
                    createOrderEntity.setConsignorTownCode(townCode);
                    createOrderEntity.setConsignorTown(townName);
                    createOrderEntity.setConsignorAddress(address);
                    createOrderEntity.setConsignorCode(contactCompanyCode);
                    createOrderEntity.setConsignorName(contactCompanyName);
                    createOrderEntity.setConsignorContact(contactName);
                    createOrderEntity.setConsignorPhone(phone);
                    createOrderEntity.setConsignorFax(fax);
                    createOrderEntity.setConsignorEmail(email);
                    createOrderEntity.setConsignorZip(zip);
                }
            } else {
                if ("1".equals(purpose)) {
                    logger.error("==>客户 {} 查询不到收货方 {}", custCode, storeCode);
                    return new ResultModel("3001", "客户 " + custCode + " 找不到收货方 ");
                } else {
                    logger.error("==>客户 {} 查询不到发货方 {}", custCode, storeCode);
                    return new ResultModel("3001", "客户 " + custCode + " 找不到发货方 ");
                }
            }
        } catch (Exception e) {
            logger.error("查询收发货方发生错误 {}", e);
            return new ResultModel(ResultModel.ResultEnum.CODE_1000);
        }
        return new ResultModel(ResultModel.ResultEnum.CODE_0000);
    }
}
