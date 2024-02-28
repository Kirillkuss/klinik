# Klinika
 
* Spring MVC
* Spring Data
* Postgres
* Lombok
* Docker
* K8s
* Mockito
* RSocket
* https and http/2
* Spring AOP
* DevTools
* Allure and Jacoco
* Swagger
* java 17
* Jenkins
* jmeter
* Caching in Spring
* Redis
* Mongo 


* Cassandra
 ( cmd )
1. Запуск
docker run --name cassandra -d cassandra:latest
2. Подключение к контейнеру
docker exec -it cassandra cqlsh
3. Создаение KEYSPACE
CREATE KEYSPACE IF NOT EXISTS testkeyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 } AND DURABLE_WRITES = true ;
4. Проверка созданного keyspace
DESCRIBE KEYSPACES;
