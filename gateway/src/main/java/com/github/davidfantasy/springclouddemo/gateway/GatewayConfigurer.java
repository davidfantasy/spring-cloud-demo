package com.github.davidfantasy.springclouddemo.gateway;

import com.github.davidfantasy.springclouddemo.gateway.auth.AuthConfigProperties;
import com.github.davidfantasy.springclouddemo.gateway.filter.AuthGlobalFilter;
import com.github.davidfantasy.springclouddemo.gateway.filter.LogGatewayFilterFactory;
import com.github.davidfantasy.springclouddemo.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AuthConfigProperties.class})
public class GatewayConfigurer {

    @Autowired
    private AuthConfigProperties authConfig;

    @Bean
    public LogGatewayFilterFactory logGatewayFilterFactory() {
        return new LogGatewayFilterFactory();
    }

    @Bean
    public AuthGlobalFilter authGlobalFilter(AuthService authService) {
        return new AuthGlobalFilter(authConfig, authService);
    }


}
