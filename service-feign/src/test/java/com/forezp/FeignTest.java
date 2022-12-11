package com.forezp;

import com.forezp.interceptor.FeignInterceptor;
import com.forezp.service.SchedualServiceHi;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class FeignTest {

    /**
     * 测试
     * TODO 版本不对
     */
    @Test
    public void test() {
        SchedualServiceHi target = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .requestInterceptor(new FeignInterceptor())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .contract(new Contract.Default())
                // .options(new Request.Options(1_0000L, TimeUnit.MILLISECONDS, 1_0000L, TimeUnit.MILLISECONDS, true))
                .retryer(new Retryer.Default(5000, 5000, 3))
                .target(SchedualServiceHi.class, "http://localhost:8762/");

        String hi = target.sayHiFromClientOne("world");
        System.out.println("hi = " + hi);
    }
}
