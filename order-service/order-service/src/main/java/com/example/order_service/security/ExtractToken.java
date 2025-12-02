package com.example.order_service.security;

import org.springframework.stereotype.Component;

@Component
public class ExtractToken {

    private static ThreadLocal<String> threadLocal=new ThreadLocal<>();

    public static String getToken(){
        return threadLocal.get();
    }

    public static void setToken(String token){
        threadLocal.set(token);
    }
}
