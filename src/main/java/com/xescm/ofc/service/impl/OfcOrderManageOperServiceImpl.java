package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OrderFollowOperResult;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcDailyAccountMapper;
import com.xescm.ofc.mapper.OfcOrderOperMapper;
import com.xescm.ofc.mapper.OfcOrderScreenMapper;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.model.dto.form.OrderStorageOperForm;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.ofc.service.OfcOrderManageOperService;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.uam.provider.UamGroupEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运营中心订单管理
 * Created by hiyond on 2016/11/24.
 */
@Service
public class OfcOrderManageOperServiceImpl implements OfcOrderManageOperService {

    @Resource
    private UamGroupEdasService uamGroupEdasService;
    @Resource
    private OfcOrderScreenMapper ofcOrderScreenMapper;
    @Resource
    private OfcOrderOperMapper ofcOrderOperMapper;

    @Resource
    private OfcDailyAccountMapper ofcDailyAccountMapper;

    @Resource
    private OfcOrderManageOperService ofcOrderManageOperService;

    @Override
    public List<OrderSearchOperResult> queryOrderStorageDataOper(AuthResDto authResDto, OrderStorageOperForm form, String tag) {
        if(tag.equals("in")){
                List<String> businessTypes=new ArrayList<>();
                businessTypes.add("620");
                businessTypes.add("621");
                businessTypes.add("622");
                businessTypes.add("623");
                businessTypes.add("624");
                businessTypes.add("625");
                businessTypes.add("626");
                form.setBusinessTypes(businessTypes);
        }else if(tag.equals("out")){
                List<String> businessTypes=new ArrayList<>();
                businessTypes.add("610");
                businessTypes.add("611");
                businessTypes.add("612");
                businessTypes.add("613");
                businessTypes.add("614");
                businessTypes.add("617");
                form.setBusinessTypes(businessTypes);
        }
        return queryStorageOrderList(authResDto,form);
    }


    public List<OrderSearchOperResult> queryStorageOrderList(AuthResDto authResDto,OrderStorageOperForm form) {
        //订单管理筛选后端权限校验
        if(null == authResDto || null == form){
            throw new BusinessException("订单管理筛选后端权限校验入参有误");
        }
        List<OrderSearchOperResult> orderSearchOperResults = new ArrayList<>();
        UamGroupDto uamGroupDto = new UamGroupDto();
        uamGroupDto.setSerialNo(authResDto.getGroupRefCode());
        Wrapper<List<UamGroupDto>> allGroupByType = uamGroupEdasService.getAllGroupByType(uamGroupDto);
        ofcOrderManageOperService.checkUamGroupEdasResultNullOrError(allGroupByType);
        if(CollectionUtils.isEmpty(allGroupByType.getResult()) || allGroupByType.getResult().size() > 1){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果为空或有误");
        }
        UamGroupDto uamGroupDtoResult = allGroupByType.getResult().get(0);
        if(null == uamGroupDtoResult || PubUtils.isSEmptyOrNull(uamGroupDtoResult.getType())){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果有误");
        }
        if(PubUtils.isSEmptyOrNull(uamGroupDtoResult.getSerialNo())){
            throw new BusinessException("当前登录的用户没有流水号!");
        }
        String groupType = uamGroupDtoResult.getType();
        if(PubUtils.isSEmptyOrNull(form.getAreaSerialNo()) && !PubUtils.isSEmptyOrNull(form.getBaseSerialNo())){
            throw new BusinessException("基地所属大区未选择!");
        }
        if(StringUtils.equals(groupType,"1")){
            //鲜易供应链身份
            if(StringUtils.equals("GD1625000003",uamGroupDtoResult.getSerialNo())){
                orderSearchOperResults = ofcOrderOperMapper.queryStorageOrderList(form);
                //大区身份
            }else{
                if(PubUtils.isSEmptyOrNull(form.getAreaSerialNo())){
                    throw new BusinessException("运营中心订单管理筛选入参:大区为空!");
                }
                orderSearchOperResults = ofcOrderOperMapper.queryStorageOrderList(form);
            }
            //基地身份
        }else if(StringUtils.equals(groupType,"3")){
            if(PubUtils.isSEmptyOrNull(form.getBaseSerialNo())){
                throw new BusinessException("运营中心订单管理筛选入参: 基地为空!");
            }
            orderSearchOperResults = ofcOrderOperMapper.queryStorageOrderList(form);
            //仓库身份, 其他身份
        }else{
            throw new BusinessException("运营中心订单管理筛选入参: 权限不足!");
        }
        if(PubUtils.isSEmptyOrNull(form.getAreaSerialNo()) && !PubUtils.isSEmptyOrNull(form.getBaseSerialNo())){
            throw new BusinessException("运营中心订单管理筛选入参: 基地所属大区为空");
        }
        return orderSearchOperResults;
    }








