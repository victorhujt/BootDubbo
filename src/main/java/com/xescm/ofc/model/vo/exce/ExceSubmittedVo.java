package com.xescm.ofc.model.vo.exce;

import com.xescm.ofc.domain.ExceSubmitted;
import lombok.Data;

/**
 * <p>Title: ExceSubmittedVo. </p>
 * <p>Description 异常报送VO </p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/11/7
 */
@Data
public class ExceSubmittedVo {
    /**
     * 附件流水号
     */
    private String imgSerialNo;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件存储相对路径
     */
    private String path;

    /**
     * 客户名称 用于回显select2
     */
    private String customerName;

    private ExceSubmitted exceSubmitted;
}
