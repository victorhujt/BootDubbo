package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.ofc.domain.ExceSubmitted;

/**
 * <p>Title: CscContractService. </p>
 * <p>Description 异常报送业务Service </p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/9/27 11:27
 */
public interface ExceSubmittedService {
    // 新增异常报送
    void exceSubmittedEdit(ExceSubmitted exceSubmitted, AuthResDto authResDto);
    // 通过id查询异常报送录入信息
    ExceSubmitted queryExceSubmittedById(String id);
}
