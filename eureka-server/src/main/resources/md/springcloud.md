###    SpringCloud



#### 注册中心 eureka

是一个服务注册和发现模块。

@EnableEurekaServer  启动一个服务注册中心

```yaml
eureka:
  client:
    registerWithEureka: false
    fetchRegistry: false # 表明自己是一个 server
```



#### 提供服务

@EnableEurekaClient 表明自己是一个eureka client，还需要在配置文件中注明自己的服务注册中心的地址

(这里真正的处理请求(供其他服务器调用))                                    

```yaml
spring:
  application:
    name: service-hi # 服务之间的根据名字进行互相调用
```



#### ribbon + restTemplate 消费服务

ribbon 是一个负载均衡客户端，可以很好的控制 http 和 tcp 的一些行为。Feign 默认集成了 ribbon

@EnableDiscoveryClient 向服务中心注册；并且向程序的 ioc 注入一个 bean: restTemplate;

@LoadBalanced 注解表明这个 restTemplate 开启负载均衡的功能

(这里通过 Controller 接收请求，然后转发到上面真正的处理请求)

```java
@Bean
@LoadBalanced
RestTemplate restTemplate() {
    return new RestTemplate();
}
```

![eureka+ribbon简单架构](./img/eureka+ribbon简单架构.png)

- 一个服务注册中心，eureka server,端口为8761
- service-hi工程跑了两个实例，端口分别为8762,8763，分别向服务注册中心注册
- service-ribbon端口为8764,向服务注册中心注册
- 当service-ribbon通过restTemplate调用service-hi的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用service-hi：8762和8763 两个端口的hi接口；



#### feign 消费服务

Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。

Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。

1. @EnableFeignClients 注解开启Feign的功能

2. 定义一个feign接口，通过@ FeignClient（“服务名”），来指定调用哪个服务

3. 在Web层的controller层，对外暴露API接口，通过上面定义的Feign客户端接口来消费服务



#### 断路器 Hystrix

在微服务架构中，根据业务来拆分成一个个的服务，服务与服务之间可以相互调用（RPC），在Spring Cloud可以用RestTemplate+Ribbon和Feign来调用。为了保证其高可用，单个服务通常会集群部署。由于网络原因或者自身的原因，服务并不能保证100%可用，如果单个服务出现问题，调用这个服务就会出现线程阻塞，此时若有大量的请求涌入，Servlet容器的线程资源会被消耗完毕，导致服务瘫痪。服务与服务之间的依赖性，故障会传播，会对整个微服务系统造成灾难性的严重后果，这就是服务故障的“雪崩”效应。

Netflix开源了Hystrix组件，实现了断路器模式，SpringCloud对这一组件进行了整合。

较底层的服务如果出现故障，会导致连锁故障。当对特定的服务的调用的不可用达到一个阀值（Hystric 是5秒20次） 断路器将会被打开。

断路打开后，可用避免连锁故障，fallback方法可以直接返回一个固定值。

在**消费服务的代码**中进行配置

1. ribbon 中使用断路器

   @EnableHystrix 注解开启Hystrix

   在 Service 方法上加上 @HystrixCommand 注解。该注解对该方法创建了熔断器的功能，并指定了 fallbackMethod 熔断方法

    ```java
    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name, String.class);
    }
    public String hiError(String name) {
        return "hi, " + name + ", sorry error!";
    }
    ```

2. feign 中使用断路器

   在 Service 接口 注解上加上指定的类

   ```java
   @FeignClient(value = "service-hi", fallback = SchedualServiceHiHystric.class)
   public interface SchedualServiceHi {
       @RequestMapping(value = "/hi", method = RequestMethod.GET)
       String sayHiFromClientOne(@RequestParam(value = "name") String name);
   }
   ```




Hystrix Dashboard（仪表盘）

主程序启动类(消费服务的代码)中加入 @EnableHystrixDashboard 注解，开启 hystrixDashboard



#### Zuul 路由转发 + 过滤器

在微服务架构中，需要几个基础的服务治理组件，包括服务注册与发现、服务消费、负载均衡、断路器、智能路由、配置管理等，由这几个基础组件相互协作，共同组建了一个简单的微服务系统。

![简单微服务架构](./img/简单微服务架构.png)

**A服务和B服务是可以相互调用的，并且配置服务也是注册到服务注册中心的。**

在Spring Cloud微服务系统中，一种常见的负载均衡方式是，客户端的请求首先经过负载均衡（zuul、Ngnix），再到达服务网关（zuul集群），然后再到具体的服务。服务统一注册到高可用的服务注册中心集群（Eureka），服务的所有的配置文件由配置服务管理，配置服务的配置文件放在git仓库，方便开发人员随时改配置。

Zuul的主要功能是路由转发和过滤器，默认和Ribbon结合实现了负载均衡的功能。



 