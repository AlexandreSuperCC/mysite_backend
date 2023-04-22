package com.ycao.mysite.utils.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.token.private-key}")
    private String privateKey;
    @Value("${jwt.token.limit-time}")
    private String limitTime; // 过期时间 毫秒

    @Value("${jwt.token.private-key}")
    public void setPrivateKey(String privateKey) {
        JwtUtil.privateKeyStatic = privateKey;
    }

    @Value("${jwt.token.limit-time}")
    public void setLimitTime(String limitTime) {
        JwtUtil.limitTimeStatic = limitTime;
    }

    private static String privateKeyStatic;
    private static String limitTimeStatic;

    /**
     * 生成签名
     * 公钥加密，私钥解密；私钥签名，公钥验签。
     * @param
     * @return
     */
    public static String sign(String username, String userId){
        try {
            //expired time
            Date date = new Date(System.currentTimeMillis()+Long.parseLong(limitTimeStatic));
            Algorithm algorithm = Algorithm.HMAC256(privateKeyStatic);
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
            Algorithm algorithm = Algorithm.HMAC256(privateKeyStatic);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

}


