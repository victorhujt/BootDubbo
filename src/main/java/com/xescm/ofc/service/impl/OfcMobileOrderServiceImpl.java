package com.xescm.ofc.service.impl;


import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcMobileOrderMapper;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CodeGenUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.*;

/**
 * Created by hujintao on 2016/12/12.
 */
@Service
@Transactional
public class OfcMobileOrderServiceImpl extends BaseService<OfcMobileOrder>  implements OfcMobileOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OfcOrderManageServiceImpl.class);

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
                    if(!HASBEENCANCELED.equals(orderStatus.getOrderStatus())){
                        throw  new BusinessException("运输单号重复");
                    }
                }
            }
        }
        ofcMobileOrder.setMobileOrderCode(codeGenUtils.getNewWaterCode("SN",6));
        ofcMobileOrder.setUploadDate(new Date());
        ofcMobileOrder.setOrderType(OrderConstConstant.TRANSPORTORDER);
        ofcMobileOrder.setMobileOrderStatus("0");
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
        OfcMobileOrder order=selectOne(ofcMobileOrder);
        OfcMobileOrderVo vo;
        List<String> urls;
       if(order!=null&& StringUtils.isNotEmpty(order.getSerialNo())){
            String serialNo=order.getSerialNo();
           modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);//严格模式
           vo=modelMapper.map(order, OfcMobileOrderVo.class);
           String[] serialNos=serialNo.split(",");
           if(serialNos.length>0){
            urls=getOssUrls(serialNos);
            vo.setUrls(urls);
           }
       }else{
           throw new BusinessException("没有查询到订单信息!");
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

        ofcFundamentalInformation.setOrderCode(codeGenUtils.getNewWaterCode("SO",6));
        orderCode=ofcFundamentalInformation.getOrderCode();
        // ofcFundamentalInformation.setCustName(authResDtoByToken.getGroupRefName());
        ofcFundamentalInformation.setAbolishMark(ORDERWASNOTABOLISHED);//未作废
        ofcFundamentalInformation.setOrderType(TRANSPORTORDER);
        if(ofcFundamentalInformation.getOrderType().equals(TRANSPORTORDER)){
            Wrapper<?> wrapper =ofcDistributionBasicInfoService.validateDistrictContactMessage(cscContantAndCompanyDtoConsignor, cscContantAndCompanyDtoConsignee);
            if(Wrapper.ERROR_CODE == wrapper.getCode()){
                throw new BusinessException(wrapper.getMessage());
            }

            //运输订单
            if(!PubUtils.isSEmptyOrNull(ofcDistributionBasicInfo.getDeparturePlaceCode()) && ofcDistributionBasicInfo.getDeparturePlaceCode().length() > 12){
                String depatrueCode = ofcDistributionBasicInfo.getDeparturePlaceCode().substring(0,13);
                String destinationCode = ofcDistributionBasicInfo.getDestinationCode().substring(0,13);
                    /*if(depatrueCode.equals(destinationCode)){
                        ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHECITY);
                    }else {
                        ofcFundamentalInformation.setBusinessType(OrderConstEnum.WITHTHETRUNK);
                    }*/
            }else{
                throw new BusinessException("四级地址编码为空!");
            }
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
            notes.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            notes.append(" 订单已创建");
            notes.append(" 操作人: ").append(authResDtoByToken.getUserName());
            notes.append(" 操作单位: ").append(authResDtoByToken.getGroupRefName());
            ofcOrderStatus.setNotes(notes.toString());
            ofcOrderStatus.setOrderCode(ofcFundamentalInformation.getOrderCode());
            ofcOrderStatus.setOrderStatus(PENDINGAUDIT);
            ofcOrderStatus.setStatusDesc("待审核");
            ofcOrderStatus.setLastedOperTime(new Date());
            ofcOrderStatus.setOperator(authResDtoByToken.getUserName());
            ofcOrderStatusService.save(ofcOrderStatus);

            //运输开单自动审核
            String code=ofcOrderManageService.orderAuditByTrans(ofcFundamentalInformation,goodsDetailsList,ofcDistributionBasicInfo,ofcFinanceInformation,ofcOrderStatus.getOrderStatus(),
                    "review",authResDtoByToken);

            if(!"".equals(PubUtils.trimAndNullAsEmpty(code))){
                if(code.equals(Wrapper.SUCCESS_CODE)){
                    return orderCode;
                }
            }
        }else{
            throw new BusinessException("您选择的订单类型系统无法识别!");
        }
        return "";
    }

    private List<String> getOssUrls(String[] serialNos) throws UnsupportedEncodingException {
        List<String> urls=new ArrayList<>();
        for (int i = 0; i <serialNos.length ; i++) {
            OfcAttachment record=new OfcAttachment();
            record.setSerialNo(serialNos[i]);
            OfcAttachment result=ofcAttachmentService.selectOne(record);
            if(result!=null&&!PubUtils.isSEmptyOrNull(result.getPath())&&!PubUtils.isSEmptyOrNull(result.getName())){
                URL url=null;
                //重试5次
                for(int count=0;count<5;count++){
                    url=ofcOssManagerService.getFileURL(result.getPath()+result.getName());//获取图片在oss的路径
                    if(url!=null){
                        break;
                    }
                }
                if(url!=null){
                    String urlStr=url.toString().replace("vpc100-","");
                    if(!urls.contains(urlStr)){
                        urls.add(urlStr);
                    }
                }
            }
        }
        return urls;
    }


}
