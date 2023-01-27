package com.ycao.mysite.dao;

import com.ycao.mysite.model.LogDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AutotaskDao {
    int realDeleteAttach();
    int realDeleteMarkdown();
    int realDeleteCategory();
    int addLog(Map map);
    List<LogDomain> getAllLogs();
}
