package com.ycao.mysite.controller.home;

import com.ycao.mysite.controller.admin.AuthController;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.MarkdownFileDomain;
import com.ycao.mysite.service.markdown.IMarkdownService;
import com.ycao.mysite.service.user.UserService;
import com.ycao.mysite.utils.APIResponse;
import io.swagger.annotations.Api;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Api("Home related interface")
@Controller
@RequestMapping(value = "/home")
public class HomeController{
    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @Autowired
    private IMarkdownService iMarkdownService;

    @ResponseBody
    @GetMapping(value="/article")
    public APIResponse toArticle(HttpServletRequest request,
                                 @RequestParam(value = "userId",required = true)
                                 String userId
    ){
        try {
//            LOGGER.info("coming into "+request.getRequestURI());
            List<MarkdownFileDomain> files = iMarkdownService.getAllMarkdown(userId);
            MarkdownFileDomain[] filesArray;
            if(files==null||files.isEmpty()){
                filesArray = new MarkdownFileDomain[0];
//                files=new ArrayList<>();
            }else{
                filesArray= files.toArray(new MarkdownFileDomain[0]);
            }
//            return APIResponse.success(files);
            return APIResponse.success(filesArray);
        }catch (BusinessException businessException){
            return APIResponse.fail(businessException.getErrorCode());
        }catch (Exception e){
            return APIResponse.fail(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping(value = "deleteOneFile")
    public APIResponse deleteOneArticle(HttpServletRequest request,
                                        @RequestParam(value = "mid",required = false)
                                        String mid
                                        ){
        iMarkdownService.deleteOneFile(mid);
        return APIResponse.success();
    }

    @ResponseBody
    @GetMapping(value = "deleteCatFiles")
    public APIResponse deleteCategoryAndFiles(HttpServletRequest request,
                                              @RequestParam(value = "cname",required = false)
                                                          String cname,
                                              @RequestParam(value = "userId",required = false)
                                                          String userId
    ){
        iMarkdownService.deleteCategoryFiles(cname,userId);
        return APIResponse.success();
    }


}
