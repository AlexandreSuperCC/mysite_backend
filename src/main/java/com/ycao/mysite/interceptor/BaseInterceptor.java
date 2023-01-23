package com.ycao.mysite.interceptor;

import com.ycao.mysite.constant.HttpStatus;
import com.ycao.mysite.service.user.UserService;
import com.ycao.mysite.utils.IPKit;
import com.ycao.mysite.utils.Tools;
import com.ycao.mysite.utils.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user-agent";

    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        LOGGER.info("UserAgent: {}",request.getHeader(USER_AGENT));
        LOGGER.info("User access address: {}, coming address: {}",url, IPKit.getIpAddrByRequest(request));

        // 如果不是映射到方法直接通过,可以访问资源.
        // if the action is uploading file, it will not come through the front-end interceptors!!!, so let it pass
        if(url.startsWith("/login")
                ||"/baseData".equals(url)
                ||"/logout".equals(url)
                ||url.startsWith("/index")
                ||url.contains("/uploadFile")
                ||(!(handler instanceof HandlerMethod)
                //不拦截swagger
                || url.contains("/swagger") || url.contains("/webjars/")
        )){
            return true;
        }

        // is null => return un error
//        String token = request.getHeader("token");
        String objStr = request.getHeader("Authorization");
        if(objStr==null){
            return false;
        }
        String token = Tools.getTokenStr(objStr);

        if(null==token||"".equals(token.trim())){
            response.sendError(HttpStatus.NO_TOKEN);
            return false;
        }

//        LOGGER.info("Here is your token: "+token+", and now we do the verification...");
        Boolean result = JwtUtil.verify(token);
        if(result){
            return true;
        }else{
            response.sendError(HttpStatus.JWT_UNAUTHORIZED);
            return false;
        }
    }
}
