package com.ycao.mysite.service.constant.impl;

import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.dao.ConstantDao;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.ConstantDomain;
import com.ycao.mysite.model.MarkdownFileDomain;
import com.ycao.mysite.service.constant.IConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        return constantDao.getMyConstant(userId,domain);
    }

    @Override
    public void updateConstant(Map map) {
        String id = (String) map.get("id");
        String cid = (String) map.get("cid");
        String content = (String) map.get("content");

        ConstantDomain cd = new ConstantDomain();
        cd.setContent(content);
        cd.setCreatorId(cid);
        cd.setId(Integer.valueOf(id));
        try{
            constantDao.updateConstant(cd);
        }catch (Exception e){
            throw BusinessException.withErrorCode(e.getMessage().substring(0,500));
        }
    }
}
