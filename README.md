# Klinika
Pet project Klinika

## Technologies

1. **Spring Boot**
2. **Spring Boot MVC**
3. **Swagger UI**
4. **Spring Boot Security**
5. **Postgres**
6. **RESTful API**
7. **Docker-compose**
8. **Backup DB**
9. **AOP** (Log, Transaction, Error, Cache, Security)
10. **Cache**
11. **Kubernetes (K8s)**
12. **Mail**
13. **DevTools**
14. **Liquibase**
15. **Apache Kafka with Zookeeper and KRaft**
16. **Gradle**
17. **CI/DI (Jenkins)**

## Add Keystore

# Creating a keystore using RSA

    keytool -genkeypair -alias klinika -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore klinika.p12 -validity 365
    keytool -genkeypair -alias klinika -keyalg RSA -keysize 2048 -keystore klinika.jks -validity 365
    keytool -importkeystore -srckeystore klinika.jks -destkeystore klinika.jks -deststoretype pkcs12
    keytool -genkeypair -alias klinikaKey -keyalg RSA -keysize 2048 -validity 365 -keystore klinika.jks -storepass klinika

## Database Backup

## Creating a Backup (CMD)

    pg_dump -U postgres -W -F c -b -v -f klinika.backup Klinika  // копирует все таблицы  
    pg_dump -U postgres -W -F c -t doctor -f backup_doctor.backup Klinika  // копирует только одну таблицу  

## Restoring a backup (CMD)

    pg_restore -U postgres -W -d Klinika klinika.backup  // возвращает все таблицы  
    pg_restore -U postgres -W -d Klinika backup_doctor.backup  // возвращает только одну таблицу  

## Adding a backup to Docker (CMD)

    docker cp klinika.backup postgres_db:/klinika.backup  // копирование резервной копии  
    docker exec -it postgres_db pg_restore -U postgres -d Klinika /klinika.backup  // восстановление резервной копии  
    docker exec -t postgres_db pg_dump -U postgres -F c -b -v -f klinika.backup Klinika  // создание резервной копии  
    docker exec -t postgres_db pg_restore -U postgres -W -d Klinika klinika.backup  // восстановление резервной копии  

## Docker
    docker tag klinika kirillkus/klinika //  Create tag
    docker push kirillkus/klinika // Push container 

## Kubernetes (K8s)
    1. K8s.yml - without ssl
    2. K8s2.yml - with ssl

## command:
    minikube start  (start minikube)
    minikube dashboard ( ui Kubernetes )
    kubectl apply -f k8s.yaml ( executable file )
    kubectl apply -f k8s2.yaml ( executable file )
    kubectl logs klinika-765bb6f8f9-zl7cd  ( check log, where klinika-765bb6f8f9-zl7cd - name's pod)
    kubectl cp klinika.jks klinika-5c66d8b6b6-pg5ms:/app/keystore ( add klinika.jks to package /app/keystore in k8s)

## Gmail
    1. need app password
    https://myaccount.google.com/apppasswords
    2. add password to email.properties

## Liquibase
    1. Rollback ( in cmd )
    mvn liquibase:rollback -Dliquibase.rollbackCount=1

## Apache Kafka
   1. setup container-internal port forwarding
   # Connect to the container's bash (find out the name with 'docker ps')
    docker exec -it --user=root kafka-ui /bin/sh
   # Now, we are connected to the container's bash.
   # Let's install 'socat'
    apk add socat
   # Use socat to create the port forwarding
    socat tcp-listen:9092,fork tcp:host.docker.internal:9092
   # This will lead to a running process that we don't kill as long as the container's runnin
   ( Unfortunately, we need to run socat each time we start the container! )

