package com.yiyun.app.mapp.controller;

import com.yiyun.utils.JSONUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传图片至七牛云存储
 * liyinq
 * 2017/12/15
 */
@Controller
@RequestMapping("/appminiprog")
public class MQiNiuUploadController {

    private final Logger logger = LoggerFactory.getLogger(MQiNiuUploadController.class);

    @Value("${qiniuParam.ACCESS_KEY}")
    private String ACCESS_KEY;
    @Value("${qiniuParam.SECRET_KEY}")
    private String SECRET_KEY;
    //空间名
    @Value("${qiniuParam.bucketname}")
    private String bucketname;
    //测试外链域名
    @Value("${qiniuParam.qiniuurl}")
    private String qiniuurl;

    /**
     * 初始化上传管理对象
     *
     * @return
     */
    private UploadManager getUploadManager() {
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        return new UploadManager(c);
    }

    /**
     * 获取上传至qiniu的token
     *
     * @param postfix
     * @return
     */
    public String getUpToken(String postfix) {
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //简单上传，使用默认策略，只需要设置上传的空间名就可以了
        StringMap x = new StringMap();
        x.put("saveKey", "$(etag)" + postfix);
        //x.put("returnBody", "{\"name\":\"img/"+ DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss")+"/$(etag)" + postfix + "\",\"size\":\"$(fsize)\",\"w\":\"$(imageInfo.width)\",\"h\":\"$(imageInfo.height)\",\"hash\":\"$(etag)\"}");
        x.put("returnBody", "{\"name\":\"$(etag)" + postfix + "\",\"size\":\"$(fsize)\",\"w\":\"$(imageInfo.width)\",\"h\":\"$(imageInfo.height)\",\"hash\":\"$(etag)\"}");
        return auth.uploadToken(bucketname, null, 3600, x);
    }

    /**
     * 上传接口，返回图片地址
     *
     * @param
     * @param
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadImg")
    @ResponseBody
    public Map<String, String> upload(@RequestParam("file") MultipartFile img) throws IOException {    //不仅仅是byte数组,也可以是文件地址,文件,
        logger.info("XInfO.[图片上传]开始 . . . ");
        try {
            String fileName = img.getOriginalFilename();
            logger.info("XInfO.[图片上传]fileName : " + fileName);
            byte[] file = img.getBytes();
            String postfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());//获得文件名的后缀
            //调用put方法上传
            Response res = getUploadManager().put(file, null, getUpToken(postfix));
            //打印返回的信息
            String resBodyStr = res.bodyString();
            Map<String, String> result = JSONUtil.jsonToMapS(resBodyStr);
            logger.info("XInfO.[图片上传]七牛上传成功 , 响应 : " + resBodyStr);
            String imgName = result.get("name");
            result.put("imgUrl", qiniuurl);
            result.put("imgPath", qiniuurl + imgName);
            logger.info("XInfO.[图片上传]完成 , 返回数据 . imgUrl : " + qiniuurl + " , imgPath : " + qiniuurl + imgName);
            return result;    //上传成功后返回的保存文件的名字
        } catch (QiniuException e) {
            Map<String, String> map = new HashMap<String, String>();
            Response r = e.response;
            logger.error("XErroR.[图片上传]异常 , 响应数据 : " + toString().getClass() , e);
            try {
                logger.error("XErroR.[图片上传]异常 , 响应信息 : " + r.bodyString());
                map.put("error", r.bodyString());
                return map;
            } catch (QiniuException e1) {
                logger.info("XInfO.[图片上传]七牛上传异常 , 失败信息 : " + e1.getLocalizedMessage() , e);
                map.put("error", e1.getLocalizedMessage());
                return map;
            }
        }

    }
}
