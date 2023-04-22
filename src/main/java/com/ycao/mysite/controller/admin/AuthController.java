package com.ycao.mysite.controller.admin;


/**
 * login controller
 * where we get connected with vue
 * Created by ycao on 8/24
 */
import com.ycao.mysite.model.LoginUser;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.UserDomain;
import com.ycao.mysite.service.user.UserService;
import com.ycao.mysite.utils.APILoginResponse;
import com.ycao.mysite.utils.APIResponse;
import com.ycao.mysite.utils.IPKit;
import com.ycao.mysite.utils.MapCache;
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

    /**
     * used for stock cache, for example, how many times the wrong login attempt before blocking the ip
     */
    protected MapCache cache = MapCache.single();


    @Autowired
    private UserService userService;

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
        String realIp = IPKit.getIpAddr(request);
        String token;
        String finalIp = realIp==""?ip:realIp;

        Integer error_count = cache.hget("login_error_count",finalIp);

        if (error_count!=null&&error_count > 3) {// debug proposal, should be restored
            String msg = "You entered the wrong password more than 3 times, please try again after 10 minutes";
            return APILoginResponse.failLogin(msg);
        }

        try{
            String username=loginUser.getUsername();
            String password=loginUser.getPassword();
            LOGGER.info("user: ["+username+"] pwd: ["+password+"] arrives");

            UserDomain userInfo = userService.login(username,password,finalIp);
            //no exception so succeed=>
            // depreciated, replace with token, but used again to save the user
            //delete by ycao 20222028 session消失bug 域名问题……调试的时候一个是127.0.0.1一个是localhost
//            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY,userInfo.getUid());
//            request.getSession().setMaxInactiveInterval(60*60);//session 60m expired

            token = JwtUtil.sign(userInfo.getUsername(),userInfo.getUid().toString());
            if(token!=null){
                if(userInfo.getUid()==null){
                    return APILoginResponse.successLogin(token,"",-1,"");
                }
                return APILoginResponse.successLogin(token,
                        String.valueOf(userInfo.getUid()),
                        userInfo.getRole(),
                        userInfo.getUsername()
                );
            }
        }catch (Exception e){
            String msg = "login fails";

            if(e instanceof BusinessException){
                error_count = null == error_count ? 1 : error_count + 1;

                cache.hset("login_error_count", finalIp,error_count, 10 * 60); // add ip filter

                String errMes = e.getMessage()!=null?e.getMessage():((BusinessException) e).getErrorCode();
                LOGGER.error(finalIp+" : "+errMes);
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
