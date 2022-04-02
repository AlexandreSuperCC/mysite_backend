package com.ycao.mysite.dao;

import com.ycao.mysite.model.AttAchDomain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttAchDao {

    /**
     * used in upload file when in 'upload your file' page
     * @param
     * @return
     */
    int doUploadFile(AttAchDomain attAchDomain);
    /**
     * get all files uploaded
     * @param
     * @return
     */
    AttAchDomain[] getAllFilesUpload(String authorid);
    /**
     * delete one file
     * @param
     * @return
     */
    int deleteOneFile(String fname);
}
