package com.ycao.mysite.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ycao.mysite.constant.ErrorConstant;
import com.ycao.mysite.constant.WebConst;
import com.ycao.mysite.exception.BusinessException;
import com.ycao.mysite.model.UserDomain;
import com.ycao.mysite.utils.security.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * Created by ycao on 10/24
 */
public class MyUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyUtils.class);


    /**
     * md5 encryption
     * @param string should be handled
     * @return string handled
     */
    public static String MD5encode(String source){
        if(StringUtils.isBlank(source)) return null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] encode = messageDigest.digest(source.getBytes());
            StringBuilder hexString = new StringBuilder();
            for(byte anEncode:encode){
                String hex = Integer.toHexString(0xff & anEncode );
                if(hex.length()==1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException ignored){

        }
        return "";
    }

    /**
     * used in interceptor and controller to set and get session
     * @param
     * @return
     */
    public static UserDomain getLoginUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(null==session){
            return null;
        }
        return (UserDomain) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
    }

    /**
     * set the cookie of remembering the code
     * @param
     * @return
     */
    public static void setCookie(HttpServletResponse response,Integer uid){
        try{
            String val = Tools.enAes(uid.toString(), WebConst.AES_SALT);
            boolean isSSL = false;
            Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE,val);
            cookie.setPath("/");
            cookie.setMaxAge(60*30);
            cookie.setSecure(isSSL);
            response.addCookie(cookie);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * get the position for upload
     * @param
     * @return
     */

    /**
     * used in uploading file
     * make the upload directory and change the name of the file to a unique key
     * @param example VisaCertifie-V10356522419.jpg
     * @return example /upload/2021/11/3oe8s7v9j0j09riqhtnfot2tpi.jpg
     */
    public static String[] getFileKey(String fileName){
        String prefix = "/"+(DateKit.dateFormat(new Date(),"yyyy/MM"));
        fileName = StringUtils.trimToNull(fileName);
        if(fileName==null){
            return new String[]{prefix+"/"+ UUID.randomUUID()+"."+null,""};
        }else{
            fileName = fileName.replace('\\','/');
            fileName = fileName.substring(fileName.lastIndexOf("/")+1);
            int index = fileName.lastIndexOf(".");
            String extName = null;
            if(index>=0){
                extName = StringUtils.trimToNull(fileName.substring(index+1));
            }
            String realFileName = fileName.substring(0,index);
            return new String[]{prefix+"/"+getRandomID()+"-"+realFileName+"."+(extName==null?"":(extName))
                    ,extName};
        }


    }

    /**
     * @DESC Generate random ID: current hour, minute, second + two random numbers
     * @param
     * @return
     * @data 16/05/2022 11:44
     * @author yuan.cao@utbm.fr
     **/
    public static Integer getRandomID() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("HHmmss");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        int rannum = (int)(1+Math.random() * 99);// 获取随机数1-99

        return Integer.valueOf(str + rannum);// 当前时间
    }

    /**
     * 判断文件是否是图片类型
     *
     * @param imageFile
     * @return
     */
    public static boolean isImage(InputStream imageFile) {
        try {
            Image img = ImageIO.read(imageFile);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将json字符串转化成json对象
     * parse the incoming json when using the API
     * {
     *     "param":{
     *
     *     }
     * }
     * @param
     * @return
     */
    public static Map getMapFromAPI(String params,String... requireAttributes) throws BusinessException{
        Map resMap = new HashMap();
        if(params==null||"".equals(params)){
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        JsonParser jp = new JsonParser();
        //将json字符串转化成json对象
        JsonObject jo = jp.parse(params).getAsJsonObject();
        //获取message对应的值
        JsonObject jsonObject = jo.get("param").getAsJsonObject();
        for (String requireAttribute:requireAttributes){
            resMap.put(requireAttribute,jsonObject.get(requireAttribute).getAsString());
        }
        return resMap;
    }

    /**
     * @DESC 0 pass -1 no token -2 wrong token
     * @return
     * @date 2023-03-12 11:41:11
     * @author yuan.cao@utbm.fr
     **/
    public static Integer checkTokenFromRequest(HttpServletRequest request){
        String objStr = request.getHeader("Authorization");
        if(objStr==null){
            return -2;
        }
        String token = Tools.getTokenStr(objStr);

        if(null==token||"".equals(token.trim())){
            return -2;
        }

        return JwtUtil.verify(token)?0:-1;
    }
}
