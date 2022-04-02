package com.ycao.mysite.dao;

import com.ycao.mysite.model.FileCategoryDomain;
import com.ycao.mysite.model.MarkdownFileDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MdCatDao {
    /**
     * get all files
     * pay attention! we get directly the name of the category instead of its id in the property 'pkCategory'
     * we can do this because in the frontend page, we can not create two categories with the same name
     * @param
     * @return
     */
    public List<MarkdownFileDomain> getAllFilesWithCatName(Integer authorId);
    /**
     * getting all category
     * @param
     * @return
     */
    public List<FileCategoryDomain> getAllCategory(Integer userId);

    /**
     * verify if a markdown file is existed or new
     * @param
     * @return
     */
    public List<MarkdownFileDomain> verifyMarkdown(String mid);

    /**
     * modify an existing markdown file
     * @param
     * @return
     */
    public void modifyMarkdown(MarkdownFileDomain markdownFileDomain);
    /**
     * verify if a category exists
     * @param
     * @return
     */
    public List<FileCategoryDomain> verifyCategory(String userId,String fileCategory);
    /**
     * create new category for the markdown
     * @param
     * @return
     */
    public void addMdCategory(FileCategoryDomain fileCategoryDomain);

    /**
     * serve for addMarkdown: get the foreign key of markdown category
     * @param
     * @return
     */
    public String getMdCatIdFromName(String cname);
    /**
     * create new markdown
     * @param
     * @return
     */
    public Integer addMarkdown(MarkdownFileDomain markdownFileDomain);
    /**
     * delete one file
     * @param
     * @return
     */
    public void deleteOneFile(String mid);
    /**
     * delete one category and all the files in this category
     * @param
     * @return
     */
    public void deleteCategoryFiles(String cname,Integer userId);

}
