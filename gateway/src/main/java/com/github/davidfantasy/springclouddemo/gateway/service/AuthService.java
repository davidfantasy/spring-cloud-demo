package com.github.davidfantasy.springclouddemo.gateway.service;

import com.github.davidfantasy.springclouddemo.gateway.auth.JwtHelper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private JwtHelper jwtHelper;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * 根据用户的唯一标识获取该用户的权限列表
     */
    public Set<String> getUserPermissions(String account) {
        if ("aUser".equals(account)) {
            return Sets.newHashSet("order", "storage");
        }
        return Collections.emptySet();
    }

    /**
     * 根据用户的唯一标识获取accessToken的加密密钥
     */
    public String getUserSecret(String account) {
        return "token-secret";
    }

    /**
     * 获取所有的接口url与用户权限的映射关系,格式仿造了shiro的权限配置格式
     */
    public Map<String, String> getAllUrlPermissionsMap() {
        Map<String, String> urlPermissionsMap = Maps.newHashMap();
        urlPermissionsMap.put("/order-service/api/order/orders", "authc");
        urlPermissionsMap.put("/order-service/api/order/create-order", "perms[order]");
        urlPermissionsMap.put("/storage-service/api/storage/**", "perms[storage]");
        return urlPermissionsMap;
    }

    /**
     * 根据一个确定url获取该url对应的权限设置
     * 利用AntPathMatcher进行模式匹配
     */
    private String getUrlPermission(String url) {
        if (Strings.isNullOrEmpty(url)) {
            return null;
        }
        Map<String, String> urlPermissionsMap = getAllUrlPermissionsMap();
        Set<String> urlPatterns = urlPermissionsMap.keySet();
        for (String pattern : urlPatterns) {
            boolean match = false;
            if (antPathMatcher.isPattern(pattern)) {
                match = antPathMatcher.match(pattern, url);
            } else {
                match = url.equals(pattern);
            }
            if (match) {
                return urlPermissionsMap.get(pattern);
            }
        }
        return null;
    }

    public String getAccountByToken(String token) {
        return jwtHelper.getAccount(token);
    }

    /**
     * 验证token的有效性及是否具备对该url的访问权限，
     * 判定规则参考了shiro的一些设定
     */
    public boolean verifyToken(String url, String token) {
        if (Strings.isNullOrEmpty(token)) {
            return false;
        }
        String urlPermission = getUrlPermission(url);
        if ("anno".equals(urlPermission)) {
            return true;
        } else {
            //获取token中包含的用户唯一标识
            String account = jwtHelper.getAccount(token);
            if (Strings.isNullOrEmpty(account)) {
                return false;
            }
            //获取token的加密密钥
            String secret = getUserSecret(account);
            //校验accessToken
            if (jwtHelper.verify(token, secret) == null) {
                return false;
            }
            // 如果url仅要求验证用户有效性，则直接通过
            if (Strings.isNullOrEmpty(urlPermission) ||
                    "authc".equals(urlPermission)) {
                return true;
            }
            // 进一步判断用户权限
            if (urlPermission.startsWith("perms")) {
                Set<String> userPerms = this.getUserPermissions(account);
                String perms = urlPermission.substring(urlPermission.indexOf("[") + 1, urlPermission.lastIndexOf("]"));
                log.info("account:{},{},{}", account, userPerms, perms);
                return userPerms.containsAll(Arrays.asList(perms.split(",")));
            }
        }
        return false;
    }

}
