package com.xescm.ofc.web.restcontroller;

import com.xescm.ofc.service.OfcOperationDistributingService;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by lyh on 2016/12/2.
 */
@Controller
@RequestMapping(value = "/open")
public class OfcOpenController extends BaseController{

    /**
     * 城配开单下载模板
     * @param response
     */
    @RequestMapping(value = "/downloadTemplate",method = RequestMethod.GET)
    public void downloadTemplate( HttpServletResponse response){
        /*try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=Template_forCP.xlsx");
            File f = ResourceUtils.getFile("classpath:static/xlsx/Template_forCP.xlsx");
            FileOutputStream fos=new FileOutputStream(f);
            response.setContentLengthLong(f.length());
//            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

//        Photo photo = photoService.getPhotoByPhotoId(id);
//        byte[] data = photo.getPhoto_data();
//        String fileName = photo.getPhoto_name()== null ? "照片.png" : photo.getPhoto_name();
        try {
            //fileName = URLEncoder.encode(fileName, "UTF-8");
            File f = ResourceUtils.getFile("classpath:templates/xlsx/Template_forCP.xlsx");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Template_forCP.xlsx");
            response.addHeader("Content-Length", "" + f.length());
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            int b;
            while((b = bis.read()) != -1) {
                outputStream.write(b);
            }
            bis.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
