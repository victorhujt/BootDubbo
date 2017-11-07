package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.ofc.domain.ExceSubmitted;
import com.xescm.core.exception.BusinessException;
import com.xescm.ofc.mapper.ExceSubmittedMapper;
import com.xescm.ofc.service.ExceSubmittedService;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.uam.provider.UamGroupEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ExceSubmittedServiceImpl extends BaseService<ExceSubmitted> implements ExceSubmittedService {

    @Resource
    private CodeGenUtils codeGenUtils;
    @Resource
    private UamGroupEdasService uamGroupEdasService;
    @Resource
    private CscCustomerEdasService cscCustomerEdasService;
    @Resource
    private ExceSubmittedMapper exceSubmittedMapper;

    /**
     * <p>Title: exceSubmittedEdit. </p>
     * <p>新增异常报送 </p>
     * @param exceSubmitted,authResDto
     * @author 袁宝龙
     * @CreateDate 2017/11/7 14:16
     */
    @Override
    public void exceSubmittedEdit(ExceSubmitted exceSubmitted, AuthResDto authResDto) {
        String userBaseCode = "";
        String userBaseName = "";
        String userRegionCode = "";
        String userRegionName = "";
        // 当前用户组织流水号，通过流水号查询基地信息
        String groupRefCode = authResDto.getGroupRefCode();
        Wrapper<UamGroupDto> uamBaseGroupDtoWrapper = uamGroupEdasService.queryByGroupSerialNo(groupRefCode);
        UamGroupDto uamBaseGroupDto = uamBaseGroupDtoWrapper.getResult();
        if (PubUtils.isOEmptyOrNull(uamBaseGroupDto)) {
            throw new BusinessException("查询当前登录用户基地信息出错 : 查询到的结果为空或有误");
        }
        String userType = uamBaseGroupDto.getType();
        if ("3".equals(userType)) {
            // 当前用户基地编码
            userBaseCode = uamBaseGroupDto.getGroupCode();
            userBaseName = uamBaseGroupDto.getGroupName();
            if (PubUtils.isSEmptyOrNull(userBaseCode) || PubUtils.isSEmptyOrNull(userBaseName)) {
                throw new BusinessException("当前登录的用户基地信息为空!");
            }
            // [3]为基地，如果此用户为基地用户，再查询下他所属的大区
            Wrapper<UamGroupDto> uamRegionGroupDtoWrapper = uamGroupEdasService.getParentInfoByChildSerilNo(groupRefCode);
            UamGroupDto uamRegionGroupDto = uamRegionGroupDtoWrapper.getResult();
            if (PubUtils.isOEmptyOrNull(uamRegionGroupDto)) {
                throw new BusinessException("查询当前登录用户大区信息出错 : 查询到的结果为空或有误");
            }
            // 当前用户大区编码
            userRegionCode = uamRegionGroupDto.getGroupCode();
            userRegionName = uamRegionGroupDto.getGroupName();
            if (PubUtils.isSEmptyOrNull(userRegionCode) || PubUtils.isSEmptyOrNull(userRegionName)) {
                throw new BusinessException("当前登录的用户大区信息为空!");
            }
        } else if ("1".equals(userType) && !"GD1625000003".equals(groupRefCode)) {
            // [1]为大区，如果此用户为大区用户，并且不为超级管理员，不记录基地
            userRegionCode = uamBaseGroupDto.getGroupCode();
            userRegionName = uamBaseGroupDto.getGroupName();
        } else if ("GD1625000003".equals(groupRefCode)) {
            throw new BusinessException("超级管理员不允许异常录入!");
        } else {
            throw new BusinessException("登录用户身份不允许异常录入!");
        }
        // 查询客户名称
        QueryCustomerCodeDto queryCustomerCodeDto = new QueryCustomerCodeDto();
        queryCustomerCodeDto.setCustomerCode(exceSubmitted.getCustomerCode());
        Wrapper<CscCustomerVo> cscCustomerVo = cscCustomerEdasService.queryCustomerByCustomerCodeOrId(queryCustomerCodeDto);
        if (Wrapper.ERROR_CODE == cscCustomerVo.getCode()) {
            throw new BusinessException(cscCustomerVo.getMessage());
        } else if (null == cscCustomerVo.getResult()) {
            throw new BusinessException("客户中心没有查到该客户!");
        }
        String customerName = cscCustomerVo.getResult().getCustomerName();
        // 保存数据
        exceSubmitted.setCustomerName(customerName);
        Date date = new Date();
        exceSubmitted.setId(UUID.randomUUID().toString().replace("-", ""));
        exceSubmitted.setExceCode(codeGenUtils.getNewWaterCode("Y-EXCE", 3));
        String creatorName = getAuthResDtoByToken().getUserName();
        String creatorId = getAuthResDtoByToken().getUserId();
        exceSubmitted.setCreatorId(creatorId);
        exceSubmitted.setCreator(creatorName);
        exceSubmitted.setLastOperatorId(creatorId);
        exceSubmitted.setLastOperator(creatorName);
        exceSubmitted.setReportPersonCode(creatorId);
        exceSubmitted.setReportPersonName(creatorName);
        exceSubmitted.setReportBaseCode(userBaseCode);
        exceSubmitted.setReportBaseName(userBaseName);
        exceSubmitted.setReportRegionCode(userRegionCode);
        exceSubmitted.setReportRegionName(userRegionName);
        exceSubmitted.setReportTime(date);
        exceSubmitted.setCreatedTime(date);
        exceSubmitted.setUpdateTime(date);
        exceSubmitted.setExceState("1");
        save(exceSubmitted);
    }


    /**
     * <p>Title: queryExceSubmittedById. </p>
     * <p>通过id查询异常报送录入信息 </p>
     *
     * @param id
     * @Author 袁宝龙
     * @CreateDate 2017/11/7 17:55
     * @return ExceSubmitted
     */
    @Override
    public ExceSubmitted queryExceSubmittedById(String id) {
        ExceSubmitted exceSubmitted = exceSubmittedMapper.selectByPrimaryKey(id);
        String reportBaseCode = exceSubmitted.getReportBaseCode();
        String reportRegionName = exceSubmitted.getReportRegionName();
        if (PubUtils.isSEmptyOrNull(reportBaseCode) && !PubUtils.isSEmptyOrNull(reportRegionName)) {
            exceSubmitted.setReportBaseName(reportRegionName);
        }
        return exceSubmitted;
    }
}
