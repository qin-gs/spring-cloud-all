package com.forezp.service;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fangzhipeng on 2017/4/6.
 */
@FeignClient(value = "service-hi", fallback = SchedualServiceHiHystric.class)
public interface SchedualServiceHi {

    /**
     * 两个注解，一个是 feign 原生的，另一个是 spring 的
     */
    @RequestLine("GET /hi")
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    String sayHiFromClientOne(@Param("name") @RequestParam(value = "name") String name);
}
