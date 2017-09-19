package com.xescm.ofc.model.dto.ofc;

import lombok.Data;

/**
 * Created by lyh on 2017/9/13.
 */
@Data
public class OfcUserMsgDTO {
    private String userId;
    private String userName;
    private String userGroupCode;
    private String userGroupName;
    private String groupType;
    private String custName;
    private String custCode;
}
