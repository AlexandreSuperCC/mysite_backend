package com.ycao.mysite.service.user;

import com.ycao.mysite.model.UserDomain;
import org.springframework.stereotype.Service;
/**
 *
 * Created by ycao on 10/24
 */
@Service
public interface UserService {

    UserDomain login(String username,String password,String ip);

    /**
     * get user's info according to his primary key
     * @param
     * @return
     */
    UserDomain getUserInfoById(Integer uid);
}
