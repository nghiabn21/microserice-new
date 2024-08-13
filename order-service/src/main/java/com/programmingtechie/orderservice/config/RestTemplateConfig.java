package com.programmingtechie.orderservice.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RestTemplateConfig {
//    @Bean
//    public RestTemplate getRestTemplatePartner() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//        TrustStrategy acceptingTrustStrategy = new TrustSelfSignedStrategy();
//
//        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
//                .build();
//
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
//
//        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        requestFactory.setHttpClient((HttpClient) httpClient);
////        requestFactory.setReadTimeout(30 * 1000);
//        requestFactory.setConnectTimeout(30 * 1000);
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        return restTemplate;
//    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}