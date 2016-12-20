package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcAttachment;

/**
 * Created by hujintao on 2016/12/17.
 */
public interface OfcAttachmentService extends IService<OfcAttachment>{

    public OfcAttachment saveAttachment(OfcAttachment attachment);

    public void deleteAttachmentByserialNo(OfcAttachment attachment);
}
