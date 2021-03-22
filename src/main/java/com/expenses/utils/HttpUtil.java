package com.expenses.utils;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {
    private static final RestTemplate restTemplate = new RestTemplateBuilder().build();

    private HttpUtil() {}

    public static String get(String url) {
        return restTemplate.getForEntity(url, String.class).getBody();
    }
}
