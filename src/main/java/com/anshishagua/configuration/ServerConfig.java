package com.anshishagua.configuration;

import com.anshishagua.annotations.Configuration;
import com.anshishagua.annotations.Value;
import com.anshishagua.constants.ServerType;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午5:08
 */

@Configuration(propertyPrefix = "server.config")
public class ServerConfig {
    public static final String DEFAULT_HOST_NAME = "localhost";
    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_DOC_BASE = ".";
    public static final String DEFAULT_CONTEXT_PATH = "";
    public static final boolean DEFAULT_AUTO_DEPLOY = false;
    public static final ServerType DEFAULT_SERVER_TYPE = ServerType.JETTY;

    @Value(property = "host")
    private String hostName = DEFAULT_HOST_NAME;

    @Value(property = "port")
    private int port = DEFAULT_PORT;

    private String docBase = DEFAULT_DOC_BASE;

    private String contextPath = DEFAULT_CONTEXT_PATH;

    private boolean autoDeploy = DEFAULT_AUTO_DEPLOY;

    @Value(property = "server.type")
    private ServerType serverType = DEFAULT_SERVER_TYPE;

    public int getPort() {
        return port;
    }

    public String getHostName() {
        return hostName;
    }

    public String getDocBase() {
        return docBase;
    }

    public String getContextPath() {
        return contextPath;
    }

    public boolean isAutoDeploy() {
        return autoDeploy;
    }

    public ServerType getServerType() {
        return serverType;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "hostName='" + hostName + '\'' +
                ", port=" + port +
                ", docBase='" + docBase + '\'' +
                ", contextPath='" + contextPath + '\'' +
                '}';
    }
}