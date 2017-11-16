package com.xescm.ofc.web.restcontroller.operationWorkbench.distributionOrder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.core.utils.PublicUtil;
import com.xescm.csc.model.dto.CscContantAndCompanyInportDto;
import com.xescm.csc.model.dto.CscGoodsImportDto;
import com.xescm.csc.provider.CscContactCompanyEdasService;
import com.xescm.csc.provider.CscGoodsEdasService;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.csc.OfcGoodsImportDto;
import com.xescm.ofc.model.dto.ofc.OfcBatchCreateByRedisDTO;
import com.xescm.ofc.model.dto.ofc.OfcDistributionExcelImportDTO;
import com.xescm.ofc.model.dto.ofc.OfcDistributionUploadDTO;
import com.xescm.ofc.model.vo.ofc.OfcCheckExcelErrorVo;
import com.xescm.ofc.service.OfcOperCommonService;
import com.xescm.ofc.service.OfcOperationDistributingService;
import com.xescm.ofc.service.OfcStorageTemplateService;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xescm.ofc.constant.ExcelCheckConstant.BATCH_CONSIGNEE;
import static com.xescm.ofc.constant.ExcelCheckConstant.BATCH_GOODS;
import static com.xescm.ofc.constant.GenCodePreffixConstant.BATCH_PRE;
import static com.xescm.ofc.constant.StorageTemplateConstant.ERROR;
import static com.xescm.ofc.constant.StorageTemplateConstant.ERROR_CUST;

/**
 *
 * Created by lyh on 2017/7/5.
 */
