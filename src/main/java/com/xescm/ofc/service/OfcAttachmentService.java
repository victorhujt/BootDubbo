package com.xescm.ofc.service;

import com.xescm.uam.domain.dto.AuthResDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by hujintao on 2016/12/12.
 */
public interface OfcAttachmentService {

    public  String uploadFile(MultipartHttpServletRequest multipartRequest, AuthResDto authResDto);
}
