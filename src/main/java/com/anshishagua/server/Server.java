package com.anshishagua.server;

import com.anshishagua.configuration.ServerConfig;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 上午9:58
 */

public interface Server {
    ServerConfig getServerConfig();
    void init();
    void start();
    void stop();
}