@Controller
@RequestMapping(value = "/page/ofc/distri/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcOperDistriMainController", tags = "OfcOperDistriMainController", description = "城配开单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcOperDistriMainController extends BaseController{
    @Resource
    private CodeGenUtils codeGenUtils;
    @Resource
    private OfcOperationDistributingService ofcOperationDistributingService;
    @Resource
    private OfcStorageTemplateService ofcStorageTemplateService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private OfcOperCommonService ofcOperCommonService;
    @Resource
    private CscGoodsEdasService cscGoodsEdasService;
    @Resource
    private CscContactCompanyEdasService cscContactCompanyEdasService;

    @ResponseBody
    @RequestMapping(value = "/placeOrdersListCon", method = {RequestMethod.POST})
    @ApiOperation(value = "城配开单确认下单", httpMethod = "POST", notes = "城配开单确认下单")
    public Wrapper placeOrdersListCon(@RequestBody String orderLists) {
        logger.info("城配开单确认下单==> orderLists={}", orderLists);
        String resultMessage;
        try {
            if (PubUtils.isSEmptyOrNull(orderLists)) {
                logger.error("入参为空");
                return WrapMapper.wrap(Wrapper.ERROR_CODE, "您没有添加任何信息,请检查!");
            }
            JSONArray jsonArray = JSON.parseArray(orderLists);
            String batchNumber = codeGenUtils.getNewWaterCode(BATCH_PRE, 4);//生成订单批次号,保证一批单子属于一个批次
            Wrapper<?> validateCustOrderCodeResult = ofcOperationDistributingService.validateCustOrderCode(jsonArray);
            if (Wrapper.ERROR_CODE == validateCustOrderCodeResult.getCode()) {
                return validateCustOrderCodeResult;
            }
            resultMessage = ofcOperationDistributingService.distributingOrderPlace(jsonArray, getAuthResDtoByToken(), batchNumber);
        } catch (BusinessException ex) {
            logger.error("运营中心城配开单批量下单失败!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("运营中心城配开单批量下单失败!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "运营中心城配开单批量下单失败!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, resultMessage);
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    @ApiOperation(value = "城配导单上传", httpMethod = "POST", notes = "城配导单上传")
    public Wrapper placeOrdersListCon(@RequestParam(value = "file") MultipartFile file, OfcDistributionUploadDTO importDTO) {
        logger.info("城配导单上传==> file={}", file);
        logger.info("城配导单上传==> importDTO={}", importDTO);
        Wrapper<?> result = null;
        try {
            if(PubUtils.isSEmptyOrNull(importDTO.getCustCode())){
                return WrapMapper.wrap(ERROR_CUST, "请先选择客户");
            }
            AuthResDto authResDto = getAuthResDtoByToken();
            Integer activeSheetNum = ofcStorageTemplateService.checkStorageTemplate(file);
            String suffix = ofcOperCommonService.getFileSuffix(file);
            Wrapper<?> checkResult = ofcOperationDistributingService.checkExcel(file, suffix,
                    activeSheetNum.toString(), authResDto, importDTO.getCustCode(), importDTO.getTemplateType(), importDTO.getModelMappingCode());
            //如果校验失败
            if (checkResult == null) {
                return WrapMapper.wrap(ERROR, "城配导单出错!");
            }else if (checkResult.getCode() == Wrapper.ERROR_CODE) {
                int tenThousand = 100000;
                OfcCheckExcelErrorVo ofcCheckExcelErrorVo = (OfcCheckExcelErrorVo) checkResult.getResult();
                List<OfcGoodsImportDto> cscGoodsImportDtoList = ofcCheckExcelErrorVo.getCscGoodsImportDtoList();
                List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = ofcCheckExcelErrorVo.getCscContantAndCompanyInportDtoList();
                if (cscGoodsImportDtoList.size() > 0) {
                    StringBuilder batchgoodsKey = new StringBuilder(BATCH_GOODS);
                    batchgoodsKey.append(System.nanoTime());
                    batchgoodsKey.append((int) (Math.random() * tenThousand));
                    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
                    ops.set(batchgoodsKey.toString(), JacksonUtil.toJsonWithFormat(cscGoodsImportDtoList));
                    stringRedisTemplate.expire(batchgoodsKey.toString(), 5L, TimeUnit.MINUTES);
                    ofcCheckExcelErrorVo.setBatchgoodsKey(batchgoodsKey.toString());
                }
                if (cscContantAndCompanyInportDtoList.size() > 0) {
                    StringBuilder batchconsingeeKey = new StringBuilder(BATCH_CONSIGNEE);
                    batchconsingeeKey.append(System.nanoTime());
                    batchconsingeeKey.append((int) (Math.random() * tenThousand));
                    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
                    ops.set(batchconsingeeKey.toString(), JacksonUtil.toJsonWithFormat(cscContantAndCompanyInportDtoList));
                    stringRedisTemplate.expire(batchconsingeeKey.toString(), 5L, TimeUnit.MINUTES);
                    ofcCheckExcelErrorVo.setBatchconsingeeKey(batchconsingeeKey.toString());
                }
                result = WrapMapper.wrap(Wrapper.ERROR_CODE, checkResult.getMessage(), ofcCheckExcelErrorVo);
            } else if (checkResult.getCode() == Wrapper.SUCCESS_CODE) {
                Map<String, JSONArray> resultMap = (Map<String, JSONArray>) checkResult.getResult();
                List<OfcDistributionExcelImportDTO> resultDTO = new ArrayList<>();
                for (String mapKey : resultMap.keySet()) {
                    OfcDistributionExcelImportDTO dto = new OfcDistributionExcelImportDTO();
                    dto.setGoodKey(mapKey);
                    dto.setJsonArray(resultMap.get(mapKey));
                    resultDTO.add(dto);
                }
                String resultJSON = JacksonUtil.toJsonWithFormat(resultDTO);
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, checkResult.getMessage(), resultJSON);
            }
        } catch (BusinessException ex) {
            logger.error("城配导单上传失败!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("城配导单上传失败!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "城配导单上传失败!");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getGoodsList", method = {RequestMethod.POST})
    @ApiOperation(value = "批量创建货品获取货品LIST ", httpMethod = "POST", notes = "城配开单确认下单")
    public Wrapper<List<OfcGoodsImportDto>> getGoodsList(@RequestBody OfcBatchCreateByRedisDTO redisDTO) {
        logger.info("批量创建货品获取货品LIST==> redisDTO={}", redisDTO);
        List<OfcGoodsImportDto> result;
        try {
            if (StringUtils.isEmpty(redisDTO.getBatchgoodsKey())) {
                throw new BusinessException("批量创建货品获取货品LIST出错!");
            }
            ValueOperations<String,String> ops  = stringRedisTemplate.opsForValue();
            String goodsList = ops.get(redisDTO.getBatchgoodsKey());
            if(PublicUtil.isEmpty(goodsList)){
                throw new BusinessException("页面已过期");
            }
            result = JSONObject.parseArray(goodsList, OfcGoodsImportDto.class);
        } catch (BusinessException ex) {
            logger.error("批量创建货品获取货品LIST失败!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("批量创建货品获取货品LIST失败!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "批量创建货品获取货品LIST失败!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    @ResponseBody
    @RequestMapping(value = "/batchCreateGoods/{batchgoodsKey}", method = {RequestMethod.POST})
    @ApiOperation(value = "批量创建货品", httpMethod = "POST", notes = "城配开单确认下单")
    public Wrapper batchCreateGoods(@RequestBody List<CscGoodsImportDto> cscGoodsImportDtos, @PathVariable String batchgoodsKey) {
        logger.info("批量创建货品==> batchgoodsKey={}", batchgoodsKey);
        logger.info("批量创建货品==> cscGoodsImportDtos={}", cscGoodsImportDtos);
        try {
            Wrapper wrapper = cscGoodsEdasService.batchCreateGoods(cscGoodsImportDtos, batchgoodsKey);
            if (wrapper.getCode() != Wrapper.SUCCESS_CODE) {
                logger.error("批量创建货品出错{}", wrapper);
                throw new BusinessException("批量创建货品出错!");
            }
        } catch (BusinessException ex) {
            logger.error("批量创建货品失败!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("批量创建货品失败!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "批量创建货品失败!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }


    @ResponseBody
    @RequestMapping(value = "/getConsigneesList", method = {RequestMethod.POST})
    @ApiOperation(value = "批量创建收货方获取货品LIST ", httpMethod = "POST", notes = "城配开单确认下单")
    public Wrapper<List<CscContantAndCompanyInportDto>> getConsigneesList(@RequestBody OfcBatchCreateByRedisDTO redisDTO) {
        logger.info("批量创建收货方获取货品LIST==> redisDTO={}", redisDTO);
        List<CscContantAndCompanyInportDto> result;
        try {
            if (StringUtils.isEmpty(redisDTO.getBatchconsingeeKey())) {
                throw new BusinessException("批量创建收货方获取货品LIST出错!");
            }
            ValueOperations<String,String> ops  = stringRedisTemplate.opsForValue();
            String consigneeList = ops.get(redisDTO.getBatchconsingeeKey());
            if(PublicUtil.isEmpty(consigneeList)){
                throw new BusinessException("页面已过期");
            }
            result = JSONObject.parseArray(consigneeList, CscContantAndCompanyInportDto.class);
        } catch (BusinessException ex) {
            logger.error("批量创建收货方获取货品LIST出错!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("批量创建收货方获取货品LIST出错!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "批量创建收货方获取货品LIST出错!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    @ResponseBody
    @RequestMapping(value = "/batchCreateConsignees/{batchconsingeeKey}", method = {RequestMethod.POST})
    @ApiOperation(value = "批量创建收货方", httpMethod = "POST", notes = "城配开单确认下单")
    public Wrapper batchCreateConsignees(@RequestBody List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtos, @PathVariable String batchconsingeeKey) {
        logger.info("批量创建收货方==> batchconsingeeKey={}", batchconsingeeKey);
        logger.info("批量创建收货方==> cscContantAndCompanyInportDtos={}", cscContantAndCompanyInportDtos);
        try {
            Wrapper wrapper = cscContactCompanyEdasService.batchCreateConsignees(cscContantAndCompanyInportDtos, batchconsingeeKey);
            if (wrapper.getCode() != Wrapper.SUCCESS_CODE) {
                logger.error("批量创建收货方出错{}", wrapper);
                throw new BusinessException("批量创建收货方出错!");
            }
        } catch (BusinessException ex) {
            logger.error("批量创建收货方出错!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("批量创建收货方出错!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "批量创建收货方出错!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }

    @ResponseBody
    @RequestMapping(value = "/storeTempOrderMsg", method = {RequestMethod.POST})
    @ApiOperation(value = "存贮临时订单信息", httpMethod = "POST", notes = "城配开单确认下单")
    public Wrapper<String> storeTempOrderMsg(@RequestBody String importMsg) {
        logger.info("存贮临时订单信息==> importMsg={}", importMsg);
        String result;
        try {
            if (StringUtils.isEmpty(importMsg)) {
                throw new BusinessException("存贮临时订单信息出错!");
            }
            StringBuilder importMsgKey = new StringBuilder("temp_order");
            importMsgKey.append(System.nanoTime());
            importMsgKey.append((int) (Math.random() * 1000));
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(importMsgKey.toString(), importMsg);
            stringRedisTemplate.expire(importMsgKey.toString(), 5L, TimeUnit.MINUTES);
            result = importMsgKey.toString();
        } catch (BusinessException ex) {
            logger.error("存贮临时订单信息出错!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("存贮临时订单信息出错!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "存贮临时订单信息出错!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    @ResponseBody
    @RequestMapping(value = "/getTempOrderMsg", method = {RequestMethod.POST})
    @ApiOperation(value = "获取临时订单信息 ", httpMethod = "POST", notes = "城配开单确认下单")
    public Wrapper<String> getTempOrderMsg(@RequestBody String importMsgKey) {
        logger.info("获取临时订单信息==> importMsgKey={}", importMsgKey);
        String result;
        try {
            if (StringUtils.isEmpty(importMsgKey)) {
                throw new BusinessException("获取临时订单信息出错!");
            }
            ValueOperations<String,String> ops  = stringRedisTemplate.opsForValue();
            result = ops.get(importMsgKey);
            if(PublicUtil.isEmpty(result)){
                throw new BusinessException("页面已过期");
            }
        } catch (BusinessException ex) {
            logger.error("获取临时订单信息出错!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("批量创建收货方获取货品LIST出错!{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "批量创建收货方获取货品LIST出错!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

}
