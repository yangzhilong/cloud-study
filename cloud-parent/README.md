#  dependency parent
- 管理公共依赖

##  下设4个模块
#### 1、cloud-parent
    管理springboot/cloud版本，下面3个pom的依赖的版本，私服，插件等维护
### 2、cloud-api-parent
	所有的api项目必须以此POM作为父POM
### 3、cloud-web-parent
	所有业务SpringMVC Service必须以此POM作为父POM
### 4、cloud-webflux-parent
	所有业务SpringFlux Service必须以此POM作为父POM