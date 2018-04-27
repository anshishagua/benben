package com.anshishagua.configuration;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: lixiao
 * Date: 2018/4/24
 * Time: 上午10:10
 */

public class UrlMappingRegistry {
    private static final Map<String, Method> URL_MAPPING = new ConcurrentHashMap<>();

    public static void register(String url, Class<?> clazz, Method method) {
        Objects.requireNonNull(url);
        Objects.requireNonNull(method);

        if (URL_MAPPING.containsKey(url)) {
            throw new RuntimeException(String.format("URL path:%s already registered in [%s:%s]", url, clazz.getName(), method.getName()));
        }

        URL_MAPPING.put(url, method);
    }

    public static Method get(String urlPath) {
        Objects.requireNonNull(urlPath);

        return URL_MAPPING.get(urlPath);
    }

    public static Map<String, Method> get() {
        return Collections.unmodifiableMap(URL_MAPPING);
    }
}