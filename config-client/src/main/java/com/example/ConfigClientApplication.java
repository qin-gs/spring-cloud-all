package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    /**
     * config-client从config-server获取了foo的属性，
     * 而config-server是从git仓库读取的
     */
    @Value("${foo}")
    private String foo;

    @RequestMapping("hi")
    public String hi() {
        return foo;
    }
}