    @Override
    public List<OrderScreenResult> queryOrderOper(OrderOperForm form) {
        return ofcOrderScreenMapper.queryOrderOper(form);
    }

    /**
     * 订单管理筛选，以及后端权限校验
     * @param authResDto 当前登录用户信息
     * @param form 查询条件
     * @return
     */
    @Override
    public List<OrderSearchOperResult> queryOrderList(AuthResDto authResDto,OrderOperForm form) {
        if(null == authResDto || null == form){
            throw new BusinessException("订单管理筛选后端权限校验入参有误");
        }
        List<OrderSearchOperResult> orderSearchOperResults;
        UamGroupDto uamGroupDto = new UamGroupDto();
        uamGroupDto.setSerialNo(authResDto.getGroupRefCode());
        Wrapper<List<UamGroupDto>> allGroupByType = uamGroupEdasService.getAllGroupByType(uamGroupDto);
        ofcOrderManageOperService.checkUamGroupEdasResultNullOrError(allGroupByType);
        if(CollectionUtils.isEmpty(allGroupByType.getResult()) || allGroupByType.getResult().size() > 1){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果为空或有误");
        }
        UamGroupDto uamGroupDtoResult = allGroupByType.getResult().get(0);
        if(null == uamGroupDtoResult || PubUtils.isSEmptyOrNull(uamGroupDtoResult.getType())){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果有误");
        }
        if(PubUtils.isSEmptyOrNull(uamGroupDtoResult.getSerialNo())){
            throw new BusinessException("当前登录的用户没有流水号!");
        }
        String groupType = uamGroupDtoResult.getType();
        if(PubUtils.isSEmptyOrNull(form.getAreaSerialNo()) && !PubUtils.isSEmptyOrNull(form.getBaseSerialNo())){
            throw new BusinessException("基地所属大区未选择!");
        }
        if(StringUtils.equals(groupType,"1")){
            //鲜易供应链身份
            if(StringUtils.equals("GD1625000003",uamGroupDtoResult.getSerialNo())){
                orderSearchOperResults = ofcOrderOperMapper.queryOrderList(form);
                //大区身份
            }else{
                if(PubUtils.isSEmptyOrNull(form.getAreaSerialNo())){
                    throw new BusinessException("运营中心订单管理筛选入参:大区为空!");
                }
                orderSearchOperResults = ofcOrderOperMapper.queryOrderList(form);
            }
            //基地身份
        }else if(StringUtils.equals(groupType,"3")){
            if(PubUtils.isSEmptyOrNull(form.getBaseSerialNo())){
                throw new BusinessException("运营中心订单管理筛选入参: 基地为空!");
            }
            orderSearchOperResults = ofcOrderOperMapper.queryOrderList(form);
            //仓库身份, 其他身份
        }else{
            throw new BusinessException("运营中心订单管理筛选入参: 权限不足!");
        }
        if(PubUtils.isSEmptyOrNull(form.getAreaSerialNo()) && !PubUtils.isSEmptyOrNull(form.getBaseSerialNo())){
            throw new BusinessException("运营中心订单管理筛选入参: 基地所属大区为空");
        }
        return orderSearchOperResults;
    }

