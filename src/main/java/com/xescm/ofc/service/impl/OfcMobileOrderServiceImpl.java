package com.xescm.ofc.service.impl;


import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.constant.GenCodePreffixConstant;
import com.xescm.ofc.constant.OrderConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcMobileOrderMapper;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.REVIEW;

/**
 *
 * Created by hujintao on 2016/12/12.
 */
@Service
@Transactional
public class OfcMobileOrderServiceImpl extends BaseService<OfcMobileOrder>  implements OfcMobileOrderService {


    @Resource
    private OfcOssManagerService ofcOssManagerService;
    @Resource
    private OfcMobileOrderMapper ofcMobileOrderMapper;
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private OfcMerchandiserService ofcMerchandiserService;
    @Resource
    private CodeGenUtils codeGenUtils;
    @Resource
    private OfcAttachmentService ofcAttachmentService;

    private ModelMapper modelMapper = new ModelMapper();

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public OfcMobileOrder saveOfcMobileOrder(OfcMobileOrder ofcMobileOrder) {
        //如果运输单号存在，校验运输单号是否重复
        if(StringUtils.isNotEmpty(ofcMobileOrder.getTranCode())){
            OfcMobileOrder condition=new OfcMobileOrder();
            condition.setTranCode(ofcMobileOrder.getTranCode());
            OfcMobileOrder order=selectOne(condition);
            if(order!=null&&StringUtils.isNotEmpty(order.getOrderCode())){
                OfcOrderStatus orderStatus=ofcOrderStatusService.orderStatusSelect(order.getOrderCode(),"orderCode");
                if(orderStatus!=null){
                    if(!HASBEEN_CANCELED.equals(orderStatus.getOrderStatus())){
                        throw  new BusinessException("运输单号重复");
                    }
                }
            }
        }
        ofcMobileOrder.setMobileOrderCode(codeGenUtils.getNewWaterCode(GenCodePreffixConstant.MOBILE_PRE,6));
        ofcMobileOrder.setUploadDate(new Date());
        ofcMobileOrder.setOrderType(OrderConstant.TRANSPORT_ORDER);
        ofcMobileOrder.setMobileOrderStatus(UN_TREATED);
        save(ofcMobileOrder);
        return ofcMobileOrder;
    }

    @Override
    public List<OfcMobileOrder> queryOrderNotes(OfcMobileOrder ofcMobileOrder) {
        return ofcMobileOrderMapper.queryOrderNotes(ofcMobileOrder);
    }

    @Override
    public List<OfcMobileOrder> queryOrderList(MobileOrderOperForm form) {
        return ofcMobileOrderMapper.queryOrderList(form);
    }

