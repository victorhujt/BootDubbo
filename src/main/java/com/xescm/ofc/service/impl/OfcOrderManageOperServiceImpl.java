package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OrderFollowOperResult;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcOrderOperMapper;
import com.xescm.ofc.mapper.OfcOrderScreenMapper;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.uam.provider.UamAuthEdasService;
import com.xescm.uam.provider.UamGroupEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hiyond on 2016/11/24.
 */
@Service
public class OfcOrderManageOperServiceImpl implements OfcOrderManageOperService {

    @Resource
    private UamGroupEdasService uamGroupEdasService;
    @Autowired
    private OfcOrderScreenMapper ofcOrderScreenMapper;
    @Autowired
    private OfcOrderOperMapper ofcOrderOperMapper;

    @Override
    public List<OrderScreenResult> queryOrderOper(OrderOperForm form) {
        return ofcOrderScreenMapper.queryOrderOper(form);
    }

    @Override
    public List<OrderSearchOperResult> queryOrderList(OrderOperForm form) {
        return ofcOrderOperMapper.queryOrderList(form);
    }

    @Override
    public List<OrderSearchOperResult> queryOrderByOrderBatchNumber(String orderBatchNumber) {
        return ofcOrderOperMapper.queryOrderByOrderBatchNumber(orderBatchNumber);
    }

    @Override
    public List<OrderFollowOperResult> queryOrder(String code, String searchType) {
        return ofcOrderOperMapper.queryOrder(code, searchType);
    }

    /**
     * 根据当前登录用户, 加载大区基地
     * @param authResDto
     * @return
     */
    @Override
    public Map<String, List<OfcGroupVo>> queryGroupList(AuthResDto authResDto) {
        UamGroupDto uamGroupDto = new UamGroupDto();
        if(null == authResDto || PubUtils.isSEmptyOrNull(authResDto.getGroupRefCode())){
            return null;
        }
        uamGroupDto.setSerialNo(authResDto.getGroupRefCode());
        Wrapper<List<UamGroupDto>> allGroupByType = uamGroupEdasService.getAllGroupByType(uamGroupDto);
        checkUamGroupEdasResultNullOrError(allGroupByType);
        if(CollectionUtils.isEmpty(allGroupByType.getResult()) || allGroupByType.getResult().size() > 1){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果为空");
        }
        UamGroupDto uamGroupDtoResult = allGroupByType.getResult().get(0);
        Map<String, List<OfcGroupVo>> resultMap = new HashMap<>();
        if(null == uamGroupDtoResult || PubUtils.isSEmptyOrNull(uamGroupDtoResult.getType())){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果有误");
        }
        if(PubUtils.isSEmptyOrNull(uamGroupDtoResult.getSerialNo())){
            throw new BusinessException("当前登录的用户没有流水号!");
        }
        String groupType = uamGroupDtoResult.getType();
        if(StringUtils.equals(groupType,"1")){
            //鲜易供应链身份
            if(StringUtils.equals("GD1625000003",uamGroupDtoResult.getSerialNo())){
                resultMap = getGroupMsg(uamGroupDtoResult,"xebest");
                //大区身份
            }else{
                resultMap = getGroupMsg(uamGroupDtoResult,"area");
            }
            //基地身份
        }else if(StringUtils.equals(groupType,"3")){
            resultMap = getGroupMsg(uamGroupDtoResult,"base");
            //仓库身份, 其他身份怎么处理?
        }else{

        }

        return resultMap;
    }


