package com.example.order_service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
// 1. Add a unique tracking id to the headers
        String correlationId= UUID.randomUUID().toString();
        request.getHeaders().add("X-Correlation-Id",correlationId);

        // 2. logging
        log.info("====================request begin====================");
        log.info("URI            : {}",request.getURI());
        log.info("Method         : {}",request.getMethod());
        log.info("Headers        : {}",request.getHeaders());
        log.info("Request Body   : {}",new String(body, StandardCharsets.UTF_8));
        log.info("====================request end=========================");

//        3. Execute the request (let it go to the inventory service)
        ClientHttpResponse response = execution.execute(request, body);

        // 4. Log the incoming response
        log.info("====================response begin=======================");
        log.info("Status code   : {}",response.getStatusCode());
        log.info("Status text   :{}",response.getStatusText());


        // 5. read the response body for logging
        InputStreamReader isr=new InputStreamReader(response.getBody(),StandardCharsets.UTF_8);
        String bodyString=new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
        log.info("Response body    :{}",bodyString);


        log.info("====================response end====================");
        return response;


    }
}
