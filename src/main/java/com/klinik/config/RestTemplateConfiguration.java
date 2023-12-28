package com.klinik.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class RestTemplateConfiguration {

    @Value("${server.ssl.key-store-password}")
    private String trustStorePassword;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
        SSLContext sslContext = SSLContextBuilder.create()
                                                .loadKeyMaterial(ResourceUtils.getFile("classpath:keystore/klinika.jks"), trustStorePassword.toCharArray(), trustStorePassword.toCharArray())
                                                .loadTrustMaterial(ResourceUtils.getFile("classpath:keystore/klinika.jks"), trustStorePassword.toCharArray())
                                                .build();
        HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
        return builder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client)).build();
    }


   // @Bean
    public Cookie getCookie( HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie test = null;
        if (cookies != null)
            for (Cookie cookie : cookies) {
                System.out.println( cookie.getName() );
                cookie.setMaxAge(0);
                response.addCookie( cookie );
                test =  cookie;
            }
        return test;
    } 
}