    /**
     * 根据登录用户获取大区, 基地
     * @param uamGroupDto
     * @param identity
     * @return
     */
    private Map<String, List<OfcGroupVo>> getGroupMsg(UamGroupDto uamGroupDto, String identity){
        if(null == uamGroupDto || PubUtils.isSEmptyOrNull(identity)){
            throw new BusinessException("根据登录用户获取组织信息入参有误!");
        }
        Map<String, List<OfcGroupVo>> resultMap = new HashMap<>();
        List<OfcGroupVo> areaList = new ArrayList<>();
        List<OfcGroupVo> baseList = new ArrayList<>();
        String userSerialNo = uamGroupDto.getSerialNo();
        //鲜易供应链
        if(StringUtils.equals("xebest",identity)){
            //鲜易供应链下所有大区
            if(PubUtils.isSEmptyOrNull(uamGroupDto.getSerialNo())){
                throw new BusinessException("根据登录用户获取大区列表入参有误!");
            }
            Wrapper<List<UamGroupDto>> childGroupInfoByParentSerilNoArea = uamGroupEdasService.getChildGroupInfoByParentSerilNo(userSerialNo);
            checkUamGroupEdasResultNullOrError(childGroupInfoByParentSerilNoArea);
            List<UamGroupDto> uamGroupDtoListArea = childGroupInfoByParentSerilNoArea.getResult();
            if(CollectionUtils.isEmpty(uamGroupDtoListArea)){
                throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果有误,或当前组织下没有子组织");
            }

            for(UamGroupDto uamGroupDtoAreaResult : uamGroupDtoListArea){
                if(null != uamGroupDtoAreaResult
                        && !PubUtils.isSEmptyOrNull(uamGroupDtoAreaResult.getGroupCode())
                        && !PubUtils.isSEmptyOrNull(uamGroupDtoAreaResult.getGroupName())){
                    OfcGroupVo ofcGroupVoArea = new OfcGroupVo();
                    ofcGroupVoArea.setGroupCode(uamGroupDtoAreaResult.getGroupCode());
                    ofcGroupVoArea.setGroupName(uamGroupDtoAreaResult.getGroupName());
                    areaList.add(ofcGroupVoArea);
                    //获取当前大区下的所有基地
                    baseList.addAll(getBaseListByCurArea(uamGroupDtoAreaResult));
                }
            }
            OfcGroupVo defaultBase = new OfcGroupVo();
            defaultBase.setGroupCode("");
            defaultBase.setGroupName("");
            baseList.add(0,defaultBase);
            OfcGroupVo defaultArea = new OfcGroupVo();
            defaultArea.setGroupCode("");
            defaultArea.setGroupName("");
            areaList.add(0,defaultArea);
            //大区
        }else if(StringUtils.equals("area",identity)){
            OfcGroupVo ofcGroupVo = new OfcGroupVo();
            ofcGroupVo.setGroupCode(uamGroupDto.getGroupCode());
            ofcGroupVo.setGroupName(uamGroupDto.getGroupName());
            areaList.add(ofcGroupVo);
            //获取当前大区下的所有基地
            baseList = getBaseListByCurArea(uamGroupDto);
            OfcGroupVo defaultBase = new OfcGroupVo();
            defaultBase.setGroupCode("");
            defaultBase.setGroupName("");
            baseList.add(0,defaultBase);
            //基地
        }else if(StringUtils.equals("base",identity)){
            if(PubUtils.isSEmptyOrNull(uamGroupDto.getGroupCode())
                    || PubUtils.isSEmptyOrNull(uamGroupDto.getGroupName())){
                throw new BusinessException("当前基地"+ uamGroupDto.getGroupName() +"信息不完整!");
            }
            //根据当前基地获取父级大区
            Wrapper<UamGroupDto> parentInfoByChildSerilNo = uamGroupEdasService.getParentInfoByChildSerilNo(userSerialNo);
            checkUamGroupEdasResultNullOrError(parentInfoByChildSerilNo);
            UamGroupDto uamGroupDtoResult = parentInfoByChildSerilNo.getResult();
            if(null == uamGroupDtoResult){
                throw new BusinessException("当前基地"+ uamGroupDto.getGroupName() +"没有所属大区!");
            }
            if(PubUtils.isSEmptyOrNull(uamGroupDtoResult.getGroupCode())
                    || PubUtils.isSEmptyOrNull(uamGroupDtoResult.getGroupName())){
                throw new BusinessException("当前基地"+ uamGroupDto.getGroupName() +"所属大区信息不完整!");
            }
            OfcGroupVo ofcGroupVoArea = new OfcGroupVo();
            ofcGroupVoArea.setGroupCode(uamGroupDtoResult.getGroupCode());
            ofcGroupVoArea.setGroupName(uamGroupDtoResult.getGroupName());
            areaList.add(ofcGroupVoArea);
            OfcGroupVo ofcGroupVoBase = new OfcGroupVo();
            ofcGroupVoBase.setGroupCode(uamGroupDto.getGroupCode());
            ofcGroupVoBase.setGroupName(uamGroupDto.getGroupName());
            baseList.add(ofcGroupVoBase);
        }
        if(areaList.size() < 1 || baseList.size() < 1){
            return null;
        }
        resultMap.put("area",areaList);
        resultMap.put("base",baseList);
        return resultMap;
    }

    /**
     * 获取当前大区下的所有基地
     * @param uamGroupDto
     */
    private List<OfcGroupVo> getBaseListByCurArea(UamGroupDto uamGroupDto) {
        Wrapper<List<UamGroupDto>> childGroupInfoByParentSerilNoBase = uamGroupEdasService.getChildGroupInfoByParentSerilNo(uamGroupDto.getSerialNo());
        checkUamGroupEdasResultNullOrError(childGroupInfoByParentSerilNoBase);
        List<UamGroupDto> uamGroupDtoListBase = childGroupInfoByParentSerilNoBase.getResult();
        if(CollectionUtils.isEmpty(uamGroupDtoListBase)){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果有误,或当前组织下没有子组织");
        }
        List<OfcGroupVo> baseList = new ArrayList<>();
        for(UamGroupDto uamGroupDtoBaseResult : uamGroupDtoListBase){
            if(null != uamGroupDtoBaseResult
                    && !PubUtils.isSEmptyOrNull(uamGroupDtoBaseResult.getGroupCode())
                    && !PubUtils.isSEmptyOrNull(uamGroupDtoBaseResult.getGroupName())){
                OfcGroupVo ofcGroupVo = new OfcGroupVo();
                ofcGroupVo.setGroupCode(uamGroupDtoBaseResult.getGroupCode());
                ofcGroupVo.setGroupName(uamGroupDtoBaseResult.getGroupName());
                baseList.add(ofcGroupVo);
            }
        }
        return baseList;
    }

    /**
     * 校验UamGroupEdas返回结果
     * @param allGroupByType
     */
    private void checkUamGroupEdasResultNullOrError(Wrapper<?> allGroupByType) {
        if(null == allGroupByType){
            throw new BusinessException("查询当前登录用户组织信息出错,接口返回null");
        }
        if(Wrapper.ERROR_CODE == allGroupByType.getCode()){
            throw new BusinessException("查询当前登录用户组织信息出错:{}",allGroupByType.getMessage());
        }
    }
}
