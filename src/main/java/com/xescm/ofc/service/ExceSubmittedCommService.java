package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>Title: CscContractService. </p>
 * <p>Description 异常报送通用Service </p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/9/27 11:27
 */
public interface ExceSubmittedCommService {
    /**
     * 上传图片
     * @param request
     */
    String uploadImg(HttpServletRequest request, AuthResDto authResDto) throws IOException;
}
