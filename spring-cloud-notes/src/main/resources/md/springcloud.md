### SpringCloud

#### 概述

客户端访问：

服务间通信：http, rpc

服务治理：服务注册与发现机制

服务出错：

SpringCloud 生态：

- SpringCloud + Netflix： 一站式

- Dubbo + zookeeper：

- SpringCloud Alibaba

|                | SpringCloud Netflix | Dubbo + zookeeper | SpringCloud Alibaba |
| -------------- | ------------------- | ----------------- | ------------------- |
| 路由(api网关)  | zuul                | 第三方            |                     |
| 通信           | feign               | dubbo             |                     |
| 服务注册与发现 | eureka              | zookeeper         |                     |
| 熔断机制       | hystrix             | 第三方            |                     |

**微服务**

| 功能                                 | 技术实现                       |
| ------------------------------------ | ------------------------------ |
| 服务开发                             | Spring, SpringMVC, SpringBoot  |
| 服务配置与管理                       | Netflix公司的Archaius, Diamond |
| 服务注册与发现                       | Eureka, Consul, Zookeeper      |
| 服务调用                             | RPC, gRPC                      |
| 服务熔断器                           | Hystrix, Envoy                 |
| 负载均衡                             | Ribbon, nginx                  |
| 服务接口调用(客户端调用服务简化工具) | Feign                          |
| 消息队列                             | kafka, rabbitmq, activemq      |
| 服务配置中心管理                     | SpringCloufConfig, Chef        |
| 服务路由                             | Zuul                           |
| 服务监控                             | Zabbix, Nagios, Metrics        |
| 全链路跟踪                           | Zipkin, Brave, Dapper          |
| 服务部署                             | Docker, Kubernetes             |
| 数据流操作开发包                     | SpringCloud Stream             |
| 消息事件总线                         | SpringCloud Bus                |



<img src="./img/Spring Cloud diagram.svg" alt="Spring Cloud diagram" style="zoom: 25%;" />



https://www.springcloud.cc/

http://springcloud.cn/



https://www.springcloud.cc/spring-cloud-netflix.html



# SpringCloud 原理

CAP

- 一致性
- 可用性
- 分区容错性

zookeeper：CP，保证数据一致性，服务不可用后短时间内无法提供服务

eureka：保证可用性，某个服务有问题不影响其他





## Feign 原理



- 使用 feign 自带的注解

  @RequestLine, @Param, @Headers, @QueryMap, @HeaderMap

- 使用 springMvc 的注解



1. 基于面向接口的动态代理方式生成实现类
2. 根据接口类的注解声明规则，解析出 MethodHandler
3. 基于 RequestBean 生成 Request
4. 将 bean 包装成请求 (编码器/解码器)
5. 拦截器复制对请求和响应进行处理 (拦截器)
6. 记录日志
7. 发送请求 (HttpURLConnection)，如果请求失败进行重试 (重试器)



服务调用过程：

- 获取代理对象 (`feign.ReflectiveFeign`)
- 控制反射方法调用 (`feign.InvocationHandlerFactory`)
- Client 实现类 (`feign.Client.Default`, `org.springframework.cloud.netflix.feign.ribbon.LoadBalancerFeignClient`)
- 封装请求参数 `RequestTemplate` ，发起请求





@EnableFeignClients 注解详解

- 引入 `FeignClientsRegistrar` 

- 扫描 `@FeignClient` 修饰的接口，每个接口生成一个 `BeanDefinition` 对象 (类型为 `FeignClientFactoryBean` )
  - 调用者：`default.com.forezp.ServiceFeignApplication.FeignClientSpecification`
  - 被调用者：`service-hi.FeignClientSpecification`
  - 每个接口：`com.forezp.service.SchedualServiceHi`

`org.springframework.cloud.netflix.feign.FeignClientFactoryBean`

- `getObject` (`refresh -> doCreateBean -> getObjectFromFactoryBean`)  -> `getTarget` 返回 `feign.Feign` (`FeignContext` 是 `org.springframework.cloud.openfeign.FeignAutoConfiguration` 注入的)

  创建 Feign 对象的过程中，根据 url 生成不同的**动态代理**对象

  - 有 url：走默认生成代理的方式 `feign.Client.Default`

  - 没有 url：走 ribbon 代理 (返回有负载均衡功能的 feign) `LoadbalancerFeignClient`

    调用 feign 接口时，代理类会调用 `Client#execute`



`org.springframework.cloud.netflix.feign.ribbon.FeignLoadBalancer`

`com.netflix.loadbalancer.PredicateBasedRule` 选择服务

`com.netflix.client.AbstractLoadBalancerAwareClient` 根据选择的服务发起请求











