package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcPlanFedBackCondition;
import com.xescm.ofc.domain.OfcPlanFedBackResult;
import com.xescm.ofc.domain.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.domain.dto.rmc.RmcCompanyLineQO;
import com.xescm.ofc.domain.dto.rmc.RmcCompanyLineVo;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;

import java.util.List;

/**
 * Created by ydx on 2016/10/12.
 */
public interface OfcPlanFedBackService {
    Wrapper<List<OfcPlanFedBackResult>> planFedBack(OfcPlanFedBackCondition ofcPlanFedBackCondition,AuthResDto authResDtoByToken);
}
