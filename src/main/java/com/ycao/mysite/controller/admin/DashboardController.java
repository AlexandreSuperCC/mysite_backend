package com.ycao.mysite.controller.admin;

import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.LogDomain;
import com.ycao.mysite.model.LoginLogDomain;
import com.ycao.mysite.service.constant.IConstantService;
import com.ycao.mysite.service.dashboard.IDashboardService;
import com.ycao.mysite.utils.APIResponse;
import com.ycao.mysite.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/db")
public class DashboardController {

    @Autowired
    IConstantService iConstantService;

    @Autowired
    private IDashboardService iDashboardService;

    @GetMapping(value = "/allLogs")
    @ResponseBody
    public APIResponse getAllLogs(){
        try{
            List<LogDomain> resList = iDashboardService.getAllLogs();
            if(resList==null||resList.size()==0){
                return APIResponse.success(new LogDomain[0]);
            }
            LogDomain[] resArr = resList.toArray(new LogDomain[resList.size()]);
            return APIResponse.success(resArr);
        }catch (BusinessException businessException){
            return APIResponse.fail(businessException.getErrorCode());
        }catch (Exception e){
            return APIResponse.fail(e.getMessage());
        }
    }

    @GetMapping(value = "/visitLogs")
    @ResponseBody
    public APIResponse getVisitLogs(){
        try{
            List<String> resList = iDashboardService.getVisitLogs();
            return APIResponse.success(resList);
        }catch (BusinessException businessException){
            return APIResponse.fail(businessException.getErrorCode());
        }catch (Exception e){
            return APIResponse.fail(e.getMessage());
        }
    }

    @GetMapping(value = "/loginLogs")
    @ResponseBody
    public APIResponse getLoginLogs(){
        try{
            List<LoginLogDomain> resList = iDashboardService.getLoginLogs();
            if(resList==null||resList.size()==0){
                return APIResponse.success(new LoginLogDomain[0]);
            }
            LoginLogDomain[] resArr = resList.toArray(new LoginLogDomain[resList.size()]);
            return APIResponse.success(resArr);
        }catch (BusinessException businessException){
            return APIResponse.fail(businessException.getErrorCode());
        }catch (Exception e){
            return APIResponse.fail(e.getMessage());
        }
    }
    @ResponseBody
    @PostMapping(value = "/updateConstant")
    public APIResponse updateMyConstant(@RequestBody String params){
        try {
            Map map = MyUtils.getMapFromAPI(params,"id","cid","content");
            iConstantService.updateConstant(map);
            return APIResponse.success();
        }catch (BusinessException businessException){
            return APIResponse.fail(businessException.getErrorCode());
        }catch (Exception e){
            return APIResponse.fail(e.getMessage());
        }
    }
}
