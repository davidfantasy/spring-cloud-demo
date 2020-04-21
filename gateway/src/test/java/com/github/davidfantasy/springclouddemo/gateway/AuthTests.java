package com.github.davidfantasy.springclouddemo.gateway;

import com.github.davidfantasy.springclouddemo.gateway.auth.AuthConfigProperties;
import com.github.davidfantasy.springclouddemo.gateway.auth.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest
@AutoConfigureWebTestClient
class AuthTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthConfigProperties authConfig;

    @Test
    public void testCreateOrder() throws Exception {
        String aToken = jwtHelper.sign("aUser", "token-secret", authConfig.getMaxAliveMinute());
        String bToken = jwtHelper.sign("bUser", "token-secret", authConfig.getMaxAliveMinute());
        //测试无token访问
        webTestClient.get().uri("/order-service/api/order/orders").exchange().expectStatus().isUnauthorized();
        //测试authc验证
        webTestClient.get().uri("/order-service/api/order/orders").header(authConfig.getHeaderKeyOfToken(), aToken).exchange().expectStatus().isOk();
        //测试perms验证
        webTestClient.post().uri("/order-service/api/order/create-order")
                .header(authConfig.getHeaderKeyOfToken(), bToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"customerCode\":\"123\",\"goodCode\":\"tc-1\",\"quantity\":20}"))
                .exchange().expectStatus().isUnauthorized();
        webTestClient.post().uri("/order-service/api/order/create-order")
                .header(authConfig.getHeaderKeyOfToken(), aToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"customerCode\":\"123\",\"goodCode\":\"tc-1\",\"quantity\":20}"))
                .exchange().expectStatus().isOk();
    }

}
