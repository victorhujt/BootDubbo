package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcMerchandiser;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.api.csc.FeignCscCustomerAPI;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import com.xescm.ofc.mapper.OfcSiloprogramInfoMapper;
import com.xescm.ofc.service.OfcMerchandiserService;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.utils.PubUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcMerchandiserServiceImpl extends BaseService<OfcMerchandiser> implements OfcMerchandiserService {
}
