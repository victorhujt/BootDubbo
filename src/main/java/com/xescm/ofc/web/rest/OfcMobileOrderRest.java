package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.enums.OssFileUrlEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcMobileOrderDto;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.service.OssManagerService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hujintao on 2016/12/12.
 */
@RequestMapping(value = "/ofc/api", produces = {"application/json;charset=UTF-8"})
@RestController
public class OfcMobileOrderRest extends BaseController {

  //  @Autowired
   // private OfcMobileOrderService ofcMobileOrderService;

    @Autowired
    private OssManagerService ossManagerService;

    @Autowired
    private OfcMobileOrderService ofcMobileOrderService;

    @RequestMapping(value = "mobileOrder/saveMobileOrder", method = RequestMethod.POST)
    @ApiOperation(value = "保存手机订单信息", response = Wrapper.class)
    public Wrapper<?> saveMobileOrder(@ApiParam(name = "ofcMobileOrderDto", value = "手机订单信息") @RequestBody  OfcMobileOrderDto ofcMobileOrderDto) {
        logger.debug("==>保存拍照录单信息 mobileOrder={}", ofcMobileOrderDto);
        try {
            if(ofcMobileOrderDto == null){
                throw new BusinessException("参数不能为空");
            }
            OfcMobileOrder ofcMobileOrder=new OfcMobileOrder();
            BeanUtils.copyProperties(ofcMobileOrderDto,ofcMobileOrder);
            ofcMobileOrderService.save(ofcMobileOrder);
        } catch (BusinessException ex) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception e) {
            logger.debug("保存拍照录单信息={}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,  e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }


    /**
     * 流水号查询拍照录单订单
     * @param
     * @return
     */
    @RequestMapping(value="mobileOrder/queryMobileOrderByCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通过流水号查询手机订单信息", notes = "返回包装手机订单信息json", response = Wrapper.class)
    private Wrapper<?> queryMobileOrderByCode(@ApiParam(name ="ofcMobileOrderDto", value = "手机订单信息") @RequestBody OfcMobileOrderDto ofcMobileOrderDto){
        OfcMobileOrder result=null;
        try {
            if(ofcMobileOrderDto == null){
                throw new BusinessException("参数不能为空");
            }
            if (StringUtils.isBlank(ofcMobileOrderDto.getMobileOrderCode())){
                throw new BusinessException("流水号不能为空!");
            }
            OfcMobileOrder condition=new OfcMobileOrder();
            BeanUtils.copyProperties(ofcMobileOrderDto,condition);
             result= ofcMobileOrderService.selectOne(condition);
        } catch (Exception e) {
            logger.error("订单号查询出错：orderCode{},{}", ofcMobileOrderDto.getMobileOrderCode(), e.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE,e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * 上传图片
     * @param request
     * @param mobileOrder
     * @throws Exception
     */
    private void uploadFile(HttpServletRequest request, OfcMobileOrder mobileOrder) throws Exception {
            String fileName1 = null;
            String fileName2 = null;
            String fileName3 = null;
            String fileName4 = null;
            Map<String,MultipartFile> fileNames=new HashMap<>();
            MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
            MultipartFile imgFile1 = multipartRequest.getFile("img_1");
            MultipartFile imgFile2 = multipartRequest.getFile("img_2");
            MultipartFile imgFile3 = multipartRequest.getFile("img_3");
            MultipartFile imgFile4 = multipartRequest.getFile("img_4");
            long maxSize = 1024 * 1024;
            Map<String,Map<String,MultipartFile>> imgMap=new HashMap<>();
            if(imgFile1 != null){
                if(imgFile1.getSize() > maxSize){
                    throw new BusinessException("上传图片不能大于1M");
                }
                fileName1 = imgFile1.getOriginalFilename();
                if(!StringUtils.isEmpty(fileName1.trim())){
                    fileNames.put(fileName1,imgFile1);
                    imgMap.put("img_1",fileNames);
                }
            }
            if(imgFile2 != null){
                if(imgFile2.getSize() > maxSize){
                    throw new BusinessException("上传图片不能大于1M");
                }
                fileName2 = imgFile2.getOriginalFilename();
                if(!StringUtils.isEmpty(fileName2.trim())){
                    fileNames.put(fileName2,imgFile2);
                    imgMap.put("img_2",fileNames);
                }
            }
            if(imgFile3 != null){
                if(imgFile3.getSize() > maxSize){
                    throw new BusinessException("上传图片不能大于1M");
                }
                fileName3 = imgFile3.getOriginalFilename();
                if(!StringUtils.isEmpty(fileName3.trim())){
                    fileNames.put(fileName3,imgFile3);
                    imgMap.put("img_3",fileNames);
                }
            }
            if(imgFile4 != null){
                if(imgFile4.getSize() > maxSize){
                    throw new BusinessException("上传图片不能大于1M");
                }
                fileName4 = imgFile4.getOriginalFilename();
                if(!StringUtils.isEmpty(fileName4.trim())){
                    fileNames.put(fileName4,imgFile4);
                    imgMap.put("img_4",fileNames);
                }
            }
            Iterator it=fileNames.keySet().iterator();
            while(it.hasNext()){
                String imgNo=it.next().toString();
                Map<String,MultipartFile> imgFile= (Map<String, MultipartFile>) fileNames.get(imgNo);
                Iterator file=imgFile.keySet().iterator();
                while(file.hasNext()){
                    String fileName=file.next().toString();
                    MultipartFile f=imgFile.get(fileName);
                    ossManagerService.uploadFile(f.getInputStream(), fileName, OssFileUrlEnum.URL_DEFAULT.getUrl());
                    if("img_1".equals(imgNo)){
                        mobileOrder.setImg1Url(OssFileUrlEnum.URL_DEFAULT.getUrl()+fileName);
                    }else if("img_2".equals(imgNo)){
                        mobileOrder.setImg2Url(OssFileUrlEnum.URL_DEFAULT.getUrl()+fileName);
                    }else if("img_3".equals(imgNo)){
                        mobileOrder.setImg3Url(OssFileUrlEnum.URL_DEFAULT.getUrl()+fileName);
                    }else if("img_4".equals(imgNo)){
                        mobileOrder.setImg4Url(OssFileUrlEnum.URL_DEFAULT.getUrl()+fileName);
                    }
                }
            }
    }
}
