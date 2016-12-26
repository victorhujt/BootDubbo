package com.xescm.ofc.model.dto.csc;

import com.xescm.csc.model.vo.CscGoodsApiVo;

import java.io.Serializable;

/**
 * Created by wangsongtao on 2016/12/26.
 */
public class OfcGoodsApiVo extends CscGoodsApiVo implements Serializable{

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    private Double goodsAmount;

}
