package com.anshishagua.configuration;

import com.anshishagua.configuration.MybatisConfig;
import com.anshishagua.configuration.ServerConfig;
import com.anshishagua.configuration.ViewConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午5:19
 */

public class ConfigurationRegistry {
    public static final Map<String, Object> map = new HashMap<>();

    public static void register(String key, Object value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        map.put(key, value);
    }

    public static Map<String, Object> getMap() {
        return map;
    }

    public static ServerConfig getServerConfig() {
        return (ServerConfig) map.get("serverConfig");
    }

    public static ViewConfig getViewConfig() {
        return (ViewConfig) map.get("viewConfig");
    }

    public static MybatisConfig getMybatisConfig() {
        return (MybatisConfig) map.get("mybatisConfig");
    }
}