java -jar zipkin-server-2.12.9-exec.jar --QUERY_PORT=8020 --STORAGE_TYPE=mysql --MYSQL_USER=root --MYSQL_PASS=1234 --MYSQL_DB=zipkin --MYSQL_HOST=localhost --MYSQL_TCP_PORT=3306 --COLLECTOR_KAFKA_ENABLED=true --KAFKA_BOOTSTRAP_SERVERS=10.92.37.62:9092,10.92.37.39:9092,10.92.36.255:9092 --KAFKA_TOPIC=common-zipkin-strawberry --KAFKA_GROUP_ID=zipkin --zipkin.collector.kafka.overrides.sasl.mechanism=SCRAM-SHA-256 --zipkin.collector.kafka.overrides.security.protocol=SASL_PLAINTEXT --zipkin.collector.kafka.overrides.sasl.jaas.config="org.apache.kafka.common.security.scram.ScramLoginModule required username='zipkin' password='123456';"