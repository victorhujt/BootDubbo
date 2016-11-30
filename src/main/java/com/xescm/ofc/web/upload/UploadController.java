package com.xescm.ofc.web.upload;

import com.xescm.ofc.web.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by hiyond on 2016/11/30.
 */
public class UploadController extends BaseController {

    @RequestMapping("ofc/upload")
    @ResponseBody
    public Object upload(@RequestParam("file")MultipartFile file) {
        if (!file.isEmpty()) {
            System.out.println(file.getName());
            System.out.println(file.getSize());
        }
        return "";
    }

}
