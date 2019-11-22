
#Eureka Server
##一、单机模式：
javar -jar eureka-server-1.0.0.jar

##二、集群模式
###1、添加host
127.0.0.1    cluster1
127.0.0.1    cluster2
127.0.0.1    cluster3

###2、启动服务
javar -jar -Dspring.profiles.active=cluster1 eureka-server-1.0.0.jar
javar -jar -Dspring.profiles.active=cluster2 eureka-server-1.0.0.jar
javar -jar -Dspring.profiles.active=cluster3 eureka-server-1.0.0.jar

###3、检查eureka集群的状态
registered-replicas：        当前机器外的全部eureka
unavailable-replicas:    不可用的eureka
available-replicas:     可用的eureka

正常状态应该是available-replicas == registered-replicas

注意： springboot2.x的security默认开启了csrf检测，必须禁用掉eureka的服务间才能相互注册
