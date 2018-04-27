package com.anshishagua.server;

import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.configuration.ServerConfig;
import com.anshishagua.exceptions.ServerException;
import com.anshishagua.utils.PackageUtils;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 上午10:37
 */

public class JettyServer implements com.anshishagua.server.Server {
    private static final Logger LOG = LoggerFactory.getLogger(JettyServer.class);

    private final ServerConfig serverConfig = ConfigurationRegistry.getServerConfig();
    private Server server;

    @Override
    public void init() {
        server = new Server(serverConfig.getPort());
        server.setHandler(new ServerHandler());
    }

    @Override
    public void start() {
        try {
            server.start();
            LOG.info("Started jetty server at port:{}", serverConfig.getPort());
            server.join();
        } catch (Exception ex) {
            LOG.error("Failed to start jetty server:", ex);

            throw new ServerException(ex);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception ex) {
            LOG.error("Failed to stop jetty server:", ex);

            throw new ServerException(ex);
        }
    }

    @Override
    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8989);
        server.setHandler(new ServerHandler());
        PackageUtils.scanPackage(PackageUtils.getBasePackageName(), true);
        server.start();
        server.join();
    }
}
