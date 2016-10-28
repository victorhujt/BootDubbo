package com.xescm.ofc.domain.dto;


import com.xescm.ofc.domain.dto.base.AuthDto;

import java.io.Serializable;

/**
 * Created by wang on 2016/10/28.
 */
public class CscGoodsDto extends AuthDto implements Serializable {
    private static final long serialVersionUID = 626828340488869922L;
    // 货品实体
    private CscGoods cscGoods;

    public CscGoods getCscGoods() {
        return cscGoods;
    }

    public void setCscGoods(CscGoods cscGoods) {
        this.cscGoods = cscGoods;
    }

    @Override
    public String toString() {
        return "CscGoodsDto{" +
                "cscGoods=" + cscGoods +
                '}';
    }
}
