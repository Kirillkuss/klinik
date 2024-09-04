# Klinika
Pet prod Klinika

1. Spring Boot
2. Spring Boot MVC
3. Swagger
4. ui
5. Spring Boot Security
6. Postgres
7. RESTful
8. Docker-compose
9. Backup db
10. AOP - log, transaction, error, cache, security
11. Сache
12. K8s 

Add keystore 

//klinika
keytool -genkeypair -alias klinika -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore klinika.p12 -validity 365
keytool -genkeypair -alias klinika -keyalg RSA -keysize 2048 -keystore klinika.jks -validity 365
keytool -importkeystore -srckeystore klinika.jks -destkeystore klinika.jks -deststoretype pkcs12

//add keys 
keytool -genkeypair -alias klinikaKey -keyalg RSA -keysize 2048 -validity 365 -keystore klinika.jks -storepass klinika

Backup for Bd

cmd commands :

1. Create backup 

1.1  pg_dump -U postgres -W -F c -b -v -f klinika.backup Klinika                // add all tables 
1.2  pg_dump -U postgres -W -F c -t doctor -f backup_doctor.backup Klinika      // add only one table 

2. Take backup

2.1  pg_restore -U postgres -W -d Klinika klinika.backup            // return all tables 
2.2  pg_restore -U postgres -W -d Klinika backup_doctor.backup      // return only one table

3. add backup in docker 

3.1 docker cp klinika.backup postgres_db:/klinika.backup                            // copy backup
3.2 docker exec -it postgres_db pg_restore -U postgres -d Klinika /klinika.backup  // copy backup
   
3.3 docker exec -t postgres_db pg_dump -U postgres -F c -b -v -f klinika.backup Klinika  // excute backup
3.4 docker exec -t postgres_db pg_restore -U postgres -W -d Klinika klinika.backup       // excute backup

4. Docker
4.1. Create tag
docker tag klinika kirillkus/klinika
4.2 Push container
docker push kirillkus/klinika

5. Kubernetes ( K8s )
5.1 K8s.yml - without ssl
5.2 K8s.yml - with ssl

command:
minikube start  (start minikube)
minikube dashboard ( ui Kubernetes )
kubectl apply -f k8s.yaml ( executable file )
kubectl apply -f k8s2.yaml ( executable file )
kubectl logs klinika-765bb6f8f9-zl7cd  ( check log, where klinika-765bb6f8f9-zl7cd - name's pod)
kubectl cp klinika.jks klinika-5c66d8b6b6-pg5ms:/app/keystore ( add klinika.jks to package /app/keystore in k8s)

