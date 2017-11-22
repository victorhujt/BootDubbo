package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.csc.model.dto.QueryCustomerCodeDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.ofc.domain.ExceSubmitted;
import com.xescm.core.exception.BusinessException;
import com.xescm.ofc.domain.OfcFinanceInformation;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.mapper.ExceSubmittedMapper;
import com.xescm.ofc.model.dto.exce.ExceSubmittedQueryDto;
import com.xescm.ofc.model.vo.exce.ExceSubmittedVo;
import com.xescm.ofc.service.ExceSubmittedService;
import com.xescm.ofc.service.OfcFinanceInformationService;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.uam.provider.UamGroupEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private ExceSubmittedMapper exceSubmittedMapper;

    /**
     * <p>Title: checkOrderCodeExists. </p>
     * <p>校验新增异常报送中，订单号是否存在 </p>
     *
     * @param
     * @Author 袁宝龙
     * @CreateDate 2017/11/9 15:13
     * @return
     */
    @Override
    public int checkOrderCodeExists(String orderCode) {
        OfcFundamentalInformation record = new OfcFundamentalInformation();
        record.setOrderCode(orderCode);
        return ofcFundamentalInformationService.selectCount(record);
    }

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
            userBaseCode = uamBaseGroupDto.getSerialNo();
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
            userRegionCode = uamRegionGroupDto.getSerialNo();
            userRegionName = uamRegionGroupDto.getGroupName();
            if (PubUtils.isSEmptyOrNull(userRegionCode) || PubUtils.isSEmptyOrNull(userRegionName)) {
                throw new BusinessException("当前登录的用户大区信息为空!");
            }
        } else if ("1".equals(userType) && !"GD1625000003".equals(groupRefCode)) {
            // [1]为大区，如果此用户为大区用户，并且不为超级管理员，不记录基地
            userRegionCode = uamBaseGroupDto.getSerialNo();
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
        exceSubmitted.setExceCode(codeGenUtils.getNewWaterCode("Y", 3));
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

    @Override
    public void exceSubmittedCancel(String id) {
        AuthResDto authdto = getAuthResDtoByToken();
        ExceSubmitted exceSubmitted = new ExceSubmitted();
        exceSubmitted.setId(id);
        exceSubmitted.setExceState("0");
        exceSubmitted.setLastOperatorId(authdto.getUserId());
        exceSubmitted.setLastOperator(authdto.getUserName());
        exceSubmitted.setUpdateTime(new Date());
        super.update(exceSubmitted);
    }

    @Override
    public void exceHandle(ExceSubmitted exceSubmitted) {
        AuthResDto authdto = getAuthResDtoByToken();
        exceSubmitted.setDealingPersonCode(authdto.getUserId());
        exceSubmitted.setDealingPersonName(authdto.getUserName());
        exceSubmitted.setDealingTime(new Date());
        exceSubmitted.setExceState("2");
        exceSubmitted.setLastOperatorId(authdto.getUserId());
        exceSubmitted.setLastOperator(authdto.getUserName());
        exceSubmitted.setUpdateTime(new Date());
        super.update(exceSubmitted);
    }

    /**
     * <p>Title: queryExceSubmittedList. </p>
     * <p>分页查询异常报送信息列表 </p>
     * <p>校验登录人权限 </p>
     * @param
     * @Author 袁宝龙
     * @CreateDate 2017/11/8 11:41
     * @return
     */
    @Override
    public List<ExceSubmittedVo> queryExceSubmittedList(ExceSubmittedQueryDto exceSubmittedQueryDto) {
        // 校验权限
        AuthResDto authResDto = getAuthResDtoByToken();
        // 当前用户组织流水号，通过流水号查询基地信息
        String groupRefCode = authResDto.getGroupRefCode();
        Wrapper<UamGroupDto> uamBaseGroupDtoWrapper = uamGroupEdasService.queryByGroupSerialNo(groupRefCode);
        UamGroupDto uamBaseGroupDto = uamBaseGroupDtoWrapper.getResult();
        if (PubUtils.isOEmptyOrNull(uamBaseGroupDto)) {
            throw new BusinessException("查询当前登录用户基地信息出错:查询到的结果为空或有误");
        }
        String userType = uamBaseGroupDto.getType(); // 1为大区，3为基地
        String userGroupCode = uamBaseGroupDto.getSerialNo(); // 用户所在大区或基地编码
        String userAreaCode = null; // 用户所在基地，反查出所在大区编码
        if ("3".equals(userType)) {
            Wrapper<UamGroupDto> dto = uamGroupEdasService.getParentInfoByChildSerilNo(userGroupCode);
            userAreaCode = dto.getResult().getSerialNo();
        }
        List<ExceSubmittedVo> list = new ArrayList<>();
        if ("3".equals(userType)) {
            // 3为基地，基地用户只能查询此基地的报送列表信息
            String userBaseCode = userGroupCode;
            String getBaseCode = exceSubmittedQueryDto.getBaseSerialNo();
            String getRegionCode = exceSubmittedQueryDto.getAreaSerialNo();
            if (PubUtils.isSEmptyOrNull(getBaseCode) && PubUtils.isSEmptyOrNull(getRegionCode)) {
                // 如果前端什么都不选，默认查询当前用户所属基地，进行查询
                exceSubmittedQueryDto.setAreaSerialNo(null);
                exceSubmittedQueryDto.setBaseSerialNo(userBaseCode);
            } else if (!PubUtils.isSEmptyOrNull(getBaseCode) && userBaseCode.equals(getBaseCode)) {
                // 如果前端选择了大区和基地，并且与用户基地相吻合，进行查询
                exceSubmittedQueryDto.setAreaSerialNo(null);
                exceSubmittedQueryDto.setBaseSerialNo(userBaseCode);
            } else if (!PubUtils.isSEmptyOrNull(getRegionCode) && PubUtils.isSEmptyOrNull(getBaseCode)) {
                // 如果前端选择了大区，没有选择基地，并且与当前用户相符合，进行查询
                if (userAreaCode.equals(getRegionCode)) {
                    exceSubmittedQueryDto.setAreaSerialNo(null);
                    exceSubmittedQueryDto.setBaseSerialNo(userBaseCode);
                } else {
                    // 如果前端选择了大区，没有选择基地，但选择的大区与当前用户不符合，不查询
                    return list;
                }
            } else {
                return list;
            }
        } else if ("1".equals(userType) && !"GD1625000003".equals(groupRefCode)) {
            // 1为大区，大区用户只能查询此大区及附属基地下的报送列表信息
            String userRegionCode = userGroupCode;
            String getBaseCode = exceSubmittedQueryDto.getBaseSerialNo();
            String getRegionCode = exceSubmittedQueryDto.getAreaSerialNo();
            if (PubUtils.isSEmptyOrNull(getBaseCode) && PubUtils.isSEmptyOrNull(getRegionCode)) {
                // 如果前端什么都不选，默认查询当前用户所属基地，进行查询
                exceSubmittedQueryDto.setAreaSerialNo(userRegionCode);
            } else if (!PubUtils.isSEmptyOrNull(getRegionCode) && userRegionCode.equals(getRegionCode)) {
                // 如果前端选择了大区，并且与用户大区相吻合，进行查询
                exceSubmittedQueryDto.setAreaSerialNo(userRegionCode);
                if (!PubUtils.isSEmptyOrNull(getBaseCode)) {
                    exceSubmittedQueryDto.setBaseSerialNo(getBaseCode);
                }
            } else {
                return list;
            }
        } else if ("GD1625000003".equals(groupRefCode)) {
            // GD1625000003为管理员，查询所有，并且可进行处理操作
        }
        list = exceSubmittedMapper.queryExceSubmittedList(exceSubmittedQueryDto);
        for (ExceSubmittedVo exceSubmittedVo: list) {
            String reportBaseCode = exceSubmittedVo.getReportBaseCode(); // 上报基地
            String reportRegionCode = exceSubmittedVo.getReportRegionCode();// 上报大区
            String exceState = exceSubmittedVo.getExceState();
            if ("GD1625000003".equals(groupRefCode) && "1".equals(exceState)) {
                // 设置管理员可进行处理操作，并加上标识符cl
                exceSubmittedVo.setOperateTag("cl");
            } else if ("1".equals(exceSubmittedVo.getExceState())){
                // 在异常状态为已上报的状态，可以进行取消操作，并加上标识符qx
                // 如果不是管理员
                // 仅上报基地
                if (!PubUtils.isSEmptyOrNull(reportBaseCode) && userGroupCode.equals(reportBaseCode)) {
                    exceSubmittedVo.setOperateTag("qx");
                }
            }
            // 上报基地名称
            String reportBaseName = exceSubmittedVo.getReportBaseName();
            String reportRegionName = exceSubmittedVo.getReportRegionName();
            if (PubUtils.isSEmptyOrNull(reportBaseName)) {
                if (!PubUtils.isSEmptyOrNull(reportRegionName)) {
                    exceSubmittedVo.setReportBaseName(reportRegionName);
                }
            }
            // 开单基地名称
            String baseName = exceSubmittedVo.getBaseName();
            String areaName = exceSubmittedVo.getAreaName();
            if (PubUtils.isSEmptyOrNull(baseName)) {
                if (!PubUtils.isSEmptyOrNull(areaName)) {
                    exceSubmittedVo.setBaseName(areaName);
                }
            }
        }
        return list;
    }
}
