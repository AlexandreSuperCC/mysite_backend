package com.ycao.mysite.service.markdown;

import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.FileCategoryDomain;

import java.util.List;
import java.util.Map;

/**
 * service for the category of the markdown file
 * Created by ycao on
 */
public interface IMdCategoryService {

    public String doMarkdownService(Map map);
    public List<FileCategoryDomain> getAllCategory(String userId) throws BusinessException;
}
