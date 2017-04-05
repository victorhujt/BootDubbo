package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSON;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.dto.QueryStoreDto;
import com.xescm.csc.model.dto.QueryWarehouseDto;
import com.xescm.csc.model.dto.warehouse.CscWarehouseDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.model.vo.CscStorevo;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.csc.provider.CscGoodsEdasService;
import com.xescm.csc.provider.CscStoreEdasService;
import com.xescm.csc.provider.CscWarehouseEdasService;
import com.xescm.epc.edas.service.EpcBaiDuEdasService;
import com.xescm.ofc.constant.OrderConstant;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcAddressReflectMapper;
import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.model.dto.coo.CreateOrderEntity;
import com.xescm.ofc.model.dto.coo.CreateOrderGoodsInfo;
import com.xescm.ofc.model.dto.coo.CreateOrderTrans;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xescm.ofc.constant.OrderConstConstant.CREATE_ORDER_BYAPI;
import static com.xescm.ofc.constant.OrderConstConstant.PENDING_AUDIT;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.REVIEW;

@Service
public class OfcCreateOrderServiceImpl implements OfcCreateOrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
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

    @Override
    public int queryCountByOrderStatus(String orderCode, String orderStatus) {
        return createOrdersMapper.queryCountByOrderStatus(orderCode, orderStatus);
    }

    @Transactional
    public ResultModel ofcCreateOrder(CreateOrderEntity createOrderEntity, String orderCode) throws BusinessException {
        ResultModel resultModel;

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
        //校验货主名称
//        if (StringUtils.isBlank(custName)) {
//            logger.error("校验数据{}失败：{}", "货主名称", custName);
//            return new ResultModel(ResultModel.ResultEnum.CODE_0008);
//        }

        QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
        queryCustomerCodeDto.setCustomerCode(custCode);
        Wrapper<CscCustomerVo> customerVoWrapper = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
        if (customerVoWrapper.getResult() == null) {
            logger.error("获取货主信息失败：custId:{}，{}", custCode, customerVoWrapper.getMessage());
            return new ResultModel(ResultModel.ResultEnum.CODE_0009);
        } else {
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

        //check 数量、重量、体积 三选一不能为空
//        resultModel = CheckUtils.checkQuantityAndWeightAndCubage(createOrderEntity.getQuantity(), createOrderEntity.getWeight(), createOrderEntity.getCubage());
//        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
//            logger.error("校验数据{}失败：{}", "数量、重量、体积 三选一不能为空", resultModel.getCode());
//            return resultModel;
//        }



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
        }/* else {
            logger.error("获取该客户下的店铺编码接口返回失败，custCode:{},接口返回值:{}", custCode, ToStringBuilder.reflectionToString(cscStoreVoList));
            resultModel = new ResultModel(ResultModel.ResultEnum.CODE_0003);
            return resultModel;
        }*/
        createOrderEntity.setStoreCode(storeCode);

        //校验：【发货方】与【收货方】//2017年3月20日 追加逻辑:收发货方地址没有细化到二级,也能过,订单状态为待审核,不进行自动审核,对地址进行匹配
        resultModel = CheckUtils.checkWaresDist(createOrderEntity);
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
        List<CreateOrderGoodsInfo> createOrderGoodsInfos = createOrderEntity.getCreateOrderGoodsInfos();
        for (CreateOrderGoodsInfo goodsInfo : createOrderGoodsInfos) {
            String goodsCode = goodsInfo.getGoodsCode();
            CscGoodsApiDto cscGoods = new CscGoodsApiDto();
            cscGoods.setCustomerCode(custCode);
            cscGoods.setGoodsCode(goodsCode);
            Wrapper<List<CscGoodsApiVo>> goodsRest = cscGoodsEdasService.queryCscGoodsList(cscGoods);
            if (OrderConstant.TRANSPORT_ORDER.equals(orderType)) {  // 运输订单 - 如果货品存在则回填大小分类
                if (Wrapper.SUCCESS_CODE == goodsRest.getCode()) {
                    for (CscGoodsApiVo goodsApiVo : goodsRest.getResult()) {
                        goodsInfo.setGoodsType(goodsApiVo.getGoodsTypeParentName());
                        goodsInfo.setGoodsCategory(goodsApiVo.getGoodsTypeName());
                    }
                }
            } else {    // 仓储订单 - 货品必须存在
                resultModel = CheckUtils.checkGoodsInfo(goodsRest, goodsInfo);
                if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                    logger.error("校验数据：{}货品编码：{}失败：{}", "货品档案信息", goodsCode, resultModel.getCode());
                    return resultModel;
                }
            }
            //2017年3月29日 lyh 追加逻辑: 表头体积重量数量由表体货品决定
            this.fixOrderGoodsMsg(createOrderEntity, goodsInfo);
        }

        //转换 dto → do
        CreateOrderTrans createOrderTrans = new CreateOrderTrans(createOrderEntity, orderCode);
        OfcFundamentalInformation ofcFundamentalInformation = createOrderTrans.getOfcFundamentalInformation();
        ofcFundamentalInformation.setStoreName(storeName);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = createOrderTrans.getOfcDistributionBasicInfo();
        OfcFinanceInformation ofcFinanceInformation = createOrderTrans.getOfcFinanceInformation();
        OfcWarehouseInformation ofcWarehouseInformation = createOrderTrans.getOfcWarehouseInformation();
        OfcOrderStatus ofcOrderStatus = createOrderTrans.getOfcOrderStatus();
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = createOrderTrans.getOfcGoodsDetailsInfoList();
        //调用创建订单方法
        resultModel = this.createOrders(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
        if (StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
            //操作成功
            logger.info("校验数据成功，执行创单操作成功；orderCode:{}", orderCode);
        }
        return resultModel;
    }

    /**
     *  2017年3月29日 lyh 追加逻辑: 表头体积重量数量由表体货品决定
     * @param createOrderEntity 表头
     * @param createOrderGoodsInfo 货品
     */
    private void fixOrderGoodsMsg(CreateOrderEntity createOrderEntity, CreateOrderGoodsInfo createOrderGoodsInfo) {
        logger.info("表头体积重量数量计算 == > 表头 createOrderEntity :{}", createOrderEntity);
        logger.info("表头体积重量数量计算 == > 货品 createOrderGoodsInfo :{}", createOrderGoodsInfo);
        //货品信息
        String quantityDetail = createOrderGoodsInfo.getQuantity();
        String cubageDetail = createOrderGoodsInfo.getCubage();
        String weightDetail = createOrderGoodsInfo.getWeight();
        if(!PubUtils.isSEmptyOrNull(quantityDetail)){
            String quantityHead = createOrderEntity.getQuantity();
            if(!PubUtils.isSEmptyOrNull(quantityHead)){
                BigDecimal quan = new BigDecimal(quantityDetail);
                BigDecimal quantityResult = new BigDecimal(quantityHead);
                quantityResult = quantityResult.add(quan);
                createOrderEntity.setQuantity(quantityResult.toString());
            }
        }
        if(!PubUtils.isSEmptyOrNull(weightDetail)){
            String weightHead = createOrderEntity.getWeight();
            if(!PubUtils.isSEmptyOrNull(weightHead)){
                BigDecimal weig = new BigDecimal(weightDetail);
                BigDecimal weightHeadResult = new BigDecimal(weightHead);
                weightHeadResult = weightHeadResult.add(weig);
                createOrderEntity.setWeight(weightHeadResult.toString());
            }
        }
        if(!PubUtils.isSEmptyOrNull(cubageDetail)){
            String cubageHead = createOrderEntity.getCubage();
            if(!PubUtils.isSEmptyOrNull(cubageHead)){
                BigDecimal cuba = new BigDecimal(cubageDetail);
                BigDecimal cubageHeadResult = new BigDecimal(cubageHead);
                cubageHeadResult = cubageHeadResult.add(cuba);
                createOrderEntity.setCubage(cubageHeadResult.toString());
            }
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
    private ResultModel createOrders(OfcFundamentalInformation ofcFundamentalInformation,
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
                ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            }
//            ofcOrderStatusService.save(ofcOrderStatus);
            try {
                //自动审核通过 review:审核；rereview:反审核
                if(sEmptyOrNull){
                    //自动审核通过 review:审核；rereview:反审核
                    this.orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
                } else {
                    this.fixOrEeAddress(ofcDistributionBasicInfo);
                    //然后再更新运输信息
                    ofcDistributionBasicInfoService.update(ofcDistributionBasicInfo);
                    //如果能匹配成功, 就继续审核, 如果匹配不成功才是未审核
                    sEmptyOrNull = this.checkAddressPass(ofcDistributionBasicInfo);
                    if(sEmptyOrNull){
                        this.orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
                    }
                }
                logger.info("订单基本信息:{}",ToStringBuilder.reflectionToString(ofcFundamentalInformation));
                //推结算
               // ofcOrderManageService.pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,ofcGoodsDetailsInfoList);
            } catch (BusinessException ex) {
                logger.error("自动审核异常，{}", ex);
                throw new BusinessException("自动审核异常", ex);
            }
            return new ResultModel(ResultModel.ResultEnum.CODE_0000);
        } else {

            ofcFundamentalInformationService.save(ofcFundamentalInformation);
            if(sEmptyOrNull){
                ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
            }
            ofcWarehouseInformationService.save(ofcWarehouseInformation);
            ofcFinanceInformationService.save(ofcFinanceInformation);
            for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo : ofcGoodsDetailsInfoList) {
                ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            }
            ofcOrderStatusService.save(ofcOrderStatus);
            try {
                //地址编码不为空才走自动审核, 为空的状态还是待审核, 并调用EPC端口补齐
                if(sEmptyOrNull){

                    //自动审核通过 review:审核；rereview:反审核
                    this.orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
                } else {
                    this.fixOrEeAddress(ofcDistributionBasicInfo);
                    //然后再保存运输信息
                    ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
                    //如果能匹配成功, 就继续审核, 如果匹配不成功才是未审核
                    sEmptyOrNull = this.checkAddressPass(ofcDistributionBasicInfo);
                    if(sEmptyOrNull){
                        this.orderApply(ofcFundamentalInformation, ofcDistributionBasicInfo, ofcFinanceInformation, ofcWarehouseInformation, ofcGoodsDetailsInfoList, ofcOrderStatus);
                    }
                }
                logger.info("订单基本信息:{}",ToStringBuilder.reflectionToString(ofcFundamentalInformation));
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
        if(!PubUtils.isSEmptyOrNull(departuePlaceCode)){
            ofcDistributionBasicInfo.setDeparturePlaceCode(departuePlaceCode);
        }
        String destinationCode = this.explainAddressByRmc(ofcDistributionBasicInfo.getDestinationProvince()
                , ofcDistributionBasicInfo.getDepartureCity(), ofcDistributionBasicInfo.getDestinationDistrict());
        if(!PubUtils.isSEmptyOrNull(destinationCode)){
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
        if(!PubUtils.isSEmptyOrNull(departurePlace)){
            OfcAddressReflect ofcAddressReflect = ofcAddressReflectService.selectByAddress(departurePlace);
            logger.info("查询本地映射结果: ofcAddressReflect {}", ofcAddressReflect);
            if(null != ofcAddressReflect && !PubUtils.isSEmptyOrNull(ofcAddressReflect.getProvince())){
                logger.info("映射成功!");
                ofcAddressReflectService.reflectAddressToDis(ofcAddressReflect, ofcDistributionBasicInfo, "departure");
            } else {
                logger.info("映射失败!");
                logger.info("开始调用Epc接口进行解析!");
                Wrapper departurePlaceResult = epcBaiDuEdasService.showLocationStr(departurePlace);
                if(departurePlaceResult.getCode() == Wrapper.ERROR_CODE || null == departurePlaceResult.getResult()){
                    logger.error("出发完整地址调用EPC接口解析完整地址失败! destinationResult :{}", departurePlaceResult);
                    ofcAddressReflect = new OfcAddressReflect();
                    ofcAddressReflect.setAddress(departurePlace);
                    int insert = ofcAddressReflectMapper.insert(ofcAddressReflect);
                    if(insert < 1){
                        logger.error("存储出发完整地址映射失败!");
//                        throw new BusinessException("存储出发完整地址映射失败!");
                    }
                } else {
                    com.alibaba.fastjson.JSONObject departurePlaceObj = JSON.parseObject((String) departurePlaceResult.getResult());
                    Object departureProvince = departurePlaceObj.get("province");
                    Object departureCity = departurePlaceObj.get("city");
                    Object departureDistrict = departurePlaceObj.get("district");
                    if(null != departureProvince){
                        String depProvince = (String) departureProvince;
                        ofcDistributionBasicInfo.setDepartureProvince(depProvince);
                        if(null != departureCity){
                            String depCity = (String) departureCity;
                            ofcDistributionBasicInfo.setDepartureCity(depCity);
                            if(null != departureDistrict){
                                String depDistrict = (String) departureDistrict;
                                ofcDistributionBasicInfo.setDepartureDistrict(depDistrict);
                                //调用RMC接口, 查询省市区名称对应的编码, 并赋值
                                logger.info("调用RMC接口, 查询省市区名称对应的编码, 并赋值");
                                String departuePlaceCode = this.explainAddressByRmc(depProvince, depCity, depDistrict);
                                if(PubUtils.isSEmptyOrNull(departuePlaceCode)){
                                    logger.error("调用RMC接口, 查询出发省市区名称对应的编码失败! ");
                                }
                                ofcDistributionBasicInfo.setDeparturePlaceCode(departuePlaceCode);
                                /*ofcAddressReflect = new OfcAddressReflect();
                                ofcAddressReflectService.reflectAddressToRef(ofcAddressReflect, ofcDistributionBasicInfo, "departure");
                                int insert = ofcAddressReflectMapper.insert(ofcAddressReflect);
                                if(insert < 1){
                                    logger.error("存储明细地址映射失败!");
                                    throw new BusinessException("存储明细地址映射失败!");
                                }*/
                            }

                        }

                    }
                }
            }
        }

        if(!PubUtils.isSEmptyOrNull(destination)){
            OfcAddressReflect ofcAddressReflect = ofcAddressReflectService.selectByAddress(destination);
            logger.info("查询本地映射结果: ofcAddressReflect {}", ofcAddressReflect);
            if(null != ofcAddressReflect && !PubUtils.isSEmptyOrNull(ofcAddressReflect.getProvince())){
                logger.info("映射成功!");
                ofcAddressReflectService.reflectAddressToDis(ofcAddressReflect, ofcDistributionBasicInfo, "destination");
            } else {
                logger.info("映射失败!");
                logger.error("开始调用Epc接口进行解析! ");
                Wrapper destinationResult = epcBaiDuEdasService.showLocationStr(destination);
                if(destinationResult == null || destinationResult.getCode() == Wrapper.ERROR_CODE || null == destinationResult.getResult()){
                    logger.error("到达完整地址调用EPC接口解析完整地址失败! destinationResult :{}", destinationResult);
                    ofcAddressReflect = new OfcAddressReflect();
                    ofcAddressReflect.setAddress(destination);
                    int insert = ofcAddressReflectMapper.insert(ofcAddressReflect);
                    if(insert < 1){
                        logger.error("存储到达完整地址映射失败!");
//                        throw new BusinessException("存储到达完整地址映射失败!");
                    }
                } else {
                    com.alibaba.fastjson.JSONObject destinationObj = JSON.parseObject((String) destinationResult.getResult());
                    Object destinationProvince = destinationObj.get("province");
                    Object destinationCity = destinationObj.get("city");
                    Object destinationDistrict = destinationObj.get("district");
                    if(null != destinationProvince){
                        String desProvince = (String) destinationProvince;
                        ofcDistributionBasicInfo.setDestinationProvince(desProvince);
                        if(null != destinationCity){
                            String desCity = (String) destinationCity;
                            ofcDistributionBasicInfo.setDestinationCity(desCity);
                            if(null != destinationDistrict){
                                String desDistrict = (String) destinationDistrict;
                                ofcDistributionBasicInfo.setDestinationDistrict(desDistrict);
                                //调用RMC接口, 查询省市区名称对应的编码, 并赋值
                                logger.info("调用RMC接口, 查询省市区名称对应的编码, 并赋值");
                                String destinationCode = this.explainAddressByRmc(desProvince, desCity, desDistrict);
                                if(PubUtils.isSEmptyOrNull(destinationCode)){
                                    logger.error("调用RMC接口, 查询到达省市区名称对应的编码失败! ");
                                }
                                ofcDistributionBasicInfo.setDestinationCode(destinationCode);
                                /*ofcAddressReflect = new OfcAddressReflect();
                                ofcAddressReflectService.reflectAddressToRef(ofcAddressReflect, ofcDistributionBasicInfo, "destination");
                                int insert = ofcAddressReflectMapper.insert(ofcAddressReflect);
                                if(insert < 1){
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
        if(PubUtils.isSEmptyOrNull(province) && PubUtils.isSEmptyOrNull(city) && PubUtils.isSEmptyOrNull(district)){
            logger.error("调用RMC接口, 通过省市区名称取得对应编码, 省市区名称三者必填!");
            return null;
//            throw new BusinessException("调用RMC接口, 通过省市区名称取得对应编码, 省市区名称三者必填!");
        }
        Wrapper<RmcAddressCodeVo> codeByName = rmcAddressEdasService.findCodeByName(rmcAddressNameVo);
        if(codeByName.getCode() == Wrapper.ERROR_CODE || codeByName.getResult() == null){
            logger.error("调用RMC接口, 通过省市区名称取得对应编码,失败! 错误信息:{}", codeByName.getMessage());
            return null;
//            throw new BusinessException(codeByName.getMessage());
        }
        RmcAddressCodeVo rmcAddressCodeVo = codeByName.getResult();
        String provinceCode = rmcAddressCodeVo.getProvinceCode();
        String cityCode = rmcAddressCodeVo.getCityCode();
        String districtCode = rmcAddressCodeVo.getDistrictCode();
        if(!PubUtils.isSEmptyOrNull(provinceCode)){
            StringBuilder sb = new StringBuilder(provinceCode);
            if(!PubUtils.isSEmptyOrNull(cityCode)){
                sb.append(",").append(cityCode);
                if(!PubUtils.isSEmptyOrNull(districtCode)){
                    sb.append(",").append(districtCode);
                    return sb.toString();
                }
            }
        }
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

}