    @Override
    public OfcMobileOrderVo selectOneOfcMobileOrder(OfcMobileOrder ofcMobileOrder) throws UnsupportedEncodingException {
        OfcMobileOrder order = super.selectOne(ofcMobileOrder);
        OfcMobileOrderVo vo;
        List<String> urls;
       if(order != null && StringUtils.isNotEmpty(order.getSerialNo())){
           String serialNo = order.getSerialNo();
           modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);//严格模式
           vo = modelMapper.map(order, OfcMobileOrderVo.class);
           String[] serialNos = serialNo.split(",");
           if(serialNos.length > 0){
            urls = this.getOssUrls(serialNos);
            vo.setUrls(urls);
           }
       }else{
           throw new BusinessException("已经没有未受理订单!");
       }
        return vo;
    }

    @Override
    public int updateByMobileCode(OfcMobileOrder mobileOrder) {
        return ofcMobileOrderMapper.updateByMobileCode(mobileOrder);
    }

    @Override
    public String placeOrder(OfcOrderDTO ofcOrderDTO, List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos,AuthResDto authResDtoByToken, String custId
            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignor
            , CscContantAndCompanyDto cscContantAndCompanyDtoConsignee, CscSupplierInfoDto cscSupplierInfoDto) {
        String orderCode;
        List<OfcGoodsDetailsInfo> goodsDetailsList=new ArrayList<>();
        OfcFinanceInformation  ofcFinanceInformation =modelMapper.map(ofcOrderDTO, OfcFinanceInformation.class);
        OfcFundamentalInformation ofcFundamentalInformation = modelMapper.map(ofcOrderDTO, OfcFundamentalInformation.class);
        OfcDistributionBasicInfo ofcDistributionBasicInfo = modelMapper.map(ofcOrderDTO, OfcDistributionBasicInfo.class);
        OfcMerchandiser ofcMerchandiser=modelMapper.map(ofcOrderDTO,OfcMerchandiser.class);
        ofcFundamentalInformation.setCreationTime(new Date());
        ofcFundamentalInformation.setCreator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setCreatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOperator(authResDtoByToken.getUserId());
        ofcFundamentalInformation.setOperatorName(authResDtoByToken.getUserName());
        ofcFundamentalInformation.setOperTime(new Date());
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        StringBuilder notes = new StringBuilder();
        //ofcFundamentalInformation.setStoreCode(ofcOrderDTO.getStoreName());//店铺还没维护表
        ofcFundamentalInformation.setStoreName(ofcOrderDTO.getStoreName());//店铺还没维护表
        ofcFundamentalInformation.setOrderSource(DING_DING);//订单来源

        //校验运输单号是否重复
        if(!"".equals(PubUtils.trimAndNullAsEmpty(ofcDistributionBasicInfo.getTransCode()))){
            int orderCodeByTransCode = ofcDistributionBasicInfoService.checkTransCode(ofcDistributionBasicInfo);
            if(orderCodeByTransCode>=1){
                throw new BusinessException("该运输单号号已经存在!您不能重复下单!");
            }
        }

        String orderType = OrderConstant.TRANSPORT_ORDER;
        ofcFundamentalInformation.setOrderCode(codeGenUtils.getNewWaterCode(GenCodePreffixConstant.ORDER_PRE,6));
        orderCode=ofcFundamentalInformation.getOrderCode();
        // ofcFundamentalInformation.setCustName(authResDtoByToken.getGroupRefName());
        ofcFundamentalInformation.setAbolishMark(ORDER_WASNOT_ABOLISHED);//未作废
        ofcFundamentalInformation.setOrderType(orderType);
        if(ofcFundamentalInformation.getOrderType().equals(orderType)){
            Wrapper<?> wrapper =ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                throw new BusinessException(wrapper.getMessage());
            }

            //运输订单
            if (PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDeparturePlaceCode()) || ofcDistributionBasicInfo.getDeparturePlaceCode().length() <= 12) {
                throw new BusinessException("四级地址编码为空!");
            } /*else {
                //String depatrueCode = ofcDistributionBasicInfo.getDeparturePlaceCode().substring(0,13);
                //String destinationCode = ofcDistributionBasicInfo.getDestinationCode().substring(0,13);
                    *//*if(depatrueCode.equals(destinationCode)){
                        ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITH_THE_CITY);
                    }else {
                        ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITH_THE_TRUNK);
                    }*//*
            }*/
            try {
                //添加基本信息
                ofcFundamentalInformationService.save(ofcFundamentalInformation);
            } catch (Exception ex) {
                if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                    throw new BusinessException("获取订单号发生重复，导致保存计划单基本信息发生错误！");
                } else {
                    throw new BusinessException("保存计划单信息发生错误！", ex);
                }
            }

            //保存财务信息
            ofcFinanceInformation.setCreationTime(ofcFundamentalInformation.getCreationTime());
            ofcFinanceInformation.setCreator(ofcFundamentalInformation.getCreator());
            ofcFinanceInformation.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcFinanceInformation.setOperator(ofcFundamentalInformation.getOperator());
            ofcFinanceInformation.setOperTime(ofcFundamentalInformation.getOperTime());
            ofcFinanceInformationService.save(ofcFinanceInformation);

            //保存运输信息
            ofcDistributionBasicInfo.setCreationTime(ofcFundamentalInformation.getCreationTime());
            ofcDistributionBasicInfo.setCreator(ofcFundamentalInformation.getCreator());
            ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcDistributionBasicInfo.setOperator(ofcFundamentalInformation.getOperator());
            ofcDistributionBasicInfo.setOperTime(ofcFundamentalInformation.getOperTime());
            ofcDistributionBasicInfoService.save(ofcDistributionBasicInfo);

            //添加该订单的货品信息
            for(OfcGoodsDetailsInfo ofcGoodsDetails : ofcGoodsDetailsInfos){
                ofcGoodsDetails.setOrderCode( ofcFundamentalInformation.getOrderCode());
                ofcGoodsDetails.setCreationTime(ofcFundamentalInformation.getCreationTime());
                ofcGoodsDetails.setCreator(ofcFundamentalInformation.getCreator());
                ofcGoodsDetails.setOperator(ofcFundamentalInformation.getOperator());
                ofcGoodsDetails.setOperTime(ofcFundamentalInformation.getOperTime());
                ofcGoodsDetailsInfoService.save(ofcGoodsDetails);
                goodsDetailsList.add(ofcGoodsDetails);
            }

            if(ofcMerchandiserService.select(ofcMerchandiser).size()==0){
                ofcMerchandiserService.save(ofcMerchandiser);
            }

            //保存订单状态日志
            notes.append(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
            notes.append(" 订单已创建");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            ofcOrderStatus.setNotes(notes.toString());
            ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcOrderStatus.setOrderStatus(PENDING_AUDIT);
            ofcOrderStatus.setStatusDesc("待审核");
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
            ofcOrderStatusService.save(ofcOrderStatus);

            //运输开单自动审核
            String code = ofcOrderManageService.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetailsInfos, ofcDistributionBasicInfo,
                    null, ofcFinanceInformation, ofcOrderStatus.getOrderStatus(), REVIEW, authResDtoByToken);

            if(!PubUtils.isSEmptyOrNull(code) && code.equals(String.valueOf(Wrapper.SUCCESS_CODE))){
                return orderCode;
            }
        }else{
            throw new BusinessException("您选择的订单类型系统无法识别!");
        }
        return "";
    }

    private List<String> getOssUrls(String[] serialNos) throws UnsupportedEncodingException {
        List<String> urls=new ArrayList<>();
        for (String serialNo : serialNos) {
            OfcAttachment record = new OfcAttachment();
            record.setSerialNo(serialNo);
            OfcAttachment result = ofcAttachmentService.selectOne(record);
            if (result != null && !PubUtils.isSEmptyOrNull(result.getPath()) && !PubUtils.isSEmptyOrNull(result.getName())) {
                URL url = null;
                //重试5次
                for (int count = 0; count < 5; count++) {
                    url = ofcOssManagerService.getFileURL(result.getPath() + result.getName());//获取图片在oss的路径
                    if (url != null) {
                        break;
                    }
                }
                if (url != null) {
                    String urlStr = url.toString().replace("vpc100-", "");
                    if (!urls.contains(urlStr)) {
                        urls.add(urlStr);
                    }
                }
            }
        }
        return urls;
    }

    /**
     * 将待受理订单号放入缓存
     * @param orderCode 订单号
     */
    public void pushOrderToCache(String orderCode) {
        String key = "MobilePendingOrderList";
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.rightPush(key, orderCode);
    }

    @Override
    public void dealDingdingOrder() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -5);
        Date time = now.getTime();
        OfcMobileOrder ofcMobileOrder = new OfcMobileOrder();
        ofcMobileOrder.setAppcetDate(time);
        ofcMobileOrder.setMobileOrderStatus(TREATING);
        logger.info("ofcMobileOrder :{}", ofcMobileOrder);
        List<OfcMobileOrder> mobileOrders = ofcMobileOrderMapper.queryNeedDeal(ofcMobileOrder);
        if(mobileOrders.size() < 1){
            logger.info("没有待处理订单...");
            return;
        }
        for (OfcMobileOrder mobileOrder : mobileOrders) {
            String mobileOrderCode = mobileOrder.getMobileOrderCode();
            mobileOrder.setMobileOrderStatus(UN_TREATED);
            int update = ofcMobileOrderMapper.updateByMobileCode(mobileOrder);
            if(update < 1){
                logger.error("钉钉录单重新更新为未处理失败! 订单号: {}", mobileOrderCode);
                continue;
            }
            this.pushOrderToCache(mobileOrderCode);
        }
    }

    /**
     * 删除未受理的手机订单
     * @param mobileOrderCode 手机订单号
     */
    @Override
    public void deleteMobileOrder(String mobileOrderCode) throws UnsupportedEncodingException {
        OfcMobileOrder ofcMobileOrder=new OfcMobileOrder();
        ofcMobileOrder.setMobileOrderCode(mobileOrderCode);
        try{
            OfcMobileOrderVo mobileOrder = selectOneOfcMobileOrder(ofcMobileOrder);
            String acceptStatus=mobileOrder.getMobileOrderStatus();
            String status = UN_TREATED.equals(acceptStatus) ? "未受理" : TREATED.equals(acceptStatus)
                    ? "已受理" : TREATING.equals(acceptStatus) ? "受理中" : acceptStatus;
           if(TREATED.equals(acceptStatus)||TREATING.equals(acceptStatus)){
               throw new BusinessException("订单号:"+mobileOrderCode+"状态为【"+status+"】不可进行删除!");
           }
           //手机订单对应的图片路径
          List<String> urls=mobileOrder.getUrls();
           //删除OSS上面的图片
           if(!CollectionUtils.isEmpty(urls)){
               for (String url :urls) {
                   ofcOssManagerService.deleteFile(url);
               }
           }
           String serialNo=mobileOrder.getSerialNo();
          if(PubUtils.isSEmptyOrNull(serialNo)){
              //删除手机订单号对应的附件
              String[] serialNos=serialNo.split(",");
              for(int i=0;i<serialNos.length;i++){
                  ofcAttachmentService.deleteByKey(serialNos[i]);
              }
          }
        }catch (Exception e) {
            throw e;
        }
    }

    /**
     * 从缓存中获取待受理订单
     * @return 订单号
     */
    public String getOrderFromCache() {
        String orderCode = null;
        String key = "MobilePendingOrderList";
        boolean existList = redisTemplate.hasKey(key);
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        if (existList && listOps.size(key) > 0) {
            orderCode = listOps.leftPop(key);
        } else {
            logger.info("不存在待受理订单！");
        }
        return orderCode;
    }

    /**
     * 自动获取待受理订单
     * @return
     */
    @Transactional
    public OfcMobileOrderVo autoAcceptPendingOrder(String user) {
        OfcMobileOrderVo mobileOrderVo = null;
        // 查询待受理订单
        String mobileOrderCode = this.getOrderFromCache();
        if (!PubUtils.isOEmptyOrNull(mobileOrderCode)) {
            OfcMobileOrder params = new OfcMobileOrder();
            params.setMobileOrderCode(mobileOrderCode);
            try {
                // 查询易录单信息
                mobileOrderVo = this.selectOneOfcMobileOrder(params);
                String acceptStatus = mobileOrderVo.getMobileOrderStatus();
                // 判断是否受理 - 待受理
                if (UN_TREATED.equals(acceptStatus)) {
                    // 更新受理状态
                    params.setMobileOrderCode(mobileOrderCode);
                    params.setAccepter(user);
                    params.setAppcetDate(new Date());
                    params.setMobileOrderStatus(TREATING);
                    int line = this.updateByMobileCode(params);
                    if (line <= 0) {
                        this.pushOrderToCache(mobileOrderCode);
                        logger.info("更新拍照开单受理信息错误，订单重新缓存到未受理状态！");
                    }
                } else { // 受理中、已受理, 重新获取新订单
                    String status = UN_TREATED.equals(acceptStatus) ? "未受理" : TREATED.equals(acceptStatus)
                        ? "已受理" : TREATING.equals(acceptStatus) ? "受理中" : acceptStatus;
                    logger.info("订单【"+mobileOrderVo.getMobileOrderCode()+"】当前状态为【"+status+"】,重新获取订单！");
                    // 订单已经受理，重新获取
                    while (true) {
                        mobileOrderVo = this.autoAcceptPendingOrder(user);
                        if (mobileOrderVo != null) {
                            return mobileOrderVo;
                        }
                    }
                }

            } catch (UnsupportedEncodingException e) {
                this.pushOrderToCache(mobileOrderCode);
                throw new BusinessException("获取照片发生错误！");
            } catch (BusinessException e) {
                this.pushOrderToCache(mobileOrderCode);
                throw e;
            } catch (Exception e) {
                this.pushOrderToCache(mobileOrderCode);
                throw e;
            }
        } else {
            throw new BusinessException("不存在待受理订单！");
        }

        return mobileOrderVo;
    }
}
