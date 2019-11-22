# Gateway的多个集成版本
## gateway-eureka-base
基础的gateway，向eureka注册

## 验证
1、访问:http://localhost:8100/api/test,返回hello字符串，filter直接chain了。

2、访问http://localhost:8100/api/test?key=xxx，filter将通过fegin和okhttp调用服务，并打印日志在控制台