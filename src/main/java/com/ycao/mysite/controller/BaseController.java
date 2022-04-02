package com.ycao.mysite.controller;

import com.ycao.mysite.controller.admin.AuthController;
import com.ycao.mysite.utils.APIResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public abstract class BaseController {
    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @ApiOperation("constant")
    @ResponseBody
    @GetMapping(value = "/baseData")
    public abstract APIResponse getBaseData(HttpServletRequest request,
                                                @RequestParam(value = "userId",required = true)
                                                @ApiParam(name="userId",value = "user's id",required = true)
                                                    String userId,
                                                @ApiParam(name="domain",value = "constant's domain",required = false)
                                                @RequestParam(value = "domain",required = true)
                                                    String domain);
}
