package com.ycao.mysite.controller.admin;


/**
 * login controller
 * where we get connected with vue
 * Created by ycao on 8/24
 */
import com.ycao.mysite.constant.WebConst;
import com.ycao.mysite.controller.BaseController;
import com.ycao.mysite.model.LoginUser;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.UserDomain;
import com.ycao.mysite.service.user.UserService;
import com.ycao.mysite.utils.APILoginResponse;
import com.ycao.mysite.utils.APIResponse;
import com.ycao.mysite.utils.IPKit;
import com.ycao.mysite.utils.security.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("login related interface")
@Controller
@RequestMapping(value = "/")
public class AuthController {
    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @ApiOperation("login")
    @ApiImplicitParam(name = "form", value = "user login form", required = true, dataType = "LoginUser")
    @PostMapping(value = "/login")
    @ResponseBody
    public APILoginResponse toLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody LoginUser loginUser//should be an object
//            @ApiParam(name="username",value = "user's name",required = true)
//            @RequestParam(name="username",required = true)
//            String username,
//            @ApiParam(name="password",value = "user's password",required = true)
//            @RequestParam(name="password",required = true)
//            String password,
//            @ApiParam(name="remember_me",value = "remember_me",required = false)
//            @RequestParam(name="remember_me",required = false)
//            String remember_me
    ){
        String ip = IPKit.getIpAddrByRequest(request);//gei ip and solve the bug of filtering the cache when logging
        String token = null;
        try{
            String username=loginUser.getUsername();
            String password=loginUser.getPassword();
            LOGGER.info("user: ["+username+"] pwd: ["+password+"] arrives");
            UserDomain userInfo = userService.login(username,password);
            //no exception so succeed=>
            // depreciated, replace with token, but used again to save the user
            //delete by ycao 20222028 session消失bug 域名问题……调试的时候一个是127.0.0.1一个是localhost
//            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY,userInfo.getUid());
//            request.getSession().setMaxInactiveInterval(60*60);//session 60m expired

            token = JwtUtil.sign(userInfo.getUsername(),userInfo.getUid().toString());
            if(token!=null){
                if(userInfo.getUid()==null){
                    return APILoginResponse.successLogin(token,"");
                }
                return APILoginResponse.successLogin(token,String.valueOf(userInfo.getUid()));
            }
        }catch (Exception e){
            String msg = "login fails";
            if(e instanceof BusinessException){
                String errMes = e.getMessage()!=null?e.getMessage():((BusinessException) e).getErrorCode();
                LOGGER.error(ip+" : "+errMes);
                msg = ((BusinessException) e).getErrorCode();
            }else{
                LOGGER.error(msg,e);
            }
            return APILoginResponse.failLogin(msg);
        }
        return APILoginResponse.failLogin("make token error");
    }

    @GetMapping(value = "/logout")
    @ResponseBody
    public APIResponse toLogout(HttpServletRequest request){
        //delete by ycao 20222028 session消失bug 域名问题……调试的时候一个是127.0.0.1一个是localhost
//        LOGGER.info("userid: ["+request.getSession().getAttribute(WebConst.LOGIN_SESSION_KEY)+"] logs out");
//        if(request.getSession().getAttribute(WebConst.LOGIN_SESSION_KEY)==null){
//            return APIResponse.fail("no session required");
//        }
//        request.getSession().removeAttribute(WebConst.LOGIN_SESSION_KEY);
        return APIResponse.success("logout success");
    }

}
