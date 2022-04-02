package com.ycao.mysite;

import com.ycao.mysite.utils.Tools;
import com.ycao.mysite.utils.database.RedisUtils;
import com.ycao.mysite.utils.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MysiteApplicationTests {

    @Test
    void contextLoads() {
        RedisUtils.set("ycao","0423");
        System.out.println(RedisUtils.get("ycao"));
    }

    //使用@Value的类，在spring中，不能直接通过new 操作符来使用，而是应该通过spring的注解 @Autowired 来使用
    @Autowired
    JwtUtil jwtutil;
    @Test
    void test2(){
        String paw="hello";
        String name="ycao";
        String	token = JwtUtil.sign("ycao","202020");   //admin类似就是一个权限等级。
        System.out.println(token);
        System.out.println(JwtUtil.verify(token));
    }

    @Test
    void test3() throws JSONException {
        String tokenT = "{token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbk5hbWUiOiJ5Y2FvIiwiZXhwIjoxNjM2MDMwNDQzLCJ1c2VySWQiOiIzIn0.bYh9A4OUS1dCFFg_2hD221WstgKYJUeewk0Bl_ea-3A}";
        String res = Tools.getTokenStr(tokenT);
        System.out.println(res);
    }

}