    /**
     * 根据订单批次号查询订单
     * @param orderBatchNumber 订单批次号
     * @return
     */
    @Override
    public List<OrderSearchOperResult> queryOrderByOrderBatchNumber(String orderBatchNumber) {
        return ofcOrderOperMapper.queryOrderByOrderBatchNumber(orderBatchNumber);
    }

    /**
     * 订单跟踪根据不同方式查询订单
     * @param code 编码
     * @param searchType 类型
     * @return
     */
    @Override
    public List<OrderFollowOperResult> queryOrder(String code, String searchType) {
        return ofcOrderOperMapper.queryOrder(code, searchType);
    }

    /**
     * 根据当前登录用户, 加载大区基地
     * @param authResDto 当前登录用户
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
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果为空或有误");
        }
        UamGroupDto uamGroupDtoResult = allGroupByType.getResult().get(0);
        if(null == uamGroupDtoResult || PubUtils.isSEmptyOrNull(uamGroupDtoResult.getType())){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果有误");
        }
        if(PubUtils.isSEmptyOrNull(uamGroupDtoResult.getSerialNo())){
            throw new BusinessException("当前登录的用户没有流水号!");
        }
        Map<String, List<OfcGroupVo>> resultMap;
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
            resultMap = null;
        }
        if(null == resultMap){
            throw new BusinessException("您所登录的用户大区基地信息不完整!");
        }
        return resultMap;
    }



    /**
     * 根据登录用户获取大区, 基地
     * @param uamGroupDto 组织实体对象
     * @param identity 身份标志位
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
                        && !PubUtils.isSEmptyOrNull(uamGroupDtoAreaResult.getSerialNo())
                        && !PubUtils.isSEmptyOrNull(uamGroupDtoAreaResult.getGroupName())){
                    OfcGroupVo ofcGroupVoArea = new OfcGroupVo();
                    ofcGroupVoArea.setSerialNo(uamGroupDtoAreaResult.getSerialNo());
                    ofcGroupVoArea.setGroupName(uamGroupDtoAreaResult.getGroupName());
                    areaList.add(ofcGroupVoArea);
                    //获取当前大区下的所有基地
                    baseList.addAll(getBaseListByCurArea(uamGroupDtoAreaResult));
                }
            }
            OfcGroupVo defaultBase = new OfcGroupVo();
            defaultBase.setSerialNo("");
            defaultBase.setGroupName("");
            baseList.add(0,defaultBase);
            OfcGroupVo defaultArea = new OfcGroupVo();
            defaultArea.setSerialNo("");
            defaultArea.setGroupName("");
            areaList.add(0,defaultArea);
            //大区
        }else if(StringUtils.equals("area",identity)){
            OfcGroupVo ofcGroupVo = new OfcGroupVo();
            ofcGroupVo.setSerialNo(uamGroupDto.getSerialNo());
            ofcGroupVo.setGroupName(uamGroupDto.getGroupName());
            areaList.add(ofcGroupVo);
            //获取当前大区下的所有基地
            baseList = getBaseListByCurArea(uamGroupDto);
            OfcGroupVo defaultBase = new OfcGroupVo();
            defaultBase.setSerialNo("");
            defaultBase.setGroupName("");
            baseList.add(0,defaultBase);
            //基地
        }else if(StringUtils.equals("base",identity)){
            if(PubUtils.isSEmptyOrNull(uamGroupDto.getSerialNo())
                    || PubUtils.isSEmptyOrNull(uamGroupDto.getGroupName())){
                throw new BusinessException("当前基地"+ uamGroupDto.getGroupName() +"信息不完整!");
            }
            //根据当前基地获取父级大区
            OfcGroupVo ofcGroupVoArea = queryAreaMsgByBase(uamGroupDto);
            areaList.add(ofcGroupVoArea);
            OfcGroupVo ofcGroupVoBase = new OfcGroupVo();
            ofcGroupVoBase.setSerialNo(uamGroupDto.getSerialNo());
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
     * @param uamGroupDto 组织实体对象
     */
    public List<OfcGroupVo> getBaseListByCurArea(UamGroupDto uamGroupDto) {
        if(null == uamGroupDto || PubUtils.isSEmptyOrNull(uamGroupDto.getSerialNo())){
            throw new BusinessException("获取当前大区下的所有基地失败");
        }
        Wrapper<List<UamGroupDto>> childGroupInfoByParentSerilNoBase = uamGroupEdasService.getChildGroupInfoByParentSerilNo(uamGroupDto.getSerialNo());
        checkUamGroupEdasResultNullOrError(childGroupInfoByParentSerilNoBase);
        List<UamGroupDto> uamGroupDtoListBase = childGroupInfoByParentSerilNoBase.getResult();
        if(CollectionUtils.isEmpty(uamGroupDtoListBase)){
            throw new BusinessException("查询当前登录用户组织信息出错:查询到的结果有误,或当前组织下没有子组织");
        }
        List<OfcGroupVo> baseList = new ArrayList<>();
        for(UamGroupDto uamGroupDtoBaseResult : uamGroupDtoListBase){
            if(null != uamGroupDtoBaseResult
                    && !PubUtils.isSEmptyOrNull(uamGroupDtoBaseResult.getSerialNo())
                    && !PubUtils.isSEmptyOrNull(uamGroupDtoBaseResult.getGroupName())){
                OfcGroupVo ofcGroupVo = new OfcGroupVo();
                ofcGroupVo.setSerialNo(uamGroupDtoBaseResult.getSerialNo());
                ofcGroupVo.setGroupName(uamGroupDtoBaseResult.getGroupName());
                baseList.add(ofcGroupVo);
            }
        }
        return baseList;
    }

