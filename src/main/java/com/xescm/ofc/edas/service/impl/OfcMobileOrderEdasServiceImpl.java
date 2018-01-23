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
import com.xescm.ofc.edas.service.OfcMobileOrderEdasService;
import com.xescm.ofc.model.vo.ofc.OfcMobileOrderVo;
import com.xescm.ofc.service.OfcAttachmentService;
import com.xescm.ofc.service.OfcMobileOrderService;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by wangsongtao on 2016/12/25.
 */
@Service
public class OfcMobileOrderEdasServiceImpl implements OfcMobileOrderEdasService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OfcMobileOrderService ofcMobileOrderService;

    @Resource
    private OfcAttachmentService ofcAttachmentService;

    /**
     * 钉钉易录单保存订单接口
     * @param ofcMobileOrderDto
     * @return
     */
    @Override
    public Wrapper<MobileOrderVo> saveMobileOrder(OfcMobileOrderDto ofcMobileOrderDto) {
        logger.info("==>保存拍照录单信息 mobileOrder={}", ofcMobileOrderDto);
        OfcMobileOrder order;
        MobileOrderVo resVo = new MobileOrderVo();
        try {
            if(ofcMobileOrderDto == null){
                throw new BusinessException("参数不能为空");
            }
            OfcMobileOrder ofcMobileOrder=new OfcMobileOrder();
            BeanUtils.copyProperties(ofcMobileOrderDto,ofcMobileOrder);
            order = ofcMobileOrderService.saveOfcMobileOrder(ofcMobileOrder);
            BeanUtils.copyProperties(order,resVo);
        } catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.info("保存拍照录单信息={}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,  e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,resVo);
    }

    /**
     *
     * @param dto 按条件查询订单信息和图片信息
     * @return
     */
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
            BeanUtils.copyProperties(dto,condition);
            condition.setMobileOrderCode(dto.getMobileOrderCode());
            result  = ofcMobileOrderService.selectOneOfcMobileOrder(condition);
            BeanUtils.copyProperties(result,resultVo);
        }catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        catch (Exception e) {
            logger.error("订单号查询出错：orderCode{},{}",dto.getMobileOrderCode(), e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, resultVo);
    }

    /**
     * 查询拍照订单前20条按时间倒叙
     * @param ofcMobileOrderDto
     * @return
     */
    @Override
    public Wrapper<List<MobileOrderVo>> queryMobileOrderList(OfcMobileOrderDto ofcMobileOrderDto){
        logger.info("==> 查询手机订单前20条");
        List<OfcMobileOrder> list;
        List<MobileOrderVo> resList = new ArrayList<>();
        OfcMobileOrder condition = new OfcMobileOrder();
        try {
            BeanUtils.copyProperties(ofcMobileOrderDto,condition);
            list=ofcMobileOrderService.queryOrderNotes(condition);

            for (OfcMobileOrder order:list) {
                MobileOrderVo vo = new MobileOrderVo();
                BeanUtils.copyProperties(order,vo);
                resList.add(vo);
            }
        } catch (Exception e){
            logger.error("查询手机订单出现异常:{},{}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, resList);
    }

    /**
     * 保存图片
     * @param attachmentDto
     * @return
     */
    @Override
    public Wrapper<AttachmentVo> saveAttachment(AttachmentDto attachmentDto) {
        OfcAttachment ofcAttachment=new OfcAttachment();
        OfcAttachment result;
        AttachmentVo resultVo = new AttachmentVo();
        try {
            if(attachmentDto == null){
                throw new BusinessException("参数不能为空");
            }
            BeanUtils.copyProperties(attachmentDto,ofcAttachment);
            result =ofcAttachmentService.saveAttachment(ofcAttachment);
            BeanUtils.copyProperties(result,resultVo);

        } catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.info("保存附件信息={}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,  e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,resultVo);
    }

    /**
     * 删除图片
     * @param attachmentDto
     * @return
     */
    @Override
    public Wrapper<?> delAttachment(AttachmentDto attachmentDto) {
        OfcAttachment ofcAttachment = new OfcAttachment();
        try {
            if(attachmentDto == null){
                throw new BusinessException("参数不能为空");
            }
            if(StringUtils.isEmpty(attachmentDto.getSerialNo())){
                throw new BusinessException("附件流水号不能为空");
            }
            logger.info("删除的附件流水号为:{}",attachmentDto.getSerialNo());
            BeanUtils.copyProperties(attachmentDto,ofcAttachment);
            ofcAttachmentService.deleteAttachmentByserialNo(ofcAttachment);
        } catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.info("删除附件信息={}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,  e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }
}
