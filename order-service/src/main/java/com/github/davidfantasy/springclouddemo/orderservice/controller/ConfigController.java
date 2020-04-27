package com.github.davidfantasy.springclouddemo.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@Slf4j
@RefreshScope
public class ConfigController {

    @Value("${global-config.key1}")
    private String key1;

    @Value("${service-config.key2}")
    private String key2;

    @GetMapping("/test-config-center")
    public void testConfigCenter() {
        log.info("global-config.key1:{},service-config.key2:{}", key1, key2);
    }

}
