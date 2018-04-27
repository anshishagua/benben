package com.anshishagua.server;

import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.configuration.MybatisConfig;
import com.anshishagua.configuration.ServerConfig;
import com.anshishagua.configuration.ViewConfig;
import com.anshishagua.utils.PackageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: lixiao
 * Date: 2018/4/24
 * Time: 上午11:18
 */

public class Bootstrap {
    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    private static ServerConfig serverConfig;
    private static ViewConfig viewConfig;
    private static MybatisConfig mybatisConfig;

    private static void init() {
        initServerConfig();
        initViewConfig();
        initMybatisConfig();
    }

    private static void initServerConfig() {
        serverConfig =  ConfigurationRegistry.getServerConfig();
    }

    private static void initViewConfig() {

    }

    private static void initMybatisConfig() {
        mybatisConfig = ConfigurationRegistry.getMybatisConfig();
        mybatisConfig.config();
    }

    public static void run(String [] args) throws Exception {
        String basePackageName = PackageUtils.getBasePackageName();
        PackageUtils.scanPackage(basePackageName, true);
        init();

        ServerConfig serverConfig = ConfigurationRegistry.getServerConfig();

        Server server = ServerFactory.getServer(serverConfig.getServerType());

        server.start();

        LOG.info("Server started successfully");
    }

    public static void run() throws Exception {
        String basePackageName = PackageUtils.getBasePackageName();
        PackageUtils.scanPackage(basePackageName, true);
        init();

        ServerConfig serverConfig = ConfigurationRegistry.getServerConfig();

        Server server = ServerFactory.getServer(serverConfig.getServerType());

        server.start();

        LOG.info("Server started successfully");
    }

    public static void main(String [] args) throws Exception {
        Package packa = Package.getPackage("com.anshishagua");

        System.out.println(packa);
    }
}