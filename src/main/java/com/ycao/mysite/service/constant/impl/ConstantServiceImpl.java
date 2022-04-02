package com.ycao.mysite.service.constant.impl;

import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.dao.ConstantDao;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.service.constant.IConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ConstantServiceImpl implements IConstantService {
    @Autowired
    private ConstantDao constantDao;

    @Override
    public List getConstantFromDomain(String userId, String domain) throws BusinessException{
        if(userId==null){
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        if(domain==null)
            domain = "";
        return constantDao.getConstant(userId,domain);
    }
}
