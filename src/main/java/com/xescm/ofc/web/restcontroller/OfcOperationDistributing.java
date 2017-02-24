package com.xescm.ofc.web.restcontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscContantAndCompanyInportDto;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.QueryCustomerNameAvgueDto;
import com.xescm.csc.model.dto.goodstype.CscGoodsTypeDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.model.vo.CscGoodsTypeVo;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.csc.provider.CscGoodsEdasService;
import com.xescm.csc.provider.CscGoodsTypeEdasService;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.csc.OfcGoodsImportDto;
import com.xescm.ofc.model.vo.ofc.OfcCheckExcelErrorVo;
import com.xescm.ofc.service.OfcOperationDistributingService;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xescm.ofc.constant.ExcelCheckConstant.BATCH_CONSIGNEE;
import static com.xescm.ofc.constant.ExcelCheckConstant.BATCH_GOODS;
import static com.xescm.ofc.constant.GenCodePreffixConstant.BATCH_PRE;

/**
* <p>Title: .城配开单 </p>
* <p>Description TODO </p>
* <p>Company: http://www.hnxianyi.com </p>
*
* @Author <a href="lyhluo@163.com"/>罗迎豪</a>
* @CreateDate 2016/11/19
*/
@RequestMapping(value = "/ofc/distributing",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOperationDistributing extends BaseController{
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcOperationDistributingService ofcOperationDistributingService;
    @Resource
    private CscCustomerEdasService cscCustomerEdasService;
    @Resource
    private CscGoodsEdasService cscGoodsEdasService;
    @Resource
    private CscGoodsTypeEdasService cscGoodsTypeEdasService;
    @Resource
    private StringRedisTemplate rt;
    @Resource
    private CodeGenUtils codeGenUtils;

    /**
     * 城配开单确认下单
     * @param orderLists 下单数据
     * @return 根据不同结果返回不同泛型
     */
    @RequestMapping(value = "/placeOrdersListCon",method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> placeOrdersListCon(String orderLists){
        logger.info("城配开单确认下单==> orderLists={}", orderLists);
        String resultMessage;
        try{
            if(PubUtils.isSEmptyOrNull(orderLists)){

                logger.error("城配开单批量下单入参为空");
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"您没有添加任何信息,请检查!");
            }
            JSONArray jsonArray = JSON.parseArray(orderLists);
            String batchNumber = codeGenUtils.getNewWaterCode(BATCH_PRE,4);//生成订单批次号,保证一批单子属于一个批次

            Wrapper<?> validateCustOrderCodeResult =  ofcOperationDistributingService.validateCustOrderCode(jsonArray);
            if(Wrapper.ERROR_CODE == validateCustOrderCodeResult.getCode()){
                return validateCustOrderCodeResult;
            }
            resultMessage = ofcOperationDistributingService.distributingOrderPlace(jsonArray,getAuthResDtoByToken(),batchNumber);
        } catch (BusinessException ex){
            logger.error("运营中心城配开单批量下单失败!{}",ex.getMessage(),ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        } catch (Exception ex){
            if (ex.getCause().getMessage().trim().startsWith("Duplicate entry")) {
                logger.error("运营中心城配开单批量下单由于单号发生重复导致失败!{}",ex.getMessage(),ex);
                throw new BusinessException("运营中心城配开单批量下单由于单号发生重复导致失败!", ex);
            } else {
                logger.error("运营中心城配开单批量下单失败!{}",ex.getMessage(),ex);
            }
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"运营中心城配开单批量下单失败!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,resultMessage);
    }

    /**
     * 根据选择的客户查询仓库
     * @param customerCode 客户编码
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/queryWarehouseByCustId",method = RequestMethod.POST)
    @ResponseBody
    public void queryCustomerByName(String customerCode,HttpServletResponse response){
        logger.info("城配开单根据选择的客户查询仓库==> customerCode={}", customerCode);
        try{
            List<RmcWarehouseRespDto> rmcWarehouseByCustCode  = ofcWarehouseInformationService.getWarehouseListByCustCode(customerCode);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JacksonUtil.toJsonWithFormat(rmcWarehouseByCustCode));
        }catch (Exception ex){
            logger.error("城配下单查询仓库列表失败!{}",ex.getMessage(),ex);
        }
    }

    /**
     * 查询货品一级种类
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/queryGoodsTypeByCustId",method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsTypeByCustId(HttpServletResponse response){
        Wrapper<List<CscGoodsTypeVo>> wrapper = null;
        try{
            CscGoodsTypeDto cscGoodsType = new CscGoodsTypeDto();
            wrapper = cscGoodsTypeEdasService.queryCscGoodsTypeList(cscGoodsType);
            if(null != wrapper.getResult()){
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(JacksonUtil.toJsonWithFormat(wrapper.getResult()));
            }
        }catch (Exception ex){
            logger.error("城配下单查询货品种类失败!异常信息为{},接口返回状态信息{},{}"
                    ,ex.getMessage(),wrapper == null ? "城配下单查询货品种类失败的结果为null": wrapper.getMessage(),ex);
        }
    }

    /**
     * 根据货品一级种类查询货品二级小类
     * @param goodsType 货品一级类别
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/queryGoodsSecTypeByCAndT",method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsSecTypeByCAndT(String goodsType,HttpServletResponse response){
        logger.info("城配开单根据选择的客户和货品一级种类查询货品二级小类==> goodsType={}", goodsType);
        Wrapper<List<CscGoodsTypeVo>> wrapper = null;
        try{
            CscGoodsTypeDto cscGoodsType = new CscGoodsTypeDto();
            cscGoodsType.setPid(goodsType);
            wrapper = cscGoodsTypeEdasService.queryCscGoodsTypeList(cscGoodsType);
            if(null != wrapper.getResult()){
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(JacksonUtil.toJsonWithFormat(wrapper.getResult()));
            }
        }catch (Exception ex){
            logger.error("城配下单查询货品小类失败!异常信息为{},接口返回状态信息{}"
                    ,ex.getMessage(),wrapper == null ? "城配下单查询货品小类失败的结果为null": wrapper.getMessage(),ex);
        }
    }

    /**
     * 查询货品列表
     * @param cscGoodsApiDto 前端查询条件
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/queryGoodsListInDistrbuting", method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsListInDistrbuting(CscGoodsApiDto cscGoodsApiDto, HttpServletResponse response){
        logger.info("城配开单查询货品列表==> cscGoodsApiDto={}", cscGoodsApiDto);
        Wrapper<List<CscGoodsApiVo>> wrapper = null;
        try{
            wrapper = cscGoodsEdasService.queryCscGoodsList(cscGoodsApiDto);
            if(null != wrapper.getResult()){
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(JacksonUtil.toJsonWithFormat(wrapper.getResult()));
            }
        }catch (Exception ex){
            logger.error("城配下单查询货品列表失败!{}{}{}"
                    ,ex.getMessage(),wrapper == null ? "城配开单查询货品列表查到的结果为null": wrapper.getMessage(),ex);
        }
    }



    /**
     * 根据客户名称分页查询客户
     * @param custName  客户名称
     * @param pageNum   页数
     * @param pageSize  每页大小
     * @return
     */
    @RequestMapping(value = "/queryCustomerByName",method = RequestMethod.POST)
    @ResponseBody
    public Object queryCustomerByName(String custName, int pageNum, int pageSize) {
        logger.info("城配开单根据客户名称查询客户==> custName={}", custName);
        logger.info("城配开单根据客户名称查询客户==> pageNum={}", pageNum);
        logger.info("城配开单根据客户名称查询客户==> pageSize={}", pageSize);
        Wrapper<PageInfo<CscCustomerVo>> result;
        try {
            QueryCustomerNameAvgueDto queryParam = new QueryCustomerNameAvgueDto();
            queryParam.setCustomerName(custName);
            queryParam.setPageNum(pageNum);
            queryParam.setPageSize(pageSize);
            result = cscCustomerEdasService.queryCustomerByNameAvgue(queryParam);
            if (Wrapper.ERROR_CODE == result.getCode()) {
                logger.error("查询客户列表失败,查询结果有误!");
            }
        } catch (BusinessException ex) {
            logger.error("==>城配开单根据客户名称查询客户发生错误：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("==>城配开单根据客户名称查询客户发生异常：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "城配开单根据客户名称查询客户发生异常！");
        }
        return result;
    }

    /**
     * Excel导入,上传,展示Sheet页
     * @param paramHttpServletRequest HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/fileUploadAndCheck",method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> fileUploadAndCheck(HttpServletRequest paramHttpServletRequest){
        List<String> excelSheet;
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) paramHttpServletRequest;
            MultipartFile uploadFile = multipartHttpServletRequest.getFile("file");
            String fileName = multipartHttpServletRequest.getParameter("fileName");
            int potIndex = fileName.lastIndexOf(".") + 1;
            if(-1 == potIndex){
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"该文件没有扩展名!");
            }
            String suffix = fileName.substring(potIndex, fileName.length());
            excelSheet = ofcOperationDistributingService.getExcelSheet(uploadFile,suffix);
        }catch (BusinessException e) {
            logger.error("城配开单Excel导入展示Sheet页出错:{}",e.getMessage(),e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        }catch (Exception e) {
            logger.error("城配开单Excel导入展示Sheet页出错:{}",e.getMessage(),e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"上传成功！",excelSheet);
    }

    /**
     * 根据用户选择的Sheet页进行校验并加载正确或错误信息
     * @param paramHttpServletRequest HttpServletRequest
     * @return 根据不同结果返回不同泛型
     */
    @RequestMapping(value = "/excelCheckBySheet",method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> excelCheckBySheet(HttpServletRequest paramHttpServletRequest){
        Wrapper<?> result = null;
        try {
            AuthResDto authResDto = getAuthResDtoByToken();
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) paramHttpServletRequest;
            MultipartFile uploadFile = multipartHttpServletRequest.getFile("file");
            String fileName = multipartHttpServletRequest.getParameter("fileName");
            //模板类型: 交叉(MODEL_TYPE_ACROSS), 明细列表(MODEL_TYPE_BORADWISE)
            String modelType = multipartHttpServletRequest.getParameter("templatesType");
            //模板映射: 标准, 呷哺呷哺, 尹乐宝等
            String modelMappingCode = multipartHttpServletRequest.getParameter("templatesMapping");
            int potIndex = fileName.lastIndexOf(".") + 1;
            if(-1 == potIndex){
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"该文件没有扩展名!");
            }
            String suffix = fileName.substring(potIndex, fileName.length());
            String customerCode = multipartHttpServletRequest.getParameter("customerCode");
            String sheetNum = multipartHttpServletRequest.getParameter("sheetNum");
            Wrapper<?> checkResult = ofcOperationDistributingService.checkExcel(uploadFile,suffix,sheetNum,authResDto,customerCode,modelType,modelMappingCode);

            //如果校验失败
            if(checkResult.getCode() == Wrapper.ERROR_CODE){
                int tenThousand = 100000;
                OfcCheckExcelErrorVo ofcCheckExcelErrorVo = (OfcCheckExcelErrorVo) checkResult.getResult();
                List<OfcGoodsImportDto> cscGoodsImportDtoList = ofcCheckExcelErrorVo.getCscGoodsImportDtoList();
                List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = ofcCheckExcelErrorVo.getCscContantAndCompanyInportDtoList();
                if(cscGoodsImportDtoList.size() > 0){
                    StringBuilder batchgoodsKey = new StringBuilder(BATCH_GOODS);
                    batchgoodsKey.append(System.nanoTime());
                    batchgoodsKey.append((int)(Math.random()*tenThousand));
                    ValueOperations<String,String> ops  = rt.opsForValue();
                    ops.set(batchgoodsKey.toString(), JacksonUtil.toJsonWithFormat(cscGoodsImportDtoList));
                    rt.expire(batchgoodsKey.toString(), 5L, TimeUnit.MINUTES);
                    ofcCheckExcelErrorVo.setBatchgoodsKey(batchgoodsKey.toString());
                }
                if(cscContantAndCompanyInportDtoList.size() > 0){
                    StringBuilder batchconsingeeKey = new StringBuilder(BATCH_CONSIGNEE);
                    batchconsingeeKey.append(System.nanoTime());
                    batchconsingeeKey.append((int)(Math.random()*tenThousand));
                    ValueOperations<String,String> ops  = rt.opsForValue();
                    ops.set(batchconsingeeKey.toString(), JacksonUtil.toJsonWithFormat(cscContantAndCompanyInportDtoList));
                    rt.expire(batchconsingeeKey.toString(), 5L, TimeUnit.MINUTES);
                    ofcCheckExcelErrorVo.setBatchconsingeeKey(batchconsingeeKey.toString());
                }
                result = WrapMapper.wrap(Wrapper.ERROR_CODE,checkResult.getMessage(),ofcCheckExcelErrorVo);
            }else if(checkResult.getCode() == Wrapper.SUCCESS_CODE){
                Map<String,JSONArray> resultMap = (Map<String, JSONArray>) checkResult.getResult();
                String resultJSON = JacksonUtil.toJsonWithFormat(resultMap);
                result =  WrapMapper.wrap(Wrapper.SUCCESS_CODE,checkResult.getMessage(),resultJSON);
            }
        } catch (BusinessException e) {
            logger.error("城配开单Excel导入校验出错:{}",e.getMessage(),e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            logger.error("城配开单Excel导入校验出错:{}",e.getMessage(),e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return result;
    }



}
