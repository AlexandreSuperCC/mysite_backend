package com.ycao.mysite.controller.admin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ycao.mysite.api.QiniuCloudService;
import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.constant.OtherConstant;
import com.ycao.mysite.constant.Types;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.AttAchDomain;
import com.ycao.mysite.service.attach.IAttAchService;
import com.ycao.mysite.utils.APIResponse;
import com.ycao.mysite.utils.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api("attachment related interface")
@Controller
@RequestMapping(value = "/home")
public class AttAchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttAchController.class);

    @Autowired
    private QiniuCloudService qiniuCloudService;
    @Autowired
    private IAttAchService attAchService;

    @Value("${qiniu.cdn.url}")
    public String QINIU_UPLOAD_SITE;

    @ResponseBody
    @PostMapping(value = "deleteOneFile")
    public APIResponse deleteOneFile(HttpServletRequest request,HttpServletResponse response,
                                     @RequestBody String params)
    {
        try {
            Map map = MyUtils.getMapFromAPI(params,"fname");
            //获取message对应的值
            String fname = (String) map.get("fname");
            int deleteRes = attAchService.deleteOneFile(fname);
            if(deleteRes!=1){
                return APIResponse.fail("deleting fails");
            }else{
                return APIResponse.success();
            }
        }catch (BusinessException exception){
            return APIResponse.fail(exception.getErrorCode());
        }

    }

    @PostMapping(value = "getUploadFiles")
    @ResponseBody
    public APIResponse getAllUploadFile(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestBody
                                        String params){
        try {
            Map map = MyUtils.getMapFromAPI(params,"userid");
            String curUserId = (String) map.get("userid");
            if(curUserId==null||"".equals(curUserId)){
                throw BusinessException.withErrorCode(ErrorConstant.Auth.NO_USER_ID);
            }
            AttAchDomain[] attResults = attAchService.getAttach(curUserId);
            return APIResponse.success(attResults);
        } catch (BusinessException e){
        return APIResponse.fail(ErrorConstant.Atth.GET_ALL_ATT_UPLOADED_FAIL,e.getErrorCode());
    }
}

    @ApiOperation("mult-file upload")
    @PostMapping(value = "uploadFile")
    @ResponseBody
    public APIResponse fileUploadToCloud(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @ApiParam(name = "file", value="file array", required=true)
                                         @RequestParam(name="file",required = true)
                                         MultipartFile[] files,
                                         //name should be the same as the request parameters
                                         String curUserId){
       try {
           String fileName="";
           request.setCharacterEncoding("utf-8");
           response.setHeader("Content-Type","text/html");
           for(MultipartFile file:files) {
               String[] fileInfo = MyUtils.getFileKey(file.getOriginalFilename());
               fileName = fileInfo[0].replaceFirst("/","");
               String fileExt = fileInfo[1]==null?"":fileInfo[1];
               qiniuCloudService.upload(file,fileName);//upload file on qiniuCloud

               AttAchDomain attAch = new AttAchDomain();
               attAch.setFname(fileName);
               attAch.setFkey(OtherConstant.httpOrHttps +qiniuCloudService.QINIU_UPLOAD_SITE+fileName);
               attAch.setFtype("".equals(fileExt)?
                       MyUtils.isImage(file.getInputStream())? Types.IMAGES.getType() : Types.FILE.getType()
                                                 :fileExt);
               if(curUserId==null||"".equals(curUserId)){
                    throw BusinessException.withErrorCode(ErrorConstant.Auth.NO_USER_ID);
               }
               attAch.setAuthorid(Integer.valueOf(curUserId) );
               //do update attachment
                attAchService.addAttach(attAch);
           }
           return APIResponse.success(OtherConstant.httpOrHttps+this.QINIU_UPLOAD_SITE+fileName);

       }  catch (BusinessException e){
           return APIResponse.fail(ErrorConstant.Atth.UPLOAD_FILE_FAIL,e.getErrorCode());
       }  catch (Exception e) {
           return APIResponse.fail(ErrorConstant.Atth.UPLOAD_FILE_FAIL,e.getMessage());
       }
    }
}
