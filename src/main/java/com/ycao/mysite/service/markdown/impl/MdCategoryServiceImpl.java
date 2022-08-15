package com.ycao.mysite.service.markdown.impl;

import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.controller.admin.AttAchController;
import com.ycao.mysite.dao.MdCatDao;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.FileCategoryDomain;
import com.ycao.mysite.model.MarkdownFileDomain;
import com.ycao.mysite.service.markdown.IMdCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MdCategoryServiceImpl implements IMdCategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttAchController.class);

    @Autowired
    MdCatDao mdCatDao;

    Map map;

    @Override
//    @CacheEvict(cacheNames = "markdownCaches",allEntries = true,beforeInvocation = true)
    public String doMarkdownService(Map map) throws BusinessException{
        if(map==null||map.size()==0){
            return "fail";
        }
        iniMdData(map);

        if(Boolean.parseBoolean((String) map.get("ifCreate"))){
            checkUniqueFid();
            doCreateMarkdown();
        }else{
            checkExistFile();
            doModifyMarkdown();
        }
       return "success";
    }

    /**
     * when modifying existing file, check if the id is valid or not
     * @param
     * @return
     */
    private void checkExistFile() throws BusinessException{
        String mid = (String) map.get("uniqueMdFileId");
        List<MarkdownFileDomain> res = mdCatDao.verifyMarkdown(mid);
        if((res==null||res.size()==0)){
            throw BusinessException.withErrorCode(ErrorConstant.MdFile.NO_FILE_ID);
        }
    }

    /**
     * when creating new file, make sure the id of the file is not used
     * @param
     * @return
     */
    private void checkUniqueFid() throws BusinessException{
        String mid = (String) map.get("uniqueMdFileId");
        List<MarkdownFileDomain> res = mdCatDao.verifyMarkdown(mid);
        if(!(res==null||res.size()==0)){
            throw BusinessException.withErrorCode(ErrorConstant.MdFile.DUPLICATE_FILE_ID);
        }
    }

    /**
     * put the data into the local variable
     * @param
     * @return
     */
    private void iniMdData(Map map){
        this.map = map;
    }
    /**
     * it is an old markdown file
     * @param
     * @return
     */
    private void doModifyMarkdown(){
        Integer mid = Integer.parseInt((String) map.get("uniqueMdFileId"));
        String mname = (String) map.get("fileName");
        String rate = (String) map.get("fileStar");
        String content = (String) map.get("content");
        String htmlText = (String) map.get("htmlText");
        String nameCategory = (String) map.get("fileCategory");
        String authorId = (String) map.get("userId");
        String cidCategory = mdCatDao.getMdCatIdFromName(nameCategory,authorId);

        mdCatDao.modifyMarkdown(new MarkdownFileDomain(mid,mname,rate,content,cidCategory,htmlText));
    }

    /**
     * it is a new markdown file
     * @param
     * @return
     */
    private void doCreateMarkdown() throws BusinessException{
        Boolean ifNewCat = checkNewCat(map);
        if (ifNewCat) doCreateCat(map);//add action:create new category

        //just do save the markdown
        doSaveNewMarkdown(map);
    }

    /**
     * save Markdown
     * @param
     * @return
     */
    private void doSaveNewMarkdown(Map map) throws BusinessException{
        String mid = (String) map.get("uniqueMdFileId");
        String authorId = (String) map.get("userId");
        String content = (String) map.get("content");
        String rate = (String) map.get("fileStar");
        String mname = (String) map.get("fileName");
        String nameCategory = (String) map.get("fileCategory");
        String htmlText = (String) map.get("htmlText");

        String cidCategory = mdCatDao.getMdCatIdFromName(nameCategory,authorId);
        try{
            mdCatDao.addMarkdown(new MarkdownFileDomain(Integer.parseInt(mid),mname,Integer.parseInt(authorId),rate,content,cidCategory,htmlText));
        }catch (Exception e){
            throw BusinessException.withErrorCode(e.getMessage().substring(0,500));
        }
    }

    /**
     * check out if it is a new category, if is, do insert
     * @param
     * @return
     */
    private Boolean checkNewCat(Map map){
        String userId = (String) map.get("userId");
        String fileCategory = (String) map.get("fileCategory");
        List<FileCategoryDomain> fileCategoryDomainList = mdCatDao.verifyCategory(userId,fileCategory);
        if(fileCategoryDomainList==null||fileCategoryDomainList.size()==0) return true;
        return false;
    }

    private void doCreateCat(Map map){
        if(map==null||map.isEmpty())
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        String userId = (String) map.get("userId");
        String fileCategory = (String) map.get("fileCategory");
        mdCatDao.addMdCategory(new FileCategoryDomain(fileCategory,Integer.parseInt(userId)));
        LOGGER.error("Upload markdown finished");
    }

    public List<FileCategoryDomain> getAllCategory(String userId) throws BusinessException{
        if(userId==null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return mdCatDao.getAllCategory(Integer.parseInt(userId));
    }

    /**
     * url text is like ![hello](https://gimg2.baidu.com/image_search/...)
     * get all urls which should be uploaded to on-line service [qiniucloudservice]
     * using regular expression
     * @param raw markdown content
     * @return arrays of url
     */
//    private String[] toUploadPhotoUrl(String rawContent){
//        // 按指定模式在字符串查找
//        String pattern1 = "![(.*)\)";
//        // 创建 Pattern 对象
//        Pattern r1 = Pattern.compile(pattern1);
//        // 现在创建 matcher 对象
//        Matcher m1 = r1.matcher(res);
//        if (m1.find()) {
//            return new String[]{m1.group(1),m2.group(1)};
//        } else {
//            return null;
//        }
//        return null;
//    }
}
