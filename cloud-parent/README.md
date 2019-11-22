#  cloud parent
## 本POM主要维护：

```
 1、SpringBoot和SpringCloud的版本
 2、Plugin的版本
 3、Maven私服信息
 4、一些jar的version属性定义
```
非业务组件的服务可以将此POM作为父POM进行依赖，例如： Eureka、Zipkin等

##  下设2个子模块

### 1、cloud-api-parent
	所有的api项目必须以此POM作为父POM
### 2、cloud-service-parent
	所有业务Service必须以此POM作为父POM