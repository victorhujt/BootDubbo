package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcSaveStorageDTO;

/**
 *
 * Created by lyh on 2017/9/13.
 */
public interface OfcCustOrderPlaceService {
    void saveStorageOrder(OfcSaveStorageDTO ofcSaveStorageDTO, String tag, AuthResDto authResDtoByToken) throws Exception;

    String placeTransOrder(AuthResDto authResDtoByToken, OfcOrderDTO ofcOrderDTOStr, String tag);
}
