package com.ycao.mysite.constant;

/**
 *
 * Created by ycao on 10/24
 */
public interface ErrorConstant {
    interface Common{
        static final String PARAM_IS_EMPTY = "the parameter is empty";
    }
    interface Auth {
        static final String USERNAME_PASSWORD_IS_EMPTY = "name and password must not be empty";
        static final String USERNAME_PASSWORD_ERROR = "username doesn't exist or password is incorrect";
        static final String NO_USER_ID = "no user's id error";
    }
    interface Atth {
        static final String ADD_NEW_ATT_FAIL = "adding attachment fails";
        static final String GET_ALL_ATT_UPLOADED_FAIL = "getting all attachment uploaded fails";
        static final String UPDATE_ATT_FAIL =  "";
        static final String DELETE_ATT_FAIL = "";
        static final String UPLOAD_FILE_FAIL = "uploading attachment fails";
    }
    interface MdFile{
        static final String DUPLICATE_FILE_ID = "the id is already used, please try another one";
        static final String NO_FILE_ID = "there is no file with this id, please check";
    }
}
