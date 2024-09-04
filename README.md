# Klinika
Pet project Klinika

## Технологии

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

## Добавление Keystore

# Создание keystore с использованием RSA
    `keytool -genkeypair -alias klinika -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore klinika.p12 -validity 365`
    `keytool -genkeypair -alias klinika -keyalg RSA -keysize 2048 -keystore klinika.jks -validity 365`
    `keytool -importkeystore -srckeystore klinika.jks -destkeystore klinika.jks -deststoretype pkcs12`

#  add keys 
    `keytool -genkeypair -alias klinikaKey -keyalg RSA -keysize 2048 -validity 365 -keystore klinika.jks -storepass klinika`

## Резервное Копирование Базы Данных

### Команды CMD:

1. **Создание резервной копии**

   1.1  `pg_dump -U postgres -W -F c -b -v -f klinika.backup Klinika`  // копирует все таблицы  
   1.2  `pg_dump -U postgres -W -F c -t doctor -f backup_doctor.backup Klinika`  // копирует только одну таблицу  

2. **Восстановление резервной копии**

   2.1  `pg_restore -U postgres -W -d Klinika klinika.backup`  // возвращает все таблицы  
   2.2  `pg_restore -U postgres -W -d Klinika backup_doctor.backup`  // возвращает только одну таблицу  

3. **Добавление резервной копии в Docker**

     `docker cp klinika.backup postgres_db:/klinika.backup`  // копирование резервной копии  
     `docker exec -it postgres_db pg_restore -U postgres -d Klinika /klinika.backup`  // восстановление резервной копии  
     `docker exec -t postgres_db pg_dump -U postgres -F c -b -v -f klinika.backup Klinika`  // создание резервной копии  
     `docker exec -t postgres_db pg_restore -U postgres -W -d Klinika klinika.backup`  // восстановление резервной копии  

## Docker
    `docker tag klinika kirillkus/klinika` //  Create tag
    `docker push kirillkus/klinika` // Push container 

## Kubernetes (K8s)
5.1 K8s.yml - without ssl
5.2 K8s.yml - with ssl

command:
minikube start  (start minikube)
minikube dashboard ( ui Kubernetes )
kubectl apply -f k8s.yaml ( executable file )
kubectl apply -f k8s2.yaml ( executable file )
kubectl logs klinika-765bb6f8f9-zl7cd  ( check log, where klinika-765bb6f8f9-zl7cd - name's pod)
kubectl cp klinika.jks klinika-5c66d8b6b6-pg5ms:/app/keystore ( add klinika.jks to package /app/keystore in k8s)

