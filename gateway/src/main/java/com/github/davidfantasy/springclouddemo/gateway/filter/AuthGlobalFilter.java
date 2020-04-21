package com.github.davidfantasy.springclouddemo.gateway.filter;

import com.github.davidfantasy.springclouddemo.gateway.auth.AuthConfigProperties;
import com.github.davidfantasy.springclouddemo.gateway.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private AuthService authService;

    private AuthConfigProperties authConfig;

    public AuthGlobalFilter(AuthConfigProperties authConfig, AuthService authService) {
        this.authConfig = authConfig;
        this.authService = authService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String reqPath = exchange.getRequest().getURI().getPath();
        String token = exchange.getRequest().getHeaders().getFirst(authConfig.getHeaderKeyOfToken());
        if (!authService.verifyToken(reqPath, token)) {
            log.warn("没有授权的访问，{}", reqPath);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    /**
     * 过滤器的优先级，越低越高
     */
    @Override
    public int getOrder() {
        return 1;
    }

}
