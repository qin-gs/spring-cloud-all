package com.feign;

import feign.Contract;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过日志配置 feign 日志
 */
@Configuration
public class FeignLog {

    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.FULL;
    }

    /**
     * 使用 feign 自带的调用方法，不使用 spring 的
     */
    // @Bean
    public Contract contract() {
        return new Contract.Default();
    }

}
