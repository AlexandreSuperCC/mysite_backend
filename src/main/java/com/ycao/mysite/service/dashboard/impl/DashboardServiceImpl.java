package com.ycao.mysite.service.dashboard.impl;

import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.dao.AutotaskDao;
import com.ycao.mysite.dao.UserDao;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.LogDomain;
import com.ycao.mysite.model.LoginLogDomain;
import com.ycao.mysite.model.UserDomain;
import com.ycao.mysite.service.dashboard.IDashboardService;
import com.ycao.mysite.utils.MapCache;
import com.ycao.mysite.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements IDashboardService {

    protected MapCache cache = MapCache.single();

    @Autowired
    AutotaskDao autotaskDao;

    @Autowired
    UserDao userDao;

    @Override
    public List<LogDomain> getAllLogs() {
        return autotaskDao.getAllMyLogs();
    }

    @Override
    public List<String> getVisitLogs() {
        List<String> logs = cache.get("visit_site_log");
        return logs==null?new ArrayList():logs;
    }

    @Override
    public List<LoginLogDomain> getLoginLogs() {
        return userDao.getLoginLog();
    }

    @Override
    public void updateUserPwd(Integer id, String name, String oldPassword, String newPassword){

        assert id!=null;
        try{
            this.verifyBeforeUpdatePwd(id,name,oldPassword);
        }catch (BusinessException e){
            throw BusinessException.withErrorCode(e.getErrorCode());
        }
        assert name!=null;
        String pwd = MyUtils.MD5encode(name+newPassword);
        userDao.updateUserPwd(id,pwd);
    }

    /**
     * @DESC verify if the old password is correct before updating new one
     * @param
     * @return
     * @data 17/05/2022 21:56
     * @author yuan.cao@utbm.fr
     **/
    private void verifyBeforeUpdatePwd(Integer id, String name, String password){
        assert id!=null;
        String pwd = MyUtils.MD5encode(name+password);
        UserDomain userDomain = userDao.verifyBeforeUpdatePwd(id,pwd);

        if(userDomain==null){
            throw BusinessException.withErrorCode(ErrorConstant.Auth.OLD_PASSWORD_ERROR);
        }
    }
}
