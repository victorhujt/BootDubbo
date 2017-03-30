package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcDailyAccount;
import com.xescm.ofc.service.OfcDailyAccountsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hujintao on 2017/3/29.
 */
@Service
@Transactional
public class OfcDailyAccountsServiceImpl  extends BaseService<OfcDailyAccount> implements OfcDailyAccountsService {
}
