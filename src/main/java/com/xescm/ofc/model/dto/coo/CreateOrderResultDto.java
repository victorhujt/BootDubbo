package com.xescm.ofc.model.dto.coo;

import lombok.Data;

import java.util.List;

/**
 * 创单返回的dto
 * Created by hiyond on 2016/11/17.
 */
@Data
public class CreateOrderResultDto {

    private String code;

    private String reason;

    private List<MessageDto> message;
}
