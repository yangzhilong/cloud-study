java -jar zipkin-server-2.12.9-exec.jar --QUERY_PORT=8020 --STORAGE_TYPE=elasticsearch --ES_INDEX=zipkin --ES_INDEX_SHARDS=1 --ES_HOSTS='http://192.168.56.101:9200'