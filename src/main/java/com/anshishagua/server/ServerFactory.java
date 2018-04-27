package com.anshishagua.server;

import com.anshishagua.constants.ServerType;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 上午10:30
 */

public class ServerFactory {
    public static Server getServer(ServerType serverType) {
        Objects.requireNonNull(serverType);

        switch (serverType) {
            case JETTY:
                Server jettyServer = new JettyServer();
                jettyServer.init();

                return jettyServer;
            case TOMCAT:
                Server tomcatServer = new TomcatServer();
                tomcatServer.init();

                return tomcatServer;
            default:
                throw new RuntimeException("Unknown server type:" + serverType);
        }
    }
}