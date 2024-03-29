package com.ycao.mysite.interceptor;

import com.ycao.mysite.constant.HttpStatus;
import com.ycao.mysite.service.user.UserService;
import com.ycao.mysite.utils.*;
import com.ycao.mysite.utils.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user-agent";
    protected MapCache cache = MapCache.single();

    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    @Value("${server.servlet.context-path}")
    public String PREFIX_URL;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        LOGGER.info("UserAgent: {}",request.getHeader(USER_AGENT));
        LOGGER.info("User access address: {}, coming address: {}",url, IPKit.getIpAddrByRequest(request));

        if("/api/baseData".equals(url)){
            String realIp = IPKit.getIpAddr(request);
            String finalIp = realIp==""?IPKit.getIpAddrByRequest(request):realIp;
            String nowLog = "["+finalIp+"] ["+ DateKit.getToday("yyyy-MM-dd HH:mm:ss")+"]";
            List<String> logs = cache.get("visit_site_log");
            if(logs==null){
                logs = new ArrayList();
            }
            logs.add(nowLog);
            cache.set("visit_site_log",logs, 30 * 24 * 60 * 60);
        }

        // 如果不是映射到方法直接通过,可以访问资源.
        // if the action is uploading file, it will not come through the front-end interceptors!!!, so let it pass
        if(url.startsWith(PREFIX_URL+"/login")
                ||url.startsWith(PREFIX_URL+"/index")
                ||(PREFIX_URL+"/baseData").equals(url)
                ||(PREFIX_URL+"/logout").equals(url)
                ||url.contains("/uploadFile")
                ||(!(handler instanceof HandlerMethod)
                //不拦截swagger
                || url.contains("/swagger") || url.contains("/webjars/")
        )){
            return true;
        }

        // is null => return un error
//        String token = request.getHeader("token");

        Integer res = MyUtils.checkTokenFromRequest(request);
        switch (res){
            case 0:
                return true;
            case -2:
                response.sendError(HttpStatus.NO_TOKEN);
                return false;
            case -1:
                response.sendError(HttpStatus.JWT_UNAUTHORIZED);
                return false;
            default:
                break;
        }
        return false;
    }
}
