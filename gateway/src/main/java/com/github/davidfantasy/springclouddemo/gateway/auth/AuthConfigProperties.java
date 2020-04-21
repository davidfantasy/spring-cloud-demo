package com.github.davidfantasy.springclouddemo.gateway.auth;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AuthConfigProperties.AUTH_CONFIG_PREFIX)
@Data
public class AuthConfigProperties {

    public static final String AUTH_CONFIG_PREFIX = "auth.token";

    /**
     * accessToken的理论过期时间，单位分钟，token如果超过该时间则接口响应的header中附带新的token信息
     */
    private int maxAliveMinute = 30;

    /**
     * accessToken的最大生存周期，单位分钟，在此时间内的token无需重新登录即可刷新
     */
    private int maxIdleMinute = 60;

    /**
     * accessToken在http header中的name
     */
    private String headerKeyOfToken = "jwt-token";

    /**
     * 解析后的用户唯一标识在header中的name
     */
    private String headerKeyOfAccount = "X-user-account";

    /**
     * token中保存的用户名的key name
     */
    private String accountAlias = "account";

}
