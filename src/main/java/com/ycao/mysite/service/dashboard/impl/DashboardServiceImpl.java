package com.ycao.mysite.service.dashboard.impl;

import com.ycao.mysite.dao.AutotaskDao;
import com.ycao.mysite.dao.UserDao;
import com.ycao.mysite.model.LogDomain;
import com.ycao.mysite.model.LoginLogDomain;
import com.ycao.mysite.service.dashboard.IDashboardService;
import com.ycao.mysite.utils.MapCache;
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
}
