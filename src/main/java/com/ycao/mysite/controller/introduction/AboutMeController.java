package com.ycao.mysite.controller.introduction;

import com.ycao.mysite.controller.BaseController;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.service.constant.IConstantService;
import com.ycao.mysite.utils.APIResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api("constant related interface")
@Controller
@RequestMapping(value = "/aboutMe")
public class AboutMeController extends BaseController {
    @Autowired
    IConstantService iConstantService;

    @Override
    public APIResponse getBaseData(HttpServletRequest request, String userId,String domain) {
        try {
            List<Map> resListMap = iConstantService.getConstantFromDomain(userId,domain);
            if(resListMap==null||resListMap.isEmpty()){
                return APIResponse.success("");
            }
            return APIResponse.success(resListMap);
        }catch (BusinessException businessException)
        {
            return APIResponse.fail(businessException.getErrorCode());
        }
    }
}
