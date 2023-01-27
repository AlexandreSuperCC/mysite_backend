package com.ycao.mysite.service.dashboard;

import com.ycao.mysite.model.LogDomain;
import com.ycao.mysite.model.LoginLogDomain;

import java.util.List;

public interface IDashboardService {
    List<LogDomain> getAllLogs();
    List<LoginLogDomain> getLoginLogs();
}
