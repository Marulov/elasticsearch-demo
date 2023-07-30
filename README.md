# elasticsearch-demo
This is a Java Spring Boot application that performs crud operations with Elasticsearch.

---

## Technologies
- Java 17
- Spring Boot 3.1.1
- Elasticsearch 8.7.1
- Kibana
- Restful API
- Maven
- Docker
- Docker Compose
- application.yml


## docker-compose.yaml
This is a docker-compose.yml with Elasticsearch and Kibana on MacBook m1 version

Run the following command in the directory where the file is located.


```sh
docker-compose up
```

```sh
version: '3.8'
services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.7.1-arm64
    expose:
      - 9200
    environment:
      - xpack.security.enabled=false
      - "discovery.type=single-node"
      - ELASTIC_USERNAME=username
      - ELASTIC_PASSWORD=password
    networks:
      - es-net
    ports:
      - 9200:9200
    volumes:
      - elasticsearch-data:/usr/share/elasticsearch/data
  kibana:
    image: docker.elastic.co/kibana/kibana:8.7.1-arm64
    environment:
      - ELASTICSEARCH_HOSTS=http://elasticsearch:9200
    expose:
      - 5601
    networks:
      - es-net
    depends_on:
      - elasticsearch
    ports:
      - 5601:5601
    volumes:
      - kibana-data:/usr/share/kibana/data
networks:
  es-net:
    driver: bridge
volumes:
  elasticsearch-data:
    driver: local
  kibana-data:
    driver: local
```