    /**
     * 根据所选基地反查大区
     * @param uamGroupDto 组织实体对象
     * @return
     */
    @Override
    public OfcGroupVo queryAreaMsgByBase(UamGroupDto uamGroupDto) {
        if(null == uamGroupDto || PubUtils.isSEmptyOrNull(uamGroupDto.getSerialNo())){
            throw new BusinessException("根据所选基地反查大区失败");
        }
        Wrapper<UamGroupDto> parentInfoByChildSerilNo = uamGroupEdasService.getParentInfoByChildSerilNo(uamGroupDto.getSerialNo());
        checkUamGroupEdasResultNullOrError(parentInfoByChildSerilNo);
        UamGroupDto uamGroupDtoResult = parentInfoByChildSerilNo.getResult();
        if(null == uamGroupDtoResult){
            throw new BusinessException("当前基地"+ uamGroupDto.getGroupName() +"没有所属大区!");
        }
        if(PubUtils.isSEmptyOrNull(uamGroupDtoResult.getSerialNo())
                || PubUtils.isSEmptyOrNull(uamGroupDtoResult.getGroupName())){
            throw new BusinessException("当前基地"+ uamGroupDto.getGroupName() +"所属大区信息不完整!");
        }
        OfcGroupVo ofcGroupVoArea = new OfcGroupVo();
        ofcGroupVoArea.setSerialNo(uamGroupDtoResult.getSerialNo());
        ofcGroupVoArea.setGroupName(uamGroupDtoResult.getGroupName());
        return ofcGroupVoArea;
    }

    /**
     * 校验UamGroupEdas返回结果
     * @param allGroupByType 查询当前登录用户组织信息UamGroupEdas返回结果
     */
    public void checkUamGroupEdasResultNullOrError(Wrapper<?> allGroupByType) {
        if(null == allGroupByType){
            throw new BusinessException("查询当前登录用户组织信息出错,接口返回null");
        }
        if(Wrapper.ERROR_CODE == allGroupByType.getCode()){
            throw new BusinessException("查询当前登录用户组织信息出错:{}",allGroupByType.getMessage());
        }
    }



}
