package com.anshishagua.configuration;

import com.anshishagua.annotations.Configuration;
import com.anshishagua.annotations.Value;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午5:08
 */

@Configuration(propertyPrefix = "server.config")
public class ServerConfig {
    public static final String DEFAULT_HOST_NAME = "localhost";
    public static final int DEFAULT_PORT = 8080;

    @Value(property = "host")
    private String hostName = DEFAULT_HOST_NAME;
    @Value(property = "port")
    private int port = DEFAULT_PORT;

    public int getPort() {
        return port;
    }

    public String getHostName() {
        return hostName;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "hostName='" + hostName + '\'' +
                ", port=" + port +
                '}';
    }
}