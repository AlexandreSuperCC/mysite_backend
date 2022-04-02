package com.ycao.mysite.utils.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "jwt.token")
@PropertySource(value = {"classpath:jwt.properties"})
public class JwtUtil {
    private static String privateKey;
    private static String limitTime; // 过期时间 毫秒

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        JwtUtil.privateKey = privateKey;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        JwtUtil.limitTime = limitTime;
    }

    /**
     * 生成签名
     * 公钥加密，私钥解密；私钥签名，公钥验签。
     * @param
     * @return
     */
    public static String sign(String username, String userId){
        try {
            //expired time
            Date date = new Date(System.currentTimeMillis()+Long.parseLong(limitTime));
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            //set header info
            Map<String,Object> header = new HashMap<>(2);
            header.put("typ","JWT");
            header.put("alg","HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("loginName",username)
                    .withClaim("userId",userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * verify the token is valid or not
     * MessageDigest.isEqual(this.createSignatureFor(algorithm, secretBytes, contentBytes), signatureBytes);
     * @param
     * @return
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

}


