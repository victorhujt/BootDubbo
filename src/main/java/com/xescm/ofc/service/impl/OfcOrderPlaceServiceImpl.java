package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.domain.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.enums.OrderConstEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscSupplierAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.PrimaryGenerater;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xescm.uam.utils.wrap.Wrapper;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ydx on 2016/10/12.
 */
@Service
@Transactional//既能注解在方法上,也能注解在类上.当注解在类上的时候,意味着这个类的所有public方法都是开启事务的,如果类级别和方法级别同事使用了该注解,则方法覆盖类.
//@Transactional(rollbackFor={xxx.class})
public class OfcOrderPlaceServiceImpl implements OfcOrderPlaceService {
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Autowired
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private FeignCscSupplierAPIClient feignCscSupplierAPIClient;
    @Resource
    private CodeGenUtils codeGenUtils;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public String placeOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos,String tag,AuthResDto authResDtoByToken, String custId
                            ,CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
                            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee,CscSupplierInfoDto cscSupplierInfoDto) {
        Wrapper<?> wrapperFun = validateFundamentalMessage(ofcOrderDTO);
        if(Wrapper.ERROR_CODE == wrapperFun.getCode()){
            throw new BusinessException(wrapperFun.getMessage());
        }
        OfcFundamentalInformation ofcFundamentalInformation = modelMapper.map(ofcOrderDTO, OfcFundamentalInformation.class);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = modelMapper.map(ofcOrderDTO, OfcDistributionBasicInfo.class);
        OfcWarehouseInformation  ofcWarehouseInformation = modelMapper.map(ofcOrderDTO, OfcWarehouseInformation.class);
        ofcFundamentalInformation.setCreationTime(new Date());
        ofcFundamentalInformation.setCreator(authResDtoByToken.getUamUser().getUserName());
        ofcFundamentalInformation.setCreatorName(authResDtoByToken.getUamUser().getUserName());
        ofcFundamentalInformation.setOperator(authResDtoByToken.getUamUser().getUserName());
        ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUamUser().getUserName());
        ofcFundamentalInformation.setOperTime(new Date());
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        //ofcFundamentalInformation.setStoreCode(ofcOrderDTO.getStoreName());//店铺还没维护表
        ofcFundamentalInformation.setStoreName(ofcOrderDTO.getStoreName());//店铺还没维护表
        ofcFundamentalInformation.setOrderSource("手动");//订单来源
        try {
            if (PubUtils.trimAndNullAsEmpty(tag).equals("place")){//下单

                String orderCodeByCustOrderCode = ofcFundamentalInformationService.getOrderCodeByCustOrderCode(ofcFundamentalInformation.getCustOrderCode());
                if (PubUtils.isSEmptyOrNull(orderCodeByCustOrderCode)){//根据客户订单编号查询唯一性
                    ofcFundamentalInformation.setOrderCode(codeGenUtils.getNewWaterCode("SO",6));
                    //"SO"+ PrimaryGenerater.getInstance()
                    //        .generaterNextNumber(PrimaryGenerater.getInstance().getLastNumber())
                    ofcFundamentalInformation.setCustCode(custId);
                    ofcFundamentalInformation.setCustName(authResDtoByToken.getUamUser().getUserName());
                    ofcFundamentalInformation.setAbolishMark(OrderConstEnum.ORDERWASNOTABOLISHED);//未作废
                    if (ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.WAREHOUSEDISTRIBUTIONORDER)){

                        if(null == ofcWarehouseInformation.getProvideTransport()){
                            ofcWarehouseInformation.setProvideTransport(OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS);
                        }
                        if(ofcWarehouseInformation.getProvideTransport()==OrderConstEnum.WAREHOUSEORDERPROVIDETRANS){
                            /*ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);*/
                            Wrapper<?> wrapper = validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                                throw new BusinessException(wrapper.getMessage());
                            }
                            addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
                            saveContactMessage(cscContantAndCompanyDtoConsignor,custId,authResDtoByToken);
                            saveContactMessage(cscContantAndCompanyDtoConsignee,custId,authResDtoByToken);
                        }
                        // 更新仓配信息
                        ofcWarehouseInformation=upOfcWarehouseInformation(ofcWarehouseInformation,ofcFundamentalInformation);
                        String businessTypeHead = ofcFundamentalInformation.getBusinessType().substring(0,2);
                        if("62".equals(businessTypeHead)){//如果是入库才有供应商信息//这儿才是入库
                            Wrapper<?> wrapper = validateSupportContactMessage(cscSupplierInfoDto);
                            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                                throw new BusinessException(wrapper.getMessage());
                            }
                            saveSupportMessage(cscSupplierInfoDto,custId,authResDtoByToken);

                        }
                        ofcWarehouseInformationService.save(ofcWarehouseInformation);
                        if("61".equals(businessTypeHead)){//如果是入库才有供应商信息//这儿是出库
                            ofcWarehouseInformation.setSupportCode("");
                            ofcWarehouseInformation.setSupportName("");
                        }
                    }else if(ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.TRANSPORTORDER)){
                        Wrapper<?> wrapper = validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                        if(Wrapper.ERROR_CODE == wrapper.getCode()){
                            throw new BusinessException(wrapper.getMessage());
                        }
                        //运输订单
                        if(!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDeparturePlaceCode()) && ofcDistributionBasicInfo.getDeparturePlaceCode().length() > 12){
                            String depatrueCode = ofcDistributionBasicInfo.getDeparturePlaceCode().substring(0,12);
                            String destinationCode = ofcDistributionBasicInfo.getDestinationCode().substring(0,12);
                            if(depatrueCode.equals(destinationCode)){
                                ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHECITY);
                            }else {
                                ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHETRUNK);
                            }
                        }else{
                            throw new BusinessException("四级地址编码为空!");
                        }

                        /*ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);*/
                        addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
                        saveContactMessage(cscContantAndCompanyDtoConsignor,custId,authResDtoByToken);
                        saveContactMessage(cscContantAndCompanyDtoConsignee,custId,authResDtoByToken);
                    }else{
                        throw new BusinessException("您选择的订单类型系统无法识别!");
                    }

                    ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                            +" "+"订单已创建");
                    upOrderStatus(ofcOrderStatus,ofcFundamentalInformation,authResDtoByToken);
                    //添加该订单的货品信息
                    for(OfcGoodsDetailsInfo ofcGoodsDetails : ofcGoodsDetailsInfos){
                        String orderCode = ofcFundamentalInformation.getOrderCode();
                        ofcGoodsDetails.setOrderCode(orderCode);
                        ofcGoodsDetails.setCreationTime(ofcFundamentalInformation.getCreationTime());
                        ofcGoodsDetails.setCreator(ofcFundamentalInformation.getCreator());
                        ofcGoodsDetails.setOperator(ofcFundamentalInformation.getOperator());
                        ofcGoodsDetails.setOperTime(ofcFundamentalInformation.getOperTime());
                        ofcGoodsDetailsInfoService.save(ofcGoodsDetails);
                    }
                    //添加基本信息
                    ofcFundamentalInformationService.save(ofcFundamentalInformation);
                }else{
                    throw new BusinessException("该客户订单编号已经存在!您不能重复下单!请查看订单编号为:" + orderCodeByCustOrderCode+ "的订单");
                }
            }else if (PubUtils.trimAndNullAsEmpty(tag).equals("manage")){ //编辑
                //现在订单编辑没有对客户订单编号进行校验
                if(PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getCustOrderCode())){
                    throw new BusinessException("您没有填写客户订单编号!");
                }
                if (("").equals(PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderCode())) || null == PubUtils.trimAndNullAsEmpty(ofcFundamentalInformation.getOrderCode())){
                    ofcFundamentalInformation.setOrderCode(ofcFundamentalInformationService.selectOne(ofcFundamentalInformation).getOrderCode());
                }
                //仓配订单
                if (ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.WAREHOUSEDISTRIBUTIONORDER)){//编辑时仓配订单
                    if(null == ofcWarehouseInformation.getProvideTransport()){
                        ofcWarehouseInformation.setProvideTransport(OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS);
                    }
                    //仓配单需要运输
                    if(ofcWarehouseInformation.getProvideTransport()==OrderConstEnum.WAREHOUSEORDERPROVIDETRANS){
                        Wrapper<?> wrapper = validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                        if(Wrapper.ERROR_CODE == wrapper.getCode()){
                            throw new BusinessException(wrapper.getMessage());
                        }
                        //如果编辑订单后, 还是需要提供运输, 就要更新运输信息
                        ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
                        saveContactMessage(cscContantAndCompanyDtoConsignor,custId,authResDtoByToken);
                        saveContactMessage(cscContantAndCompanyDtoConsignee,custId,authResDtoByToken);
                        OfcDistributionBasicInfo ofcDist = new OfcDistributionBasicInfo();
                        ofcDist.setOrderCode(ofcFundamentalInformation.getOrderCode());
                        List<OfcDistributionBasicInfo> select = ofcDistributionBasicInfoService.select(ofcDist);
                        if(select.size() > 0){//有运输信息
                            ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
                        }else if (select.size() == 0){
                            addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
                        }
                    //仓配单不需要运输,需要将属于该订单的运输信息删除
                    }else if (ofcWarehouseInformation.getProvideTransport() == OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS){
                        ofcFundamentalInformation.setSecCustCode("");
                        ofcFundamentalInformation.setSecCustName("");
                        ofcDistributionBasicInfoService.deleteByOrderCode(ofcFundamentalInformation.getOrderCode());
                    }
                    // 更新仓配信息
                    ofcWarehouseInformation=upOfcWarehouseInformation(ofcWarehouseInformation,ofcFundamentalInformation);
                    //入库
                    if("62".equals(ofcFundamentalInformation.getBusinessType().substring(0,2))){//如果是入库才有供应商信息
                        Wrapper<?> wrapper = validateSupportContactMessage(cscSupplierInfoDto);
                        if(Wrapper.ERROR_CODE == wrapper.getCode()){
                            throw new BusinessException(wrapper.getMessage());
                        }
                        String saveSupportMessageResult = saveSupportMessage(cscSupplierInfoDto,custId,authResDtoByToken);
                    }
                    //出库
                    if("61".equals(ofcFundamentalInformation.getBusinessType().substring(0,2))){//如果是入库才有供应商信息
                        ofcWarehouseInformation.setSupportCode("");
                        ofcWarehouseInformation.setSupportName("");
                        //去数据库清掉相应的供应商信息
                    }
                    //先去数据库查, 如果有就是改, 如果没有就是增
                    OfcWarehouseInformation ofcWare = new OfcWarehouseInformation();
                    ofcWare.setOrderCode(ofcFundamentalInformation.getOrderCode());
                    List<OfcWarehouseInformation> select = ofcWarehouseInformationService.select(ofcWare);
                    if(select.size() > 0){//有
                        ofcWarehouseInformationService.updateByOrderCode(ofcWarehouseInformation);
                    }else if(select.size() == 0){
                        saveWarehouseMessage(ofcWarehouseInformation);
                    }
                }else if(ofcFundamentalInformation.getOrderType().equals(OrderConstEnum.TRANSPORTORDER)){
                    Wrapper<?> wrapper = validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
                    if(Wrapper.ERROR_CODE == wrapper.getCode()){
                        throw new BusinessException(wrapper.getMessage());
                    }
                    //删除仓配信息
                    OfcWarehouseInformation ofcWarehouseInformationForTrans = new OfcWarehouseInformation();
                    ofcWarehouseInformationForTrans.setOrderCode(ofcFundamentalInformation.getOrderCode());
                    ofcWarehouseInformationService.delete(ofcWarehouseInformationForTrans);
                    //更新运输信息
                    //运输订单
                    if(!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDeparturePlaceCode()) && ofcDistributionBasicInfo.getDeparturePlaceCode().length() > 12){
                        String depatrueCode = ofcDistributionBasicInfo.getDeparturePlaceCode().substring(0,12);
                        String destinationCode = ofcDistributionBasicInfo.getDestinationCode().substring(0,12);
                        if(depatrueCode.equals(destinationCode)){
                            ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHECITY);
                        }else {
                            ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHETRUNK);
                        }
                    }else{
                        ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHECITY);
                    }
                    ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
                    saveContactMessage(cscContantAndCompanyDtoConsignor,custId,authResDtoByToken);
                    saveContactMessage(cscContantAndCompanyDtoConsignee,custId,authResDtoByToken);
                    OfcDistributionBasicInfo ofcDist = new OfcDistributionBasicInfo();
                    ofcDist.setOrderCode(ofcFundamentalInformation.getOrderCode());
                    List<OfcDistributionBasicInfo> select = ofcDistributionBasicInfoService.select(ofcDist);
                    if(select.size() > 0){//有运输信息
                        ofcDistributionBasicInfoService.updateByOrderCode(ofcDistributionBasicInfo);
                    }else if (select.size() < 0){
                        addDistributionInfo(ofcDistributionBasicInfo, ofcFundamentalInformation);
                    }
                }else{
                    throw new BusinessException("您的订单类型系统无法识别!");
                }
                //删除之前订单的货品信息
                OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
                ofcGoodsDetailsInfo.setOrderCode(ofcOrderDTO.getOrderCode());
                ofcGoodsDetailsInfoService.delete(ofcGoodsDetailsInfo);
                //添加该订单的货品信息
                for(OfcGoodsDetailsInfo ofcGoodsDetails : ofcGoodsDetailsInfos){
                    String orderCode = ofcFundamentalInformation.getOrderCode();
                    ofcGoodsDetails.setOrderCode(orderCode);
                    ofcGoodsDetails.setCreationTime(ofcFundamentalInformation.getCreationTime());
                    ofcGoodsDetails.setCreator(ofcFundamentalInformation.getCreator());
                    ofcGoodsDetails.setOperator(ofcFundamentalInformation.getOperator());
                    ofcGoodsDetails.setOperTime(ofcFundamentalInformation.getOperTime());
                    ofcGoodsDetailsInfoService.save(ofcGoodsDetails);
                }


                ofcFundamentalInformation.setOperator(authResDtoByToken.getUamUser().getUserName());
                ofcFundamentalInformation.setOperTime(new Date());
                ofcOrderStatus.setNotes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        +" "+"订单已更新");
                upOrderStatus(ofcOrderStatus,ofcFundamentalInformation,authResDtoByToken);
                ofcFundamentalInformationService.update(ofcFundamentalInformation);
            }else {
                throw new BusinessException("未知操作!系统无法识别!");
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        if("place".equals(tag)){
            return "您已成功下单!";
        }else if("manage".equals(tag)){
            return "您的订单修改成功!";
        }else {
            return "异常";
        }
    }

    /**
     * 更新并保存订单状态
     * @param ofcOrderStatus
     * @param ofcFundamentalInformation
     * @param authResDtoByToken
     */
    public void upOrderStatus(OfcOrderStatus ofcOrderStatus,OfcFundamentalInformation ofcFundamentalInformation,AuthResDto authResDtoByToken){
        ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcOrderStatus.setOrderStatus(OrderConstEnum.PENDINGAUDIT);
        ofcOrderStatus.setStatusDesc("待审核");
        ofcOrderStatus.setLastedOperTime(new Date());
        ofcOrderStatus.setOperator(authResDtoByToken.getUamUser().getUserName());
        ofcOrderStatusService.save(ofcOrderStatus);
    }


    /**
     * 添加运输订单
     * @param ofcDistributionBasicInfo
     * @param ofcFundamentalInformation
     */
    public void addDistributionInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo, OfcFundamentalInformation ofcFundamentalInformation){
        ofcFundamentalInformation.setSecCustCode("001");
        ofcFundamentalInformation.setSecCustName("众品");
        /*if (PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDeparturePlace())
                .equals(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getDestination()))){
            ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHECITY);
        }else{
            ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHETRUNK);
        }*/
        ofcDistributionBasicInfo=upDistributionBasicInfo(ofcDistributionBasicInfo,ofcFundamentalInformation);
        ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);
    }

    public OfcDistributionBasicInfo upDistributionBasicInfo(OfcDistributionBasicInfo ofcDistributionBasicInfo
            ,OfcFundamentalInformation ofcFundamentalInformation){
        ofcDistributionBasicInfo.setTransCode(ofcFundamentalInformation.getOrderCode().replace("SO","TSO"));
        /*ofcDistributionBasicInfo.setDeparturePlaceCode("001");
        ofcDistributionBasicInfo.setDestinationCode("001");*/
        if(null == ofcFundamentalInformation.getCreationTime()){
            ofcDistributionBasicInfo.setCreationTime(ofcFundamentalInformation.getCreationTime());
            ofcDistributionBasicInfo.setCreator(ofcFundamentalInformation.getCreator());
        }
        ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcDistributionBasicInfo.setOperator(ofcFundamentalInformation.getOperator());
        ofcDistributionBasicInfo.setOperTime(ofcFundamentalInformation.getOperTime());
        return ofcDistributionBasicInfo;
    }

    public OfcWarehouseInformation upOfcWarehouseInformation(OfcWarehouseInformation ofcWarehouseInformation
            ,OfcFundamentalInformation ofcFundamentalInformation){
        /*ofcWarehouseInformation.setSupportCode("001");
        ofcWarehouseInformation.setSupportName("众品");*/
        ofcWarehouseInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
        ofcWarehouseInformation.setCreationTime(ofcFundamentalInformation.getCreationTime());
        ofcWarehouseInformation.setCreator(ofcFundamentalInformation.getCreator());
        ofcWarehouseInformation.setOperTime(ofcFundamentalInformation.getOperTime());
        ofcWarehouseInformation.setOperator(ofcFundamentalInformation.getOperator());
        return ofcWarehouseInformation;
    }

    /**
     * 下单或编辑时在订单中心本地保存客户订单中的货品信息
     */
    public String saveOrderGoodsList(List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList){
        if (ofcGoodsDetailsInfoList.size() < 1){
            return "未添加货品信息";
        }
        for (OfcGoodsDetailsInfo ofcGoodsDetailsInfo: ofcGoodsDetailsInfoList) {
            int save = ofcGoodsDetailsInfoService.save(ofcGoodsDetailsInfo);
            if(save == 0){
                throw new BusinessException("保存货品信息失败");
            }
        }
        return Wrapper.SUCCESS_MESSAGE;
    }
    /**
     * 下单或编辑时保存发货方收货方及其联系人
     * saveContactMessage(cscContantAndCompanyDtoConsignee,custId
     ,authResDtoByToken.getUserId(),authResDtoByToken.getUamUser().getUserName(),authResDtoByToken.getGroupId());
     */
    public String saveContactMessage(CscContantAndCompanyDto cscContantAndCompanyDto,String custId,AuthResDto authResDtoByToken){//AuthResDto authResDtoByToken,//String custId,String userId,String userName,String groupId
        if(null == cscContantAndCompanyDto){
            throw new BusinessException("未添加联系人信息");
        }
        try {
            cscContantAndCompanyDto.setCustomerId(custId);
            Wrapper<List<CscContantAndCompanyVo>> listWrapper = feignCscCustomerAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
            if(listWrapper.getResult().size() > 0){
                return "该联系人信息已在资源中心中存在,无需再次添加!";
            }
            cscContantAndCompanyDto.setUserId(authResDtoByToken.getUserId());
            cscContantAndCompanyDto.setUserName(authResDtoByToken.getUamUser().getUserName());
            cscContantAndCompanyDto.setGroupId(authResDtoByToken.getGroupId());
            /*cscContantAndCompanyDto.getCscContact().setProvince("ofc001");
            cscContantAndCompanyDto.getCscContact().setCity("ofc001");
            cscContantAndCompanyDto.getCscContact().setArea("ofc001");
            cscContantAndCompanyDto.getCscContact().setStreet("ofc001");*/
            /**
             * 校验地址编码和名称是否完整
             */
            String provinceCode = cscContantAndCompanyDto.getCscContact().getProvince();
            String provinceName = cscContantAndCompanyDto.getCscContact().getProvinceName();
            String cityCode = cscContantAndCompanyDto.getCscContact().getCity();
            String cityName = cscContantAndCompanyDto.getCscContact().getCityName();
            String areaCode = cscContantAndCompanyDto.getCscContact().getArea();
            String areaName = cscContantAndCompanyDto.getCscContact().getAreaName();
            String streetCode = cscContantAndCompanyDto.getCscContact().getStreet();
//            String streetName = cscContantAndCompanyDto.getCscContact().getStreetName();
            if(PubUtils.isSEmptyOrNull(provinceCode) || PubUtils.isSEmptyOrNull(provinceName)
                    || PubUtils.isSEmptyOrNull(cityCode) || PubUtils.isSEmptyOrNull(cityName)
                    || PubUtils.isSEmptyOrNull(areaCode) || PubUtils.isSEmptyOrNull(areaName)
                   /* || PubUtils.isSEmptyOrNull(streetCode) || PubUtils.isSEmptyOrNull(streetName)*/){
                throw new BusinessException("联系人地址不完整");
            }



            Wrapper<?> wrapper = feignCscCustomerAPIClient.addCscContantAndCompany(cscContantAndCompanyDto);
            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                throw new BusinessException(wrapper.getMessage());
            }
        }catch (Exception ex){
            throw new BusinessException(ex.getMessage());
        }
        return Wrapper.SUCCESS_MESSAGE;
    }
    /**
     * 下单或编辑时保存供应商及供应商联系人
     */
    public String saveSupportMessage(CscSupplierInfoDto cscSupplierInfoDto,String custId,AuthResDto authResDtoByToken){
        if(null == cscSupplierInfoDto){
            throw new BusinessException( "未添加供应商信息");
        }
        try {
            String provinceCode = cscSupplierInfoDto.getProvince();
            String provinceName = cscSupplierInfoDto.getProvinceName();
            String cityCode = cscSupplierInfoDto.getCity();
            String cityName = cscSupplierInfoDto.getCityName();
            String areaCode = cscSupplierInfoDto.getArea();
            String areaName = cscSupplierInfoDto.getAreaName();
            String streetCode = cscSupplierInfoDto.getStreet();
//            String streetName = cscSupplierInfoDto.getStreetName();
            if(PubUtils.isSEmptyOrNull(provinceCode) || PubUtils.isSEmptyOrNull(provinceName)
                    || PubUtils.isSEmptyOrNull(cityCode) || PubUtils.isSEmptyOrNull(cityName)
                    || PubUtils.isSEmptyOrNull(areaCode) || PubUtils.isSEmptyOrNull(areaName)
                    /*|| PubUtils.isSEmptyOrNull(streetCode) || PubUtils.isSEmptyOrNull(streetName)*/){
                throw new BusinessException("联系人地址不完整");
            }
            cscSupplierInfoDto.setCustomerId(custId);
            Wrapper<List<CscSupplierInfoDto>> listWrapper = null;
            try{
                listWrapper = feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
            }catch (Exception ex){
                if(Wrapper.ERROR_CODE != listWrapper.getCode()){
                    return listWrapper.getMessage();
                }
            }
            if(listWrapper.getResult().size() > 0){
                return "该供应商信息已在资源中心中存在,无需再次添加!";
            }
            cscSupplierInfoDto.setSupplierCode("spofc" + System.currentTimeMillis());
            cscSupplierInfoDto.setUserId(authResDtoByToken.getUserId());
            cscSupplierInfoDto.setUserName(authResDtoByToken.getUamUser().getUserName());
            cscSupplierInfoDto.setGroupId(authResDtoByToken.getGroupId());
            Wrapper<?> wrapper = feignCscSupplierAPIClient.addSupplierBySupplierCode(cscSupplierInfoDto);
            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                throw new BusinessException(wrapper.getMessage());
            }
        }catch (Exception ex){
            throw new BusinessException("添加供应商信息出错!");
        }
        return Wrapper.SUCCESS_MESSAGE;
    }
    /**
     * 下单或编辑时在订单中心为用户保存订单中的仓库信息
     */
    public String saveWarehouseMessage(OfcWarehouseInformation ofcWarehouseInformation){
        if(null == ofcWarehouseInformation){
            return "未添加仓库信息";
        }
        try {
            int save = ofcWarehouseInformationService.save(ofcWarehouseInformation);
            if(save == 0){
                throw new BusinessException("保存仓库信息失败");
            }
        }catch (Exception ex){
            throw new BusinessException("保存仓库信息失败!");
        }
        return Wrapper.SUCCESS_MESSAGE;
    }


    public Wrapper<?> validateFundamentalMessage(OfcOrderDTO ofcOrderDTO){
        if(null == ofcOrderDTO.getOrderTime()){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"请选择订单日期");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }

    /**
     * 校验收发货方信息
     * @param cscContantAndCompanyDtoConsignor
     * @param cscContantAndCompanyDtoConsignee
     * @return
     */
    public Wrapper<?> validateDistrictContactMessage(CscContantAndCompanyDto cscContantAndCompanyDtoConsignor, CscContantAndCompanyDto cscContantAndCompanyDtoConsignee){
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContactCompany().getContactCompanyName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"请输入发货方信息");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContact().getContactName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人名称未填写");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContact().getPhone())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人电话未填写");
        }
        //二级地址还需特殊处理
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContact().getProvinceName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人地址未选择");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContact().getCityName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人地址不完整");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignor.getCscContact().getAreaName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"发货方联系人地址不完整");
        }

        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContactCompany().getContactCompanyName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"请输入收货方信息");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContact().getContactName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人名称未填写");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContact().getPhone())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人电话未填写");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContact().getProvinceName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人地址未选择");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContact().getCityName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人地址不完整");
        }
        if(PubUtils.isSEmptyOrNull(cscContantAndCompanyDtoConsignee.getCscContact().getAreaName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人地址不完整");
        }

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }

    /**
     * 校验供应商联系人
     */
    public Wrapper<?> validateSupportContactMessage(CscSupplierInfoDto cscSupplierInfoDto){
        if(PubUtils.isSEmptyOrNull(cscSupplierInfoDto.getSupplierName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"请输入供应商信息");
        }
        if(PubUtils.isSEmptyOrNull(cscSupplierInfoDto.getContactName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"供应商联系人名称未填写");
        }
        if(PubUtils.isSEmptyOrNull(cscSupplierInfoDto.getContactPhone())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"供应商联系人电话未填写");
        }
        if(PubUtils.isSEmptyOrNull(cscSupplierInfoDto.getProvinceName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"收货方联系人地址未选择");
        }
        if(PubUtils.isSEmptyOrNull(cscSupplierInfoDto.getCityName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"供应商联系人地址不完整");
        }
        if(PubUtils.isSEmptyOrNull(cscSupplierInfoDto.getAreaName())){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"供应商联系人地址不完整");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);

    }





}
