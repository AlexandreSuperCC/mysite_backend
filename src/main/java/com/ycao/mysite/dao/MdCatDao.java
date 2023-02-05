package com.ycao.mysite.dao;

import com.ycao.mysite.model.FileCategoryDomain;
import com.ycao.mysite.model.MarkdownFileDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    public List<MarkdownFileDomain> getAllFilesWithCatName();
    /**
     * getting all category
     * @param
     * @return
     */
    public List<FileCategoryDomain> getAllCategory(@Param("userId") Integer userId);

    /**
     * verify if a markdown file is existed or new
     * @param
     * @return
     */
    public List<MarkdownFileDomain> verifyMarkdown(@Param("mid") String mid);

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
    public List<FileCategoryDomain> verifyCategory(@Param("userId") String userId, @Param("fileCategory") String fileCategory);
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
    public String getMdCatIdFromName(@Param("cname") String cname,@Param("userId") String userId);
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
    public void deleteOneMarkdown(@Param("mid") String mid);
    /**
     * delete one category and all the files in this category
     * @param
     * @return
     */
    public void deleteCategoryFiles(@Param("cname") String cname, @Param("userId") Integer userId);

}
