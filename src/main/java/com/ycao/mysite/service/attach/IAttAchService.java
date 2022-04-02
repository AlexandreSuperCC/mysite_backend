package com.ycao.mysite.service.attach;

import com.ycao.mysite.model.AttAchDomain;

/**
 * 附件服务层接口
 * Created by ycao on 20211201
 */
public interface IAttAchService {

    /**
     * 添加单个附件信息
     * @param
     * @return
     */
    public void addAttach(AttAchDomain attAchDomain);
    /**
     * getting all files uploaded to show in the page
     * @param
     * @return
     */
    public AttAchDomain[] getAttach(String authorid);
    /**
     * delete one file
     * @param
     * @return
     */
    public int deleteOneFile(String fname);
}
