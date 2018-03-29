package com.xescm.ofc.edas.service.impl;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.edas.model.dto.attachment.AttachmentDto;
import com.xescm.ofc.edas.model.dto.mobile.OfcMobileOrderDto;
import com.xescm.ofc.edas.model.vo.attachment.AttachmentVo;
import com.xescm.ofc.edas.model.vo.mobile.MobileOrderVo;
import com.xescm.ofc.edas.service.OfcMobileOrderEdasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by wangsongtao on 2016/12/25.
 */
@Service
public class OfcMobileOrderEdasServiceImpl implements OfcMobileOrderEdasService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 钉钉易录单保存订单接口
     * @param ofcMobileOrderDto
     * @return
     */
    @Override
    public Wrapper<MobileOrderVo> saveMobileOrder(OfcMobileOrderDto ofcMobileOrderDto) {
        return null;
    }

    /**
     *
     * @param dto 按条件查询订单信息和图片信息
     * @return
     */
    @Override
    public Wrapper<MobileOrderVo> queryMobileOrderByCode(OfcMobileOrderDto dto){
        return null;
    }

    /**
     * 查询拍照订单前20条按时间倒叙
     * @param ofcMobileOrderDto
     * @return
     */
    @Override
    public Wrapper<List<MobileOrderVo>> queryMobileOrderList(OfcMobileOrderDto ofcMobileOrderDto){
        return null;
    }

    /**
     * 保存图片
     * @param attachmentDto
     * @return
     */
    @Override
    public Wrapper<AttachmentVo> saveAttachment(AttachmentDto attachmentDto) {
        return null;
    }

    /**
     * 删除图片
     * @param attachmentDto
     * @return
     */
    @Override
    public Wrapper<?> delAttachment(AttachmentDto attachmentDto) {
        return null;
    }
}
