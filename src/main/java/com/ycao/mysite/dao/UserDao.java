package com.ycao.mysite.dao;

import com.ycao.mysite.model.LoginLogDomain;
import com.ycao.mysite.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by ycao on 10/24
 */
@Mapper
public interface UserDao {
    /**
     * get userinfo according to username and password
     * @param
     * @return
     */
    UserDomain getUserInfoByCond(@Param("username") String username,@Param("password") String password);

    /**
     * get user's info according to his primary key
     * @param
     * @return
     */
    UserDomain getUserInfoById(@Param("uid") Integer uid);

    int insertLoginInfo(Map map);

    List<LoginLogDomain> getLoginLog();
}
