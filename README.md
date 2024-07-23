# Klinika
Pet prod Spring Boot 6 with OAuth via Google and GitHub

1. Add Credentia 
https://console.cloud.google.com/apis/credentials?project

The Redirect URI for Google is http://localhost:8082/login/oauth2/code/google

![alt text](src/main/resources/images/image.png)

![alt text](src/main/resources/images/image-1.png)

![alt text](src/main/resources/images/image-2.png)

2. Add Client ID and secret into application-google.properties

3. Add https://github.com/settings/applications/new

The Callback URL for GitHub is http://localhost:8082/login/oauth2/code/github

![alt text](src/main/resources/images/image-3.png)

4. Add https://oauth.mail.ru/app/

https://id.vk.com/about/business/go/docs/en/vkid/latest/oauth/oauth-mail/index#XOAUTH2