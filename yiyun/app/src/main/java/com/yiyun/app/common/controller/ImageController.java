package com.yiyun.app.common.controller;

import com.yiyun.app.common.annotation.JSONParam;
import com.yiyun.app.common.req.CommonParamReq;
import com.yiyun.app.common.utils.BeanValidator;
import com.yiyun.app.common.utils.BeanValidator.ValidateResult;
import com.yiyun.app.common.utils.QiNiuUpload;
import com.yiyun.app.user.req.ImageUploadReq;
import com.yiyun.app.user.resp.ImageUploadResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author YanXiaoGuang
 * @version 1.0
 * @Title 上传图片类
 * @Description
 * @createDate 2018年1月4日
 * @modifier
 * @modifyDate
 */
@RestController
@Api(value = "ImageController", description = "图片上传")
@RequestMapping("/image")
public class ImageController extends BaseController {

    private static final Logger logger = LogManager.getLogger(ImageController.class.getName());


//    @Autowired
//    private UMemberCardInfoService uMemberCardInfoService;

    @Autowired
    private QiNiuUpload qiNiuUpload;

    /**
     * @author YanXiaoGuang
     * @version 1.0
     * @Title 上传图片 type 1七牛 2身份证
     * @Description
     * @createDate 2018年1月4日
     * @modifier
     * @modifyDate
     */

    @ApiImplicitParams({@ApiImplicitParam(required = true, name = "data", value = "", dataType = "String", paramType = "query", defaultValue = "{\"image\":\"O11514980605860\",\"type\":\"2\"}")})
    @RequestMapping(value = "imageUpload", method = RequestMethod.POST)
    public void imageUpload(@JSONParam("common") CommonParamReq common, @JSONParam("data") ImageUploadReq req, HttpServletResponse response) throws Exception {

        // 请求参数校验数据格式
        ValidateResult validateResult = BeanValidator.validateBean(req);
        if (!validateResult.isValid()) {
            returnFail(validateResult.getMessage(), response);
            return;
        }
        try {
            String image = "";
            // 上传图片 1七牛 2身份证照片
            if (req.getType().equals("1")) {
                Map<String, String> upload = qiNiuUpload.upload(req.getImage());
                image = upload.get("imgPath");
                logger.info("七牛图片上传成功，地址:" + image);
            } else {//身份证图片
//                String fileName = UUID.randomUUID().toString() + ".jpg";
//                image = uMemberCardInfoService.saveCardImg(req.getImage(), fileName, req.getType(), response);
//                if (StringUtils.isBlank(image)) {
//                    logger.info("身份证上传失败");
//                    returnFail("图片信息录入失败", response);
//                    return;
//                }
            }
            ImageUploadResp resp = new ImageUploadResp();
            resp.setImageName(image);
            logger.info("身份证上传成功");
            returnSuccess(resp, "图片上传成功", response);
            return;

        } catch (Exception e) {
            logger.info("图片上传上传失败" + e.toString());
            returnFail("图片上传失败", response);
        }

    }
}
