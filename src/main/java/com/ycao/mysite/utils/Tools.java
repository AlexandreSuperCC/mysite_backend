package com.ycao.mysite.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * tool class
 * Created by ycao on 10/24
 */
public class Tools {
    public static String enAes(String data,String key) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),"AES");
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        Base64.Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString(encryptedBytes);
        return encode;
    }

    /**
     * String to token
     * @param
     * @return String
     */
    @Deprecated
    public static String getTokenStrOld(String str) {
        String pattern = "token:(.*?)$";
        Pattern r1 = Pattern.compile(pattern);
        Matcher m1 = r1.matcher(str);
        List<String> resList = new ArrayList<>();
        while(m1.find()){
            resList.add(m1.group(1).trim());
        }
        if(resList.size()!=1){
            return null;
        }else{
            String res = resList.toString();
            res = res.replace("[","")
                    .replace("]","");
            return res;
        }
    }

    /**
     *
     * @param str
     * {token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbk5hbWUiOiJ2aXNpdG9yIiwiZXhwIjoxNjQ2MDc1OTg5LCJ1c2VySWQiOiIzIn0.AM6URm4BJBGsb_gTTLtdUyRhilN3nH72MEZ1Ecnd19Q}"
     * @return
     */
    public static String getTokenStr(String str) {
        //delete by ycao 20220228 https://www.cnblogs.com/skywalker-/p/15136017.html
        // caused by java.lang.NoClassDefFoundError: org/springframework/boot/configurationprocessor/json/JSONObject
//            JSONObject jsonObject = new JSONObject(str);
//            return jsonObject.getString("token");
        return getTokenStrOld(str);
    }
}
