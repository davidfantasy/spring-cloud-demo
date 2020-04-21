package com.github.davidfantasy.springclouddemo.gateway.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtHelper {

    @Autowired
    private AuthConfigProperties authConfig;

    public JwtHelper(AuthConfigProperties authConfig) {
        this.authConfig = authConfig;
    }

    /**
     * 校验token是否正确
     */
    public DecodedJWT verify(String token, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    //在token失效前提供一个安全窗口期，使前端有机会刷新token
                    //注意这里的单位为秒
                    .acceptExpiresAt(authConfig.getMaxIdleMinute() * 60)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    /**
     * 获得token中的信息，注意此时尚未验证token的有效性
     *
     * @return token中包含的用户名
     */
    public String getAccount(String token) {
        if (Strings.isNullOrEmpty(token)) {
            return null;
        }
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(authConfig.getAccountAlias()).asString();
        } catch (JWTDecodeException e) {
            log.error("decode token error", e);
        }
        return null;
    }

    /**
     * 生成签名
     *
     * @param account            用户名
     * @param secret             用于加密的key
     * @param expireAfterMinutes 指定token在多少分钟后过期
     * @return 加密的token
     */
    public String sign(String account, String secret, long expireAfterMinutes) {
        Date expireAfter = new Date(System.currentTimeMillis() + expireAfterMinutes * 60 * 1000);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withIssuedAt(new Date())
                .withClaim(authConfig.getAccountAlias(), account)
                .withExpiresAt(expireAfter)
                .sign(algorithm);
    }


}
