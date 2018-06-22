package com.yiyun.app.common.utils;

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
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 上传图片至七牛云存储
 * liyinq
 * 2017/12/15
 */
@Service
public class QiNiuUpload {

    private final Logger logger = LoggerFactory.getLogger(QiNiuUpload.class);

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

    public Map<String, String> upload(String img) throws IOException {    //不仅仅是byte数组,也可以是文件地址,文件,
        try {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            byte[] file = null;
            // 校验图片格式是否合法
            BASE64Decoder decoder = new BASE64Decoder();
            file = decoder.decodeBuffer(img);// Base64解码
            for (int i = 0; i < file.length; ++i) {
                if (file[i] < 0) {// 调整异常数据
                    file[i] += 256;
                }
            }
            String postfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());//获得文件名的后缀
            //调用put方法上传
            Response res = getUploadManager().put(file, null, getUpToken(postfix));
            //打印返回的信息
            String resBodyStr = res.bodyString();
            Map<String, String> result = JSONUtil.jsonToMapS(resBodyStr);
            logger.info("七牛上传成功返回:{}", resBodyStr);
            String imgName = result.get("name");
            result.put("url", qiniuurl);
            result.put("imgPath", qiniuurl + imgName);
            return result;    //上传成功后返回的保存文件的名字
        } catch (QiniuException e) {
            Map<String, String> map = new HashMap<String, String>();
            Response r = e.response;
            logger.error("请求失败时打印的异常的信息:{}", r.toString());
            try {
                logger.error("响应的文本信息:{}", r.bodyString());
                map.put("error", r.bodyString());
                return map;
            } catch (QiniuException e1) {
                logger.error("七牛上传失败,打印失败信息:{}", e1.getLocalizedMessage());
                map.put("error", e1.getLocalizedMessage());
                return map;
            }
        }

    }
}
