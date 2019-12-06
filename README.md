# 我的SpingCloud学习笔记
## 版本
```
	SpringBoot:	2.1.5.RELEASE
	SpringCloud: Greenwich.SR1
	SpringCloud-Alibaba: 2.1.0.RELEASE
```

## 项目模块：
### cloud-parent：
```
 1、SpringBoot和SpringCloud的版本
 2、Plugin的版本
 3、Maven私服信息
 4、一些jar的version属性定义
```

### common
```
 API项目和Service项目的公共类和Util合集
```

### eureka-server
- 单机端口： 8000
- 集群端口： 8001、8002、8003
```
 eureka的server服务，有单机配置和集群配置
```

### zipkin-server
- 端口： 8020
```
 zipkin 服务端
```

### gateway
```
Gateway的各种集成
```
- gateway-eureka-base
端口：8100

### producer-service
```
服务的提供者的多个版本
```
- producer-eureka-base
端口：8200

### elasticsearch
#### elasticsearch-spring-data-rest
- 使用spring-boot-starter-data-elasticsearch的restful api
- 端口：8210

### consumer-service
```
服务的消费者的多个版本
```