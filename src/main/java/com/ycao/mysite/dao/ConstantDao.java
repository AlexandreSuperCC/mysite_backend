package com.ycao.mysite.dao;

import com.ycao.mysite.model.ConstantDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface ConstantDao {
    /**
     * need to add @Param("")
     * get dedicated constant
     * @param
     * @return
     */
    public List<Map> getMyConstant(@Param("userId") String userId, @Param("domain") String domain);
    void updateConstant(ConstantDomain constantDomain);
}
