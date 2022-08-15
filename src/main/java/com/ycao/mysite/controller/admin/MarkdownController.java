package com.ycao.mysite.controller.admin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.FileCategoryDomain;
import com.ycao.mysite.service.markdown.IMdCategoryService;
import com.ycao.mysite.utils.APIResponse;
import com.ycao.mysite.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * controller for part "write your markdown"
 * Created by ycao on 20211211
 */
@Controller
@RequestMapping(value = "/home")
public class MarkdownController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkdownController.class);

    @Autowired
    IMdCategoryService iMdCategoryService;

    @ResponseBody
    @GetMapping(value = "getAllCategory")
    public APIResponse getALlCategory(HttpServletRequest request,HttpServletResponse response,
                                      @RequestParam(name = "userId", required = false)
                                      String userId){
        try{
            List<FileCategoryDomain> resList = iMdCategoryService.getAllCategory(userId);
            if(resList==null||resList.size()==0){
                return APIResponse.success(new FileCategoryDomain[0]);
            }
            FileCategoryDomain[] resArr = resList.toArray(new FileCategoryDomain[resList.size()]);
            return APIResponse.success(resArr);
        }catch (BusinessException exception){
            return APIResponse.fail(exception.getErrorCode());
        }

    }

    @ResponseBody
    @PostMapping(value = "saveMarkdown")
    public APIResponse saveMarkdownRT(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody String params){
        try {
            Map map = MyUtils.getMapFromAPI(params,"htmlText","ifCreate","uniqueMdFileId","userId","content","fileStar","fileName","fileCategory");
            String doRes = iMdCategoryService.doMarkdownService(map);
            if(doRes==null||"".equals(doRes)||"fail".equals(doRes)){
                return APIResponse.fail("Save Markdown file fails");
            }
            return APIResponse.success();
        }catch (BusinessException businessException){
            return APIResponse.fail(businessException.getErrorCode());
        }catch (Exception e){
            return APIResponse.fail(e.getMessage());
        }
    }

}
