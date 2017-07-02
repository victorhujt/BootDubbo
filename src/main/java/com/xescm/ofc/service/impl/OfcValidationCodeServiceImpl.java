package com.xescm.ofc.service.impl;

import com.xescm.ofc.service.OfcValidationCodeService;
import com.xescm.ofc.utils.RandomGraphic;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 *
 * Created by hujintao on 2016/12/18.
 */
@Service
public class OfcValidationCodeServiceImpl implements OfcValidationCodeService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<String, String> getValidationCode() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Map<String,String> result = new HashedMap();
        String str = null;
        try {
            str = RandomGraphic.createInstance(4).drawInputstr(4,RandomGraphic.GRAPHIC_PNG,output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------------str:"+str);
        result.put("str",str);
        byte[] captcha = output.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String imagestr =  encoder.encode(captcha);// 返回Base64编码过的字节数组字符串
        System.out.println("----------------:"+imagestr);
        result.put("imagestr","data:image/png;base64,"+imagestr);
        System.out.println("----------------:"+captcha.toString());
        /*String path = "D:/myimg.png";
        String path2 = "D:/myimg2.png";
        byte[] data = captcha;
        if(data.length<3||path.equals("")) return null;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }*/

        BASE64Decoder decoder = new BASE64Decoder();
        /*try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imagestr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(path2);
            out.write(bytes);
            out.flush();
            out.close();

        } catch (Exception e) {

        }*/
        return result;
    }
}

