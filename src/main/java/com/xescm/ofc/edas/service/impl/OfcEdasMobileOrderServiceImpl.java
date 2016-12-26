package com.xescm.ofc.edas.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.exception.BusinessException;
import com.xescm.ofc.domain.OfcAttachment;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.edas.model.dto.attachment.AttachmentDto;
import com.xescm.ofc.edas.model.dto.mobile.OfcMobileOrderDto;
import com.xescm.ofc.edas.model.vo.attachment.AttachmentVo;
import com.xescm.ofc.edas.model.vo.mobile.MobileOrderVo;
import com.xescm.ofc.edas.service.OfcEdasMobileOrderService;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
import com.xescm.ofc.service.OfcAttachmentService;
import com.xescm.ofc.service.OfcMobileOrderService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsongtao on 2016/12/25.
 */
@Service
public class OfcEdasMobileOrderServiceImpl implements OfcEdasMobileOrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OfcMobileOrderService ofcMobileOrderService;

    @Autowired
    private OfcAttachmentService ofcAttachmentService;

    @Override
    public Wrapper<MobileOrderVo> saveMobileOrder(OfcMobileOrderDto ofcMobileOrderDto) {
        logger.debug("==>保存拍照录单信息 mobileOrder={}", ofcMobileOrderDto);
        OfcMobileOrder order=new OfcMobileOrder();
        MobileOrderVo resVo = new MobileOrderVo();
        try {
            if(ofcMobileOrderDto == null){
                throw new BusinessException("参数不能为空");
            }
            OfcMobileOrder ofcMobileOrder=new OfcMobileOrder();
            BeanUtils.copyProperties(ofcMobileOrder,ofcMobileOrderDto);
            order=ofcMobileOrderService.saveOfcMobileOrder(ofcMobileOrder);

            BeanUtils.copyProperties(resVo,order);
        } catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.debug("保存拍照录单信息={}", e.getMessage(), e);
            e.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,  e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,resVo);
    }


    @Override
    public Wrapper<MobileOrderVo> queryMobileOrderByCode(OfcMobileOrderDto dto){
        MobileOrderVo resultVo = new MobileOrderVo();
        OfcMobileOrderVo result;
        try {
            if(dto == null){
                throw new BusinessException("参数不能为空");
            }
            if (StringUtils.isBlank(dto.getMobileOrderCode())){
                throw new BusinessException("流水号不能为空!");
            }
            OfcMobileOrder condition=new OfcMobileOrder();
            BeanUtils.copyProperties(condition,dto);
            condition.setMobileOrderCode(dto.getMobileOrderCode());
            result= ofcMobileOrderService.selectOneOfcMobileOrder(condition);
            BeanUtils.copyProperties(resultVo,result);
        } catch (Exception e) {
            logger.error("订单号查询出错：orderCode{},{}",dto.getMobileOrderCode(), e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, resultVo);
    }

    @Override
    public Wrapper<List<MobileOrderVo>> queryMobileOrderList(OfcMobileOrderDto ofcMobileOrderDto){
        logger.debug("==> 查询手机订单前20条");
        List<OfcMobileOrder> list = null;
        List<MobileOrderVo> resList = new ArrayList<>();
        OfcMobileOrder condition=new OfcMobileOrder();
        try {
            BeanUtils.copyProperties(condition,ofcMobileOrderDto);
            list=ofcMobileOrderService.queryOrderNotes(condition);

            for (OfcMobileOrder order:list) {
                MobileOrderVo vo = new MobileOrderVo();
                BeanUtils.copyProperties(vo,order);
                resList.add(vo);
            }
        } catch (Exception e){
            logger.error("查询手机订单出现异常:{},{}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, resList);
    }

    @Override
    public Wrapper<AttachmentVo> saveAttachment(AttachmentDto attachmentDto) {
        OfcAttachment ofcAttachment=new OfcAttachment();
        OfcAttachment result;
        AttachmentVo resultVo = new AttachmentVo();
        try {
            if(attachmentDto == null){
                throw new BusinessException("参数不能为空");
            }
            BeanUtils.copyProperties(ofcAttachment,attachmentDto);
            result =ofcAttachmentService.saveAttachment(ofcAttachment);
            BeanUtils.copyProperties(resultVo,result);
        } catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.debug("保存附件信息={}", e.getMessage(), e);
            e.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,  e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,resultVo);
    }

    @Override
    public Wrapper<?> delAttachment(AttachmentDto attachmentDto) {
        OfcAttachment ofcAttachment=new OfcAttachment();
        try {
            if(attachmentDto == null){
                throw new BusinessException("参数不能为空");
            }
            if(StringUtils.isEmpty(attachmentDto.getSerialNo())){
                throw new BusinessException("附件流水号不能为空");
            }
            logger.info("删除的附件流水号为:{}",attachmentDto.getSerialNo());
            BeanUtils.copyProperties(ofcAttachment,attachmentDto);
            ofcAttachmentService.deleteAttachmentByserialNo(ofcAttachment);
        } catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.debug("删除附件信息={}", e.getMessage(), e);
            e.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,  e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }
}
