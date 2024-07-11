# Klinika
Pet prod Klinika with  Keycloak ( Spring Security OAuth2.0 ) 

1. Add container keycloak 
---------------------------------
docker run --name KEYCLOAK_TEST -p 8079:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.1 start-dev

http://localhost:8079

login: admin
password: admin

----------------
create realm

![alt text](src/main/resources/images/image.png)

![alt text](src/main/resources/images/image-1.png)

create client

![alt text](src/main/resources/images/image-2.png)

![alt text](src/main/resources/images/image-3.png)

![alt text](src/main/resources/images/image-4.png)

![alt text](src/main/resources/images/image-5.png)

Client scopes -> microprofile-jwt ->( Default )

Create role 
-------------------
![alt text](src/main/resources/images/image-6.png)

name: role_klinika

![alt text](src/main/resources/images/image-7.png)

Create user
-------------------
![alt text](src/main/resources/images/image-8.png)

![alt text](src/main/resources/images/image-9.png)

in the credentials add password

![alt text](src/main/resources/images/image-10.png)

add role for user( klinika )

![alt text](src/main/resources/images/image-11.png)
-----------------------------
Turn off "Verify profile" in "Authentication" -> "Required actions"

![alt text](src/main/resources/images/image-12.png)

3. Check tocken in postman
![alt text](src/main/resources/images/image-13.png)

( http://localhost:8079/realms/KlinikaKeycloak/protocol/openid-connect/token )

2. Start app Klinika

into package "klinik"

3. open link http://localhost:8082/