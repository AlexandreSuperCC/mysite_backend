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

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDomain login(String username, String password) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);

        String pwd = MyUtils.MD5encode(username+password);
        UserDomain user = userDao.getUserInfoByCond(username,pwd);
        if(null == user)
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);

        return user;
    }

    @Override
    public UserDomain getUserInfoById(Integer uid) {
        return userDao.getUserInfoById(uid);
    }
}
