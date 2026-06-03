package com.husseinabonoktah.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient restClient(
            @Value("${application.http.connect-timeout-ms:3000}") int connectTimeoutMs,
            @Value("${application.http.read-timeout-ms:5000}") int readTimeoutMs
    ) {

        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeoutMs);
        requestFactory.setReadTimeout(readTimeoutMs);

        return RestClient.builder()
                .requestFactory(requestFactory)
                .build();
    }
}
