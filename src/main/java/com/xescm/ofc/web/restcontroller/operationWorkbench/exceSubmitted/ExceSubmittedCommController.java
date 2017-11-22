package com.xescm.ofc.web.restcontroller.operationWorkbench.exceSubmitted;

import com.aliyun.oss.OSSClient;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.config.OSSConfigureConfig;
import com.xescm.ofc.domain.OfcAttachment;
import com.xescm.ofc.enums.ExceBusinessTypeEnum;
import com.xescm.ofc.enums.ExceTypeEnum;
import com.xescm.ofc.model.vo.exce.ExceSubmittedVo;
import com.xescm.ofc.service.ExceSubmittedCommService;
import com.xescm.ofc.service.OfcAttachmentService;
import com.xescm.ofc.utils.CheckArgumentUtil;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.model.dto.group.UamGroupDto;
import com.xescm.uam.provider.UamGroupEdasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;

/**
 * <p>Title: ExceSubmittedCommController. </p>
 * <p>异常报送通用Controller </p>
 * @param
 * @Author 袁宝龙
 * @CreateDate 2017/11/7 14:02
 * @return 
 */
@Controller
@RequestMapping(value = "/page/ofc/comm/exce/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "ExceSubmittedCommController", tags = "ExceSubmittedCommController", description = "异常报送通用", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExceSubmittedCommController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ExceSubmittedCommController.class);

    @Autowired
    private ExceSubmittedCommService exceSubmittedCommService;
    @Autowired
    private OfcAttachmentService ofcAttachmentService;
    @Resource
    private UamGroupEdasService uamGroupEdasService;
    @Resource
    private OSSConfigureConfig ossConfigure;

    @ResponseBody
    @RequestMapping(value = "/selectExceBusinessType", method = {RequestMethod.POST})
    @ApiOperation(value = "查询异常录入业务类型文案", httpMethod = "POST", notes = "查询异常录入业务类型文案")
    public Wrapper<?> selectExceBusinessType() {
        try {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ExceBusinessTypeEnum.getMap2List());
        } catch (BusinessException ex) {
            logger.error("查询异常录入业务类型文案：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("查询异常录入业务类型文案：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectExceType", method = {RequestMethod.POST})
    @ApiOperation(value = "查询异常录入类型文案", httpMethod = "POST", notes = "查询异常录入类型文案")
    public Wrapper<?> selectExceType() {
        try {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ExceTypeEnum.getMap2List());
        } catch (BusinessException ex) {
            logger.error("查询异常录入类型文案：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("查询异常录入类型文案：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/uploadImg")
    @ApiOperation(notes = "返回图片流水号", httpMethod = "POST", value = "上传异常附件图片")
    public Wrapper<String> uploadImg(HttpServletRequest request) {
        logger.info("上传异常附件图片");
        String serialNo = null;
        try {
            serialNo = exceSubmittedCommService.uploadImg(request, getAuthResDtoByToken());
        } catch (Exception e) {
            logger.error("==>上传异常附件图片, 出现异常={}", e.getMessage(), e);
            return  WrapMapper.wrap(Wrapper.ERROR_CODE,"上传异常附件图片失败");
        }
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, serialNo);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteImg/{serialNo}")
    @ApiOperation(notes = "返回操作结果", httpMethod = "POST", value = "删除异常报送图片附件")
    public Wrapper deleteImg(@ApiParam(name = "serialNo", value = "照片流水号") @PathVariable String serialNo) {
        logger.info("删除异常报送图片附件，serialNo={}", serialNo);
        try {
            OfcAttachment search = new OfcAttachment();
            search.setSerialNo(serialNo);
            OfcAttachment ofcAttachment = ofcAttachmentService.selectOne(search);
            CheckArgumentUtil.checkArgument(ofcAttachment != null, "没有该流水信息");
            OSSClient ossClient = new OSSClient(ossConfigure.getEndpoint(), ossConfigure.getAccessKeyId(), ossConfigure.getAccessKeySecret());
            ossClient.deleteObject(ossConfigure.getBucketName(), ofcAttachment.getPath() + ofcAttachment.getName());
        } catch (Exception e) {
            logger.error("==>删除异常报送图片附件, 出现异常={}", e.getMessage(), e);
            return  WrapMapper.wrap(Wrapper.ERROR_CODE,"删除异常报送图片附件失败");
        }
        return  WrapMapper.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/queryImg/{serialNo}")
    @ApiOperation(notes = "返回图片信息", httpMethod = "POST", value = "返回图片信息")
    public Wrapper<?> queryImg(@ApiParam(name = "serialNo", value = "照片流水号") @PathVariable String serialNo) {
        logger.info("查询异常报送图片附件路径，serialNo={}", serialNo);
        try {
            OfcAttachment search = new OfcAttachment();
            search.setSerialNo(serialNo);
            OfcAttachment ofcAttachment = ofcAttachmentService.selectOne(search);
            OSSClient ossClient = new OSSClient(ossConfigure.getEndpoint(), ossConfigure.getAccessKeyId(), ossConfigure.getAccessKeySecret());
            ExceSubmittedVo exceSubmittedVo = new ExceSubmittedVo();
            if (!PubUtils.isOEmptyOrNull(ofcAttachment)){
                exceSubmittedVo.setImgSerialNo(ofcAttachment.getSerialNo());
                exceSubmittedVo.setName(ofcAttachment.getName());
                URL img = ofcAttachmentService.getFileURL(ofcAttachment.getPath() + ofcAttachment.getName());
                if(img != null){
                    exceSubmittedVo.setPath(img.toString().replace("vpc100-", ""));
                }
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, exceSubmittedVo);
        } catch (Exception e) {
            logger.error("==>查询异常报送图片附件路径, 出现异常={}", e.getMessage(), e);
            return  WrapMapper.wrap(Wrapper.ERROR_CODE,"查询异常报送图片附件路径失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkUserGroup")
    @ApiOperation(notes = "检查异常录入用户权限", httpMethod = "POST", value = "检查异常录入用户权限")
    public Wrapper<?> checkUserGroup() {
        logger.info("检查异常录入用户权限");
        try {
            String msg = "只有基地人员才能录入异常";
            AuthResDto authResDto = getAuthResDtoByToken();
            String groupRefCode = authResDto.getGroupRefCode();
            // GD1625000003为管理员
            if ("GD1625000003".equals(groupRefCode)) {
                return  WrapMapper.wrap(Wrapper.ERROR_CODE,msg);
            }
            // userType为1 表示此用户为大区用户
            Wrapper<UamGroupDto> uamBaseGroupDtoWrapper = uamGroupEdasService.queryByGroupSerialNo(groupRefCode);
            UamGroupDto uamBaseGroupDto = uamBaseGroupDtoWrapper.getResult();
            String userType = uamBaseGroupDto.getType();
            if ("1".equals(userType)) {
                return  WrapMapper.wrap(Wrapper.ERROR_CODE,msg);
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
        } catch (Exception e) {
            logger.error("==>检查异常录入用户权限, 出现异常={}", e.getMessage(), e);
            return  WrapMapper.wrap(Wrapper.ERROR_CODE,"检查异常录入用户权限失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getUserPower")
    @ApiOperation(notes = "获取用户权限", httpMethod = "POST", value = "获取用户权限")
    public Wrapper<?> getUserPower() {
        logger.info("获取用户权限");
        try {
            String userPower = "";
            AuthResDto authResDto = getAuthResDtoByToken();
            String groupRefCode = authResDto.getGroupRefCode();
            if ("GD1625000003".equals(groupRefCode)) {
                userPower = "2";
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, userPower);
            }
            Wrapper<UamGroupDto> uamBaseGroupDtoWrapper = uamGroupEdasService.queryByGroupSerialNo(groupRefCode);
            UamGroupDto uamBaseGroupDto = uamBaseGroupDtoWrapper.getResult();
            String userType = uamBaseGroupDto.getType();
            if ("1".equals(userType) || "3".equals(userType)) {
                userPower = "1";
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, userPower);
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, userPower);
        } catch (Exception e) {
            logger.error("==>获取用户权限, 出现异常={}", e.getMessage(), e);
            return  WrapMapper.wrap(Wrapper.ERROR_CODE,"获取用户权限失败");
        }
    }
}
