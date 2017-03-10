package com.xescm.ofc.config;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.constant.CreateOrderApiConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.service.*;
import com.xescm.rmc.edas.domain.vo.RmcAddressNameVo;
import com.xescm.rmc.edas.domain.vo.RmcServiceCoverageForOrderVo;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.uam.provider.UamGroupEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xescm.ofc.constant.OrderConstConstant.DING_DING;
import static com.xescm.ofc.constant.OrderConstConstant.WITH_THE_KABAN;
import static com.xescm.ofc.constant.OrderConstant.TRANSPORT_ORDER;

/**
 * Created by lyh on 2017/1/11.
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTask{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcFinanceInformationService ofcFinanceInformationService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private OfcCreateOrderService ofcCreateOrderService;
    @Resource
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Resource
    private UamGroupEdasService uamGroupEdasService;
    @Resource
    private OfcOrderNewstatusService ofcOrderNewstatusService;
    @Resource
    private OfcOrderStatusMapper ofcOrderStatusMapper;

    //推送历史卡班订单到运输中心
//    @Scheduled(cron = "0 */1 * * * ?")
//    @Scheduled(cron = "")
    public void pushHistoryKabanOrderToTfc(){
        logger.info("推送历史卡班订单到运输中心");
        //查询历史卡班订单,出现重复的怎么办? 这些单子运输中心不用再推给DMS
        OfcFundamentalInformation ofcFun = new OfcFundamentalInformation();
        ofcFun.setOrderType(TRANSPORT_ORDER);
        ofcFun.setBusinessType(WITH_THE_KABAN);
        List<OfcFundamentalInformation> ofcFundamentalInformationList = ofcFundamentalInformationService.select(ofcFun);
        logger.info("历史卡班订单总量:{}",ofcFundamentalInformationList.size());
        Integer num = 1;
        for (OfcFundamentalInformation fundamentalInformation : ofcFundamentalInformationList) {
            if(null == fundamentalInformation || PubUtils.isSEmptyOrNull(fundamentalInformation.getOrderCode())){
                logger.error("当前订单数据有误");
                continue;
            }
            logger.info("开始推送第{}条订单,订单编号为:{}",num,fundamentalInformation.getOrderCode());
            String orderCode = fundamentalInformation.getOrderCode();
            OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.queryByOrderCode(orderCode);
            OfcFinanceInformation ofcFinanceInformation = ofcFinanceInformationService.queryByOrderCode(orderCode);
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
            if(null == ofcDistributionBasicInfo || null == ofcFinanceInformation || CollectionUtils.isEmpty(ofcGoodsDetailsInfoList)){
                logger.error("当前订单数据有误,订单号:{}",fundamentalInformation.getOrderCode());
                continue;
            }
            String departurePlaceCode = ofcDistributionBasicInfo.getDeparturePlaceCode();
            String destinationCode = ofcDistributionBasicInfo.getDestinationCode();
            if(PubUtils.isSEmptyOrNull(departurePlaceCode) || PubUtils.isSEmptyOrNull(destinationCode)
                    || departurePlaceCode.split(",").length < 2 || destinationCode.split(",").length < 2){
                logger.error("当前订单数据有误");
                continue;
            }
            ofcOrderManageService.pushOrderToTfc(fundamentalInformation
                    ,ofcFinanceInformation,ofcDistributionBasicInfo,ofcGoodsDetailsInfoList);
            num ++;
        }

    }

    //将历史订单的基地和大区字段补齐
