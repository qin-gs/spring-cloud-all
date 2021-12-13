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



