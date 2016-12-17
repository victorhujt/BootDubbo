package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcAttachment;
import com.xescm.ofc.mapper.OfcAttachmentMapper;
import com.xescm.ofc.service.OfcAttachmentService;
import com.xescm.ofc.utils.CodeGenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hujintao on 2016/12/17.
 */
@Service
public class OfcAttachmentServiceImpl extends BaseService<OfcAttachment>  implements OfcAttachmentService {

    @Autowired
    private OfcAttachmentMapper ofcAttachmentMapper;

    @Autowired
    private CodeGenUtils codeGenUtils;

    @Override
    public OfcAttachment saveAttachment(OfcAttachment attachment) {
        attachment.setSerialNo(codeGenUtils.getNewWaterCode("AT",6));
        save(attachment);
        return attachment;
    }
}