//    @Scheduled(cron = "0 */1 * * * ?")
//    @Scheduled(cron = "0 30 20 12 JAN ?")
   // @Scheduled(fixedDelay = 300000)
    public void fixHistoryOrder(){
        //查询所有订单
        List<OfcFundamentalInformation> ofcFundamentalInformationList = ofcFundamentalInformationService.selectAll();
        int historyOrderNum = ofcFundamentalInformationList.size();
        logger.info("历史订单条数:{}",historyOrderNum);
        int runningNum = 0;
        int successNum = 0;
        int jumpNum = 0;
        for (OfcFundamentalInformation ofcFundamentalInformation : ofcFundamentalInformationList){
            logger.info("历史订单{}的基地和大区字段补齐操作,进度:第{}条,共{}条", ofcFundamentalInformation.getOrderCode(), ++runningNum, historyOrderNum);
            if(!PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseCode()) && !PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getBaseName())
                    && !PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getAreaCode()) && !PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getAreaName())){
                logger.info("历史订单的基地和大区字段补齐操作,当前订单:{}的已经补齐基地大区信息",ofcFundamentalInformation.getOrderCode());
                jumpNum ++;
                continue;
            }
            //根据不同情况
            OfcFundamentalInformation newOfcFundamentalInfomation = new OfcFundamentalInformation();
            if(StringUtils.equals(ofcFundamentalInformation.getCustCode(), CreateOrderApiConstant.XEBEST_CUST_CODE)
                    || StringUtils.equals(ofcFundamentalInformation.getCustCode(), CreateOrderApiConstant.XEBEST_CUST_CODE_TEST)){
                logger.info("鲜易网历史订单{}的基地和大区字段补齐操作",ofcFundamentalInformation.getOrderCode());
                OfcDistributionBasicInfo ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
                ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
                List<OfcDistributionBasicInfo> select = ofcDistributionBasicInfoService.select(ofcDistributionBasicInfo);
                if(null == select || select.size() < 1 || null == select.get(0)){
                    logger.info("鲜易网历史订单的基地和大区字段补齐操作,未查到当前订单:{}的运输基本信息",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                OfcDistributionBasicInfo ofcDistributionBasicInfoResult = select.get(0);
                if(PubUtils.isSEmptyOrNull(ofcDistributionBasicInfoResult.getDepartureProvince())
                        || PubUtils.isSEmptyOrNull(ofcDistributionBasicInfoResult.getDepartureCity())
                        || PubUtils.isSEmptyOrNull(ofcDistributionBasicInfoResult.getDepartureDistrict())){
                    logger.info("鲜易网历史订单的基地和大区字段补齐操作,当前订单:{}的运输基本信息中省市区信息不完整",ofcFundamentalInformation.getOrderCode());
                    continue;
                }

                RmcAddressNameVo rmcAddressNameDto = new RmcAddressNameVo();
                rmcAddressNameDto.setProvinceName(ofcDistributionBasicInfoResult.getDepartureProvince());
                rmcAddressNameDto.setCityName(ofcDistributionBasicInfoResult.getDepartureCity());
                rmcAddressNameDto.setDistrictName(ofcDistributionBasicInfoResult.getDepartureDistrict());
                Map<String, String> addressCodeTemp = ofcCreateOrderService.getAddressCodeTemp(rmcAddressNameDto);
                if(null == addressCodeTemp){
                    logger.info("鲜易网历史订单的基地和大区字段补齐操作,当前订单:{}的运输基本信息中省市区反差区域编码失败",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                StringBuilder departurePlaceCode = new StringBuilder();
                if(!PubUtils.isSEmptyOrNull(addressCodeTemp.get("provinceCode"))){
                    departurePlaceCode.append(addressCodeTemp.get("provinceCode"));
                    if(!PubUtils.isSEmptyOrNull(addressCodeTemp.get("cityCode"))){
                        departurePlaceCode.append(",").append(addressCodeTemp.get("cityCode"));
                        if(!PubUtils.isSEmptyOrNull(addressCodeTemp.get("districtCode"))){
                            departurePlaceCode.append(",").append(addressCodeTemp.get("districtCode"));
                        }
                    }
                }
                OfcDistributionBasicInfo distributionBasicInfo = new OfcDistributionBasicInfo();
                distributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
                distributionBasicInfo.setDeparturePlaceCode(departurePlaceCode.toString());
                ofcDistributionBasicInfoService.updateByOrderCode(distributionBasicInfo);
                RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
                rmcServiceCoverageForOrderVo = ofcOrderManageService.copyDestinationPlace(departurePlaceCode.toString(), rmcServiceCoverageForOrderVo);
                RmcServiceCoverageForOrderVo rmcPickup = ofcOrderManageService.rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo,"Pickup");
                if(null == rmcPickup || PubUtils.isSEmptyOrNull(rmcPickup.getSerialNo()) || PubUtils.isSEmptyOrNull(rmcPickup.getBaseName())){
                    logger.info("鲜易网历史订单的基地和大区字段补齐操作,未查到当前订单:{}的区域覆盖信息,或信息不完整",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                newOfcFundamentalInfomation.setOrderCode(ofcFundamentalInformation.getOrderCode());
                newOfcFundamentalInfomation.setBaseCode(rmcPickup.getSerialNo());
                newOfcFundamentalInfomation.setBaseName(rmcPickup.getBaseName());
                UamGroupDto uamGroupDto = new UamGroupDto();
                uamGroupDto.setSerialNo(rmcPickup.getSerialNo());
                OfcGroupVo ofcGroupVo = ofcOrderManageOperService.queryAreaMsgByBase(uamGroupDto);
                if(null == ofcGroupVo || PubUtils.isSEmptyOrNull(ofcGroupVo.getSerialNo()) || PubUtils.isSEmptyOrNull(ofcGroupVo.getGroupName())){
                    logger.info("鲜易网历史订单的基地和大区字段补齐操作,未查到当前订单:{}的区域覆盖信息,或信息不完整",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                newOfcFundamentalInfomation.setAreaCode(ofcGroupVo.getSerialNo());
                newOfcFundamentalInfomation.setAreaName(ofcGroupVo.getGroupName());
                int num = ofcFundamentalInformationService.update(newOfcFundamentalInfomation);
                if(num < 1){
                    logger.info("鲜易网历史订单的基地和大区字段补齐操作,当前订单:{}的更新失败!",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                logger.info("鲜易网历史订单的基地和大区字段补齐操作,当前订单:{}的更新成功!",ofcFundamentalInformation.getOrderCode());
                successNum ++;
                //钉钉录单
            }else if(org.apache.commons.lang.StringUtils.equals(ofcFundamentalInformation.getOrderSource(),DING_DING)){
                logger.info("钉钉录单历史订单{}的基地和大区字段补齐操作",ofcFundamentalInformation.getOrderCode());
                OfcDistributionBasicInfo ofcDistributionBasicInfo = new OfcDistributionBasicInfo();
                ofcDistributionBasicInfo.setOrderCode(ofcFundamentalInformation.getOrderCode());
                List<OfcDistributionBasicInfo> select = ofcDistributionBasicInfoService.select(ofcDistributionBasicInfo);
                if(null == select || select.size() < 1){
                    logger.info("钉钉录单历史订单的基地和大区字段补齐操作,未查到当前订单:{}的运输基本信息",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                OfcDistributionBasicInfo ofcDistributionBasicInfoResult = select.get(0);
                RmcServiceCoverageForOrderVo rmcServiceCoverageForOrderVo = new RmcServiceCoverageForOrderVo();
                rmcServiceCoverageForOrderVo = ofcOrderManageService.copyDestinationPlace(ofcDistributionBasicInfoResult.getDeparturePlaceCode(), rmcServiceCoverageForOrderVo);
                RmcServiceCoverageForOrderVo rmcPickup = ofcOrderManageService.rmcServiceCoverageAPI(rmcServiceCoverageForOrderVo,"Pickup");
                if(null == rmcPickup || PubUtils.isSEmptyOrNull(rmcPickup.getSerialNo()) || PubUtils.isSEmptyOrNull(rmcPickup.getBaseName())){
                    logger.info("钉钉录单历史订单的基地和大区字段补齐操作,未查到当前订单:{}的区域覆盖信息,或信息不完整",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                newOfcFundamentalInfomation.setOrderCode(ofcFundamentalInformation.getOrderCode());
                newOfcFundamentalInfomation.setBaseCode(rmcPickup.getSerialNo());
                newOfcFundamentalInfomation.setBaseName(rmcPickup.getBaseName());
                UamGroupDto uamGroupDto = new UamGroupDto();
                uamGroupDto.setSerialNo(rmcPickup.getSerialNo());
                OfcGroupVo ofcGroupVo = ofcOrderManageOperService.queryAreaMsgByBase(uamGroupDto);
                if(null == ofcGroupVo || PubUtils.isSEmptyOrNull(ofcGroupVo.getSerialNo()) || PubUtils.isSEmptyOrNull(ofcGroupVo.getGroupName())){
                    logger.info("钉钉录单历史订单的基地和大区字段补齐操作,未查到当前订单:{}的区域覆盖信息,或信息不完整",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                newOfcFundamentalInfomation.setAreaCode(ofcGroupVo.getSerialNo());
                newOfcFundamentalInfomation.setAreaName(ofcGroupVo.getGroupName());
                int num = ofcFundamentalInformationService.update(newOfcFundamentalInfomation);
                if(num < 1){
                    logger.info("钉钉录单历史订单的基地和大区字段补齐操作,当前订单:{}的更新失败!",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                logger.info("钉钉录单历史订单的基地和大区字段补齐操作,当前订单:{}的更新成功!",ofcFundamentalInformation.getOrderCode());
                successNum ++;
                //普通手录订单
            }else{
                logger.info("普通手录历史订单的基地和大区字段补齐操作");
                String userId = ofcFundamentalInformation.getCreator();
                if(PubUtils.isSEmptyOrNull(userId)){
                    logger.info("普通手录历史订单的基地和大区字段补齐操作,当前订单没有创建人id");
                    continue;
                }
                Wrapper<UamGroupDto> groupInfoByUserId = uamGroupEdasService.findGroupInfoByUserId(userId);
                if(null == groupInfoByUserId || Wrapper.ERROR_CODE == groupInfoByUserId.getCode() || null == groupInfoByUserId.getResult()){
                    logger.info("普通手录历史订单的基地和大区字段补齐操作,当前订单创建人没有组织信息, 或组织信息查询出错!");
                    continue;
                }
                String groupSerialNo = groupInfoByUserId.getResult().getSerialNo();
                if(PubUtils.isSEmptyOrNull(groupSerialNo)){
                    logger.info("普通手录历史订单的基地和大区字段补齐操作,查询订单创建人用户信息, 该用户没有流水号");
                    continue;
                }
                AuthResDto authResDto = new AuthResDto();
                authResDto.setGroupRefCode(groupSerialNo);
                OfcFundamentalInformation updateAreaAndBaseMsg = ofcOrderPlaceService.getAreaAndBaseMsg(authResDto, ofcFundamentalInformation);
                int update = ofcFundamentalInformationService.update(updateAreaAndBaseMsg);
                if(update < 1){
                    logger.info("普通手录历史订单的基地和大区字段补齐操作,当前订单:{}的更新失败!",ofcFundamentalInformation.getOrderCode());
                    continue;
                }
                logger.info("普通手录历史订单的基地和大区字段补齐操作,当前订单:{}的更新成功!",ofcFundamentalInformation.getOrderCode());
                successNum ++;
            }
        }
        logger.info("历史订单的基地和大区字段补齐,共{}条,成功{}条,失败条数中包含跳过更新{}条", historyOrderNum, successNum, jumpNum);

    }

    //同步历史订单最新状态到订单最新状态表
    //@Scheduled(cron = "0 */1 * * * ?")//一分鐘執行一次
    @Scheduled(cron = "0 35 16 9 MAR ?")//定時執行一次
//    @Scheduled(cron = "")
    public void pushOrderNewStatusToNewTable(){
        logger.info("开始,同步历史订单最新状态到订单最新状态表");
        List<OfcFundamentalInformation> ofcFundamentalInformations = ofcFundamentalInformationService.selectAll();
        OfcOrderNewstatus orderNewstatus=new OfcOrderNewstatus();
        Map<String, String> mapperMap = new HashMap<>();
        for (OfcFundamentalInformation ofcFundamentalInformation : ofcFundamentalInformations) {
            String orderCode = ofcFundamentalInformation.getOrderCode();
            orderNewstatus.setOrderCode(orderCode);
            if (null == ofcOrderNewstatusService.selectByKey(orderCode)) {
                mapperMap.put("orderCode", orderCode);
                OfcOrderStatus ofcOrderStatus = ofcOrderStatusMapper.orderStatusSelect(mapperMap);
                if(null!=ofcOrderStatus){
                    OfcOrderNewstatus orderNewstatu = new OfcOrderNewstatus();
                    orderNewstatu.setOrderCode(orderCode);
                    orderNewstatu.setOrderLatestStatus(PubUtils.trimAndNullAsEmpty(ofcOrderStatus.getOrderStatus()));
                    orderNewstatu.setStatusUpdateTime(new Date());
                    orderNewstatu.setStatusCreateTime(new Date());
                    ofcOrderNewstatusService.save(orderNewstatu);
                }
            }
        }
    }
}
