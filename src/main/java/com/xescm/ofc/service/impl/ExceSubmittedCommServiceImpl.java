package com.xescm.ofc.service.impl;


import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.ImageCompressUtil;
import com.xescm.ofc.domain.ExceSubmitted;
import com.xescm.ofc.domain.OfcAttachment;
import com.xescm.ofc.enums.OssFileUrlEnum;
import com.xescm.ofc.service.ExceSubmittedCommService;
import com.xescm.ofc.service.OfcAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@Transactional
public class ExceSubmittedCommServiceImpl extends BaseService<ExceSubmitted> implements ExceSubmittedCommService {

    @Autowired
    private OfcAttachmentService ofcAttachmentService;

    @Override
    public String uploadImg(HttpServletRequest request, AuthResDto authResDto) throws IOException {
        try {
            MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
            MultipartFile imgFile = multipartRequest.getFile("img_1");
            String fileName = null;
            OfcAttachment ofcAttachment = null;
            long maxSize = 5 * 1024 * 1024;
            if(imgFile != null){
                if(imgFile.getSize() > maxSize){
                    throw new BusinessException("上传图片不能大于5M");
                }
                fileName = imgFile.getOriginalFilename();
                if(StringUtils.hasText(fileName)){
                    ofcAttachment = uploadFile(ImageCompressUtil.compress(imgFile.getInputStream(), 480, 320, false), fileName, OssFileUrlEnum.EXCE_SUBMITTED_IMG.getUrl());
                    if(ofcAttachment != null){
                        ofcAttachment.setFormat("异常报送附件");
                        // 添加创建人id,姓名，创建时间
                        ofcAttachment.setCreator(authResDto.getUserName());
                        ofcAttachment.setCreatorId(authResDto.getUserId());
                        // 最后修改人信息以及最后修改时间
                        ofcAttachment.setLastOperator(authResDto.getUserName());
                        ofcAttachment.setLastOperatorId(authResDto.getUserId());
                        ofcAttachmentService.saveAttachment(ofcAttachment);
                        return ofcAttachment.getSerialNo();
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }
}
