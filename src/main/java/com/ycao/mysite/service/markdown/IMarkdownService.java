package com.ycao.mysite.service.markdown;

import com.ycao.mysite.model.MarkdownFileDomain;

import java.util.List;
/**
 * used in index/article get all article,
 * maybe in the future i will ameliorate it by using lazy loading
 * Created by ycao on
 */
public interface IMarkdownService {
    public List<MarkdownFileDomain> getAllMarkdown();
    public List<MarkdownFileDomain> getAllMarkdownAndPrivate();
    public void deleteOneMarkdown(String mid);
    public void deleteCategoryFiles(String cname,String userId);
}
