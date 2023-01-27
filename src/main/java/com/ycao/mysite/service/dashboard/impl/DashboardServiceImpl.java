package com.ycao.mysite.service.dashboard.impl;

import com.ycao.mysite.dao.AutotaskDao;
import com.ycao.mysite.dao.UserDao;
import com.ycao.mysite.model.LogDomain;
import com.ycao.mysite.model.LoginLogDomain;
import com.ycao.mysite.service.dashboard.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements IDashboardService {

    @Autowired
    AutotaskDao autotaskDao;

    @Autowired
    UserDao userDao;

    @Override
    public List<LogDomain> getAllLogs() {
        return autotaskDao.getAllLogs();
    }

    @Override
    public List<LoginLogDomain> getLoginLogs() {
        return userDao.getLoginLog();
    }
}
