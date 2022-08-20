package com.ycao.mysite.service.user.impl;

import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.dao.UserDao;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.UserDomain;
import com.ycao.mysite.service.user.UserService;
import com.ycao.mysite.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDomain login(String username, String password, String ip) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);

        String pwd = MyUtils.MD5encode(username+password);
        UserDomain user = userDao.getUserInfoByCond(username,pwd);
        if(null == user)
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);
        Map<String,String> logMap = new HashMap<String,String>(){{
            put("table","t_login_log");
            put("uid",String.valueOf(user.getUid()));
            put("ip",ip);
        }};
        userDao.insertLoginInfo(logMap);
        return user;
    }

    @Override
    public UserDomain getUserInfoById(Integer uid) {
        return userDao.getUserInfoById(uid);
    }
}
