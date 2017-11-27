package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcAttachment;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcAttachmentService;
import com.xescm.ofc.service.OfcOssManagerService;
import com.xescm.ofc.utils.CodeGenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.xescm.ofc.constant.GenCodePreffixConstant.ATTACHMENT_PRE;

/**
 *
 * Created by hujintao on 2016/12/17.
 */
@Service
public class OfcAttachmentServiceImpl extends BaseService<OfcAttachment>  implements OfcAttachmentService {

    @Resource
    private CodeGenUtils codeGenUtils;

    @Resource
    private OfcOssManagerService ofcOssManagerService;

    @Override
    public OfcAttachment saveAttachment(OfcAttachment attachment) {
        attachment.setSerialNo(codeGenUtils.getNewWaterCode(ATTACHMENT_PRE,6));
        save(attachment);
        return attachment;
    }

    @Override
    public void deleteAttachmentByserialNo(OfcAttachment attachment) {
        OfcAttachment result = selectOne(attachment);
        if (result != null) {
            if (StringUtils.isEmpty(result.getPath())||StringUtils.isEmpty(result.getName())) {
                throw new BusinessException("附件文件路径或附件文件名为空");
            } else {
                ofcOssManagerService.deleteFile(result.getPath()+result.getName());
                delete(attachment);
            }
        } else {
            throw new BusinessException("附件不存在");
        }
    }
}
