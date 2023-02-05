package com.ycao.mysite.service.attach.impl;

import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.controller.admin.AttAchController;
import com.ycao.mysite.dao.AttAchDao;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.AttAchDomain;
import com.ycao.mysite.service.attach.IAttAchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 附件服务层接口实现
 * Created by ycao on 20211201
 */
@Service
public class AttAchServiceImpl implements IAttAchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttAchController.class);

    @Autowired
    AttAchDao attAchDao;

    @Override
    //@CacheEvict(cacheNames = "attachCaches",allEntries = true,beforeInvocation = true)
    public void addAttach(AttAchDomain attAchDomain) {
        if(attAchDomain==null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        int uploadRes = attAchDao.doUploadAtt(attAchDomain);
        LOGGER.error("Upload ["+uploadRes+"] file/files finished");
    }

    //@Cacheable(cacheNames = "attachCaches",key = "#root.methodName+':['+#authorid+']'")
    @Override
    public AttAchDomain[] getAttach(String authorid) {
        if(authorid==null||"".equals(authorid)){
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        AttAchDomain[] allFiles = attAchDao.getAllFilesUpload(authorid);
        return allFiles;
    }

    //@CacheEvict(cacheNames = "attachCaches",allEntries = true,beforeInvocation = true)
    @Override
    public int deleteOneFile(String fname) {
        return attAchDao.deleteOneFile(fname);
    }
}
