package com.xescm.ofc.service;

import com.github.pagehelper.PageInfo;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.ofc.model.dto.csc.CscGoodsApiDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * Created by lyh on 2017/7/11.
 */
public interface OfcOperCommonService {
    PageInfo<CscGoodsApiVo> queryGoodsByPage(CscGoodsApiDTO cscGoodsApiDto);
    String getFileSuffix(MultipartFile file);
}
