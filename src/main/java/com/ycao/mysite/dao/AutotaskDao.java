package com.ycao.mysite.dao;

import com.ycao.mysite.model.LogDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AutotaskDao {
    int realDeleteAtt();
    int realDeleteMd();
    int realDeleteCat();
    int addALog(Map map);
    List<LogDomain> getAllMyLogs();
}
