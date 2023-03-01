package com.ycao.mysite.service.markdown.impl;

import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.constant.OtherConstant;
import com.ycao.mysite.dao.MdCatDao;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.MarkdownFileDomain;
import com.ycao.mysite.service.markdown.IMarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MarkdownServiceImpl implements IMarkdownService {
    @Autowired
    MdCatDao mdCatDao;
    //@Cacheable(cacheNames = "markdownCaches",key = "#root.methodName + ':[' + #userId + ']'")
    @Override
    public List<MarkdownFileDomain> getAllMarkdown() {
        return mdCatDao.getAllFilesWithCatName();
    }

    //@CacheEvict(cacheNames = "markdownCaches",allEntries = true,beforeInvocation = true)
    @Override
    public void deleteOneMarkdown(String mid) {
        mdCatDao.deleteOneMarkdown(mid);
    }

    @Override
    //@CacheEvict(cacheNames = "markdownCaches",allEntries = true,beforeInvocation = true)
    public void deleteCategoryFiles(String cname,String userId) {
        mdCatDao.deleteCategoryFiles(cname, OtherConstant.myId);
    }
}
