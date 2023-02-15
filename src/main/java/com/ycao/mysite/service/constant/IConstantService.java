package com.ycao.mysite.service.constant;

import com.ycao.mysite.model.ConstantDomain;

import java.util.List;
import java.util.Map;

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
    void updateConstant(Map map);

}
