package com.ycao.mysite.service.constant;

import java.util.List;

/**
 * get frontend-needed constant from backend
 * Created by ycao on 2021-12-25 13:00:05
 */

public interface IConstantService {
    /**
     *
     * @param
     * @return
     */
    public List getConstantFromDomain(String userId, String domain);
}
