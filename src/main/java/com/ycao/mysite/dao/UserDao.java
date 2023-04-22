package com.ycao.mysite.dao;

import com.ycao.mysite.model.LoginLogDomain;
import com.ycao.mysite.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * @DESC before update user password, verify
     * @param
     * @return
     * @data 17/05/2022 13:41
     * @author yuan.cao@utbm.fr
     **/
    UserDomain verifyBeforeUpdatePwd(@Param("id") Integer id,
                                     @Param("password") String password);

    void updateUserPwd(@Param("id") Integer id,
                       @Param("password") String password
    );
}
