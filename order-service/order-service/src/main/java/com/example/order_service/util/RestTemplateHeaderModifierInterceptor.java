package com.example.order_service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
@Slf4j
public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//        1. grab the current incoming request
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes!=null){
//            2. extract the authorization header
            String jwtToken = attributes.getRequest().getHeader("Authorization");

//            3. if the token exists, attach it to the OUTGOING request to the inventory.
            if(jwtToken!=null){
                request.getHeaders().add("Authorization",jwtToken);
                log.info("Propagating Jwt token: "+ jwtToken);

            }
        }
        return execution.execute(request,body);
    }
}
