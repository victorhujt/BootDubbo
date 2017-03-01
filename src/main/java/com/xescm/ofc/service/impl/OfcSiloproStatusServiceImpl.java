package com.xescm.ofc.service.impl;

import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcSiloproStatusMapper;
import com.xescm.ofc.model.vo.ofc.OfcTransplanInfoVo;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.*;

/**
 *
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcSiloproStatusServiceImpl extends BaseService<OfcSiloproStatus> implements OfcSiloproStatusService {
	protected Logger logger = LoggerFactory.getLogger(OfcSiloproStatusServiceImpl.class);

    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
	@Resource
	private OfcFundamentalInformationService ofcFundamentalInformationService;









}